package com.santosh.aiworkflowplatform.dto.response;

import com.santosh.aiworkflowplatform.entity.WorkflowStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class WorkflowResponse {

    private Long id;
    private String name;
    private String description;
    private String definition;
    private WorkflowStatus status;
    private Long ownerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}