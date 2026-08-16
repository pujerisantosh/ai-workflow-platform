package com.santosh.aiworkflowplatform.service;

import com.santosh.aiworkflowplatform.dto.response.WorkflowExecutionResponse;

import java.util.List;

public interface WorkflowExecutionService {

    WorkflowExecutionResponse executeWorkflow(Long workflowId);

    List<WorkflowExecutionResponse> getExecutionHistory(Long workflowId);
}