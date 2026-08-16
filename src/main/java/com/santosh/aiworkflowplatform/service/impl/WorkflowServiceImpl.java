package com.santosh.aiworkflowplatform.service.impl;

import com.santosh.aiworkflowplatform.dto.request.CreateWorkflowRequest;
import com.santosh.aiworkflowplatform.dto.request.UpdateWorkflowRequest;
import com.santosh.aiworkflowplatform.dto.request.UpdateWorkflowStatusRequest;
import com.santosh.aiworkflowplatform.dto.response.WorkflowResponse;
import com.santosh.aiworkflowplatform.entity.Role;
import com.santosh.aiworkflowplatform.entity.User;
import com.santosh.aiworkflowplatform.entity.Workflow;
import com.santosh.aiworkflowplatform.entity.WorkflowStatus;
import com.santosh.aiworkflowplatform.exception.WorkflowInactiveException;
import com.santosh.aiworkflowplatform.repository.UserRepository;
import com.santosh.aiworkflowplatform.repository.WorkflowRepository;
import com.santosh.aiworkflowplatform.service.WorkflowService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkflowServiceImpl implements WorkflowService {

    private final WorkflowRepository workflowRepository;
    private final UserRepository userRepository;

    @Override
    public WorkflowResponse createWorkflow(CreateWorkflowRequest request) {

        User currentUser = getCurrentUser();

        LocalDateTime now = LocalDateTime.now();

        Workflow workflow = new Workflow();

        workflow.setName(request.getName());
        workflow.setDescription(request.getDescription());
        workflow.setDefinition(request.getDefinition());
        workflow.setStatus(WorkflowStatus.ACTIVE);
        workflow.setOwner(currentUser);
        workflow.setCreatedAt(now);
        workflow.setUpdatedAt(now);

        Workflow savedWorkflow = workflowRepository.save(workflow);

        return toResponse(savedWorkflow);
    }

    @Override
    public List<WorkflowResponse> getMyWorkflows() {

        User currentUser = getCurrentUser();

        return workflowRepository
                .findByOwnerId(currentUser.getId())
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public WorkflowResponse getWorkflow(Long workflowId) {

        User currentUser = getCurrentUser();

        Workflow workflow = workflowRepository.findById(workflowId)
                .orElseThrow(() ->
                        new RuntimeException("Workflow not found")
                );

        validateOwnership(workflow, currentUser);

        return toResponse(workflow);
    }

    @Override
    public WorkflowResponse updateWorkflow(
            Long workflowId,
            UpdateWorkflowRequest request) {

        User currentUser = getCurrentUser();

        Workflow workflow = workflowRepository.findById(workflowId)
                .orElseThrow(() ->
                        new RuntimeException("Workflow not found")
                );

        validateOwnership(workflow, currentUser);

        workflow.setName(request.getName());
        workflow.setDescription(request.getDescription());
        workflow.setDefinition(request.getDefinition());
        workflow.setUpdatedAt(LocalDateTime.now());

        Workflow updatedWorkflow = workflowRepository.save(workflow);

        return toResponse(updatedWorkflow);
    }

    @Override
    public WorkflowResponse updateWorkflowStatus(
            Long workflowId,
            UpdateWorkflowStatusRequest request) {

        User currentUser = getCurrentUser();

        Workflow workflow = workflowRepository.findById(workflowId)
                .orElseThrow(() ->
                        new RuntimeException("Workflow not found"));

        validateOwnership(workflow, currentUser);

        workflow.setStatus(request.getStatus());
        workflow.setUpdatedAt(LocalDateTime.now());

        Workflow updatedWorkflow =
                workflowRepository.save(workflow);

        return toResponse(updatedWorkflow);
    }

    @Override
    public void deleteWorkflow(Long workflowId) {

        User currentUser = getCurrentUser();

        Workflow workflow = workflowRepository.findById(workflowId)
                .orElseThrow(() ->
                        new RuntimeException("Workflow not found")
                );

        validateOwnership(workflow, currentUser);

        workflowRepository.delete(workflow);
    }

    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext()
                        .getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );
    }

    private void validateOwnership(
            Workflow workflow,
            User currentUser) {

        boolean isOwner =
                workflow.getOwner().getId()
                        .equals(currentUser.getId());

        boolean isAdmin =
                currentUser.getRole() == Role.ADMIN;

        if (!isOwner && !isAdmin) {
            throw new RuntimeException(
                    "You are not authorized to access this workflow"
            );
        }
    }

    private WorkflowResponse toResponse(Workflow workflow) {

        return new WorkflowResponse(
                workflow.getId(),
                workflow.getName(),
                workflow.getDescription(),
                workflow.getDefinition(),
                workflow.getStatus(),
                workflow.getOwner().getId(),
                workflow.getCreatedAt(),
                workflow.getUpdatedAt()
        );
    }
}