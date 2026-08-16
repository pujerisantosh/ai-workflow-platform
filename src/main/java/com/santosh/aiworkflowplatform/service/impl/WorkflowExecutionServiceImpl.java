package com.santosh.aiworkflowplatform.service.impl;

import com.santosh.aiworkflowplatform.dto.response.WorkflowExecutionResponse;
import com.santosh.aiworkflowplatform.entity.ExecutionStatus;
import com.santosh.aiworkflowplatform.entity.User;
import com.santosh.aiworkflowplatform.entity.Workflow;
import com.santosh.aiworkflowplatform.entity.WorkflowExecution;
import com.santosh.aiworkflowplatform.repository.UserRepository;
import com.santosh.aiworkflowplatform.repository.WorkflowExecutionRepository;
import com.santosh.aiworkflowplatform.repository.WorkflowRepository;
import com.santosh.aiworkflowplatform.service.WorkflowExecutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkflowExecutionServiceImpl
        implements WorkflowExecutionService {

    private final WorkflowRepository workflowRepository;
    private final WorkflowExecutionRepository executionRepository;
    private final UserRepository userRepository;

    @Override
    public WorkflowExecutionResponse executeWorkflow(Long workflowId) {

        User currentUser = getCurrentUser();

        Workflow workflow = workflowRepository.findById(workflowId)
                .orElseThrow(() ->
                        new RuntimeException("Workflow not found"));

        validateOwnership(workflow, currentUser);

        LocalDateTime now = LocalDateTime.now();

        WorkflowExecution execution = new WorkflowExecution();

        execution.setWorkflow(workflow);
        execution.setStatus(ExecutionStatus.RUNNING);
        execution.setStartedAt(now);
        execution.setCreatedAt(now);

        execution = executionRepository.save(execution);

        try {

            // Temporary execution logic.
            // The real workflow engine will be added later.

            execution.setResult(
                    "Workflow executed successfully"
            );

            execution.setStatus(ExecutionStatus.COMPLETED);
            execution.setCompletedAt(LocalDateTime.now());

        } catch (Exception exception) {

            execution.setStatus(ExecutionStatus.FAILED);
            execution.setError(exception.getMessage());
            execution.setCompletedAt(LocalDateTime.now());
        }

        execution = executionRepository.save(execution);

        return toResponse(execution);
    }


    @Override
    public List<WorkflowExecutionResponse> getExecutionHistory(Long workflowId) {

        User currentUser = getCurrentUser();

        Workflow workflow = workflowRepository.findById(workflowId)
                .orElseThrow(() ->
                        new RuntimeException("Workflow not found"));

        validateOwnership(workflow, currentUser);

        return executionRepository.findByWorkflowId(workflowId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext()
                        .getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }

    private void validateOwnership(
            Workflow workflow,
            User currentUser) {

        boolean isOwner =
                workflow.getOwner().getId()
                        .equals(currentUser.getId());

        if (!isOwner) {
            throw new RuntimeException(
                    "You are not authorized to execute this workflow"
            );
        }
    }

    private WorkflowExecutionResponse toResponse(
            WorkflowExecution execution) {

        return new WorkflowExecutionResponse(
                execution.getId(),
                execution.getWorkflow().getId(),
                execution.getStatus(),
                execution.getResult(),
                execution.getError(),
                execution.getStartedAt(),
                execution.getCompletedAt(),
                execution.getCreatedAt()
        );
    }
}