package com.santosh.aiworkflowplatform.service;

import com.santosh.aiworkflowplatform.dto.response.WorkflowExecutionResponse;

import java.util.List;

public interface WorkflowExecutionService {

    WorkflowExecutionResponse createExecution(Long workflowId);

    void executeWorkflow(Long executionId);

    List<WorkflowExecutionResponse> getExecutionHistory(Long workflowId);
}