package com.santosh.aiworkflowplatform.dto.response;

import com.santosh.aiworkflowplatform.entity.ExecutionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class WorkflowExecutionResponse {

    private Long id;
    private Long workflowId;
    private ExecutionStatus status;
    private String result;
    private String error;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private LocalDateTime createdAt;
}