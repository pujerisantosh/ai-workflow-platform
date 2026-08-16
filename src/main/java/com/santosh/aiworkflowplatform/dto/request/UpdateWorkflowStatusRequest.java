package com.santosh.aiworkflowplatform.dto.request;

import com.santosh.aiworkflowplatform.entity.WorkflowStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateWorkflowStatusRequest {

    @NotNull
    private WorkflowStatus status;
}