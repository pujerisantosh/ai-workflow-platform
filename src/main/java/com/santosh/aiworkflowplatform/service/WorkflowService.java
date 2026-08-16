package com.santosh.aiworkflowplatform.service;

import com.santosh.aiworkflowplatform.dto.request.CreateWorkflowRequest;
import com.santosh.aiworkflowplatform.dto.request.UpdateWorkflowRequest;
import com.santosh.aiworkflowplatform.dto.response.WorkflowResponse;

import java.util.List;

public interface WorkflowService {

    WorkflowResponse createWorkflow(CreateWorkflowRequest request);

    List<WorkflowResponse> getMyWorkflows();

    WorkflowResponse getWorkflow(Long workflowId);

    WorkflowResponse updateWorkflow(
            Long workflowId,
            UpdateWorkflowRequest request
    );

    void deleteWorkflow(Long workflowId);
}