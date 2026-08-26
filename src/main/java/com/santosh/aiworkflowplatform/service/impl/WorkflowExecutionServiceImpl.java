package com.santosh.aiworkflowplatform.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.santosh.aiworkflowplatform.dto.response.WorkflowExecutionResponse;
import com.santosh.aiworkflowplatform.entity.*;
import com.santosh.aiworkflowplatform.exception.WorkflowInactiveException;
import com.santosh.aiworkflowplatform.repository.UserRepository;
import com.santosh.aiworkflowplatform.repository.WorkflowExecutionRepository;
import com.santosh.aiworkflowplatform.repository.WorkflowRepository;
import com.santosh.aiworkflowplatform.service.WorkflowExecutionService;
import com.santosh.aiworkflowplatform.workflow.WorkflowDefinition;
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
    private final ObjectMapper objectMapper;

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

            WorkflowDefinition definition =
                    parseDefinition(workflow.getDefinition());

            StringBuilder result = new StringBuilder();

            definition.getSteps()
                    .stream()
                    .sorted((step1, step2) ->
                            Integer.compare(
                                    step1.getOrder(),
                                    step2.getOrder()
                            ))
                    .forEach(step -> {

                        result.append("Step ")
                                .append(step.getOrder())
                                .append(": ")
                                .append(step.getName())
                                .append(" executed successfully")
                                .append(System.lineSeparator());
                    });

            execution.setResult(result.toString());

            execution.setStatus(ExecutionStatus.COMPLETED);
            execution.setCompletedAt(LocalDateTime.now());

        } catch (Exception exception) {

            execution.setStatus(ExecutionStatus.FAILED);
            execution.setError(exception.getMessage());
            execution.setCompletedAt(LocalDateTime.now());
        }

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


        validateOwnership(workflow, currentUser);

        if (workflow.getStatus() == WorkflowStatus.INACTIVE) {
            throw new WorkflowInactiveException(
                    "Workflow is inactive and cannot be executed"
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


    private WorkflowDefinition parseDefinition(String definition) {

        try {
            return objectMapper.readValue(
                    definition,
                    WorkflowDefinition.class
            );
        } catch (JsonProcessingException exception) {
            throw new IllegalArgumentException(
                    "Invalid workflow definition",
                    exception
            );
        }
    }
}