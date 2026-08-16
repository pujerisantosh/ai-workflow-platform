package com.santosh.aiworkflowplatform.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateWorkflowRequest {

    @NotBlank
    @Size(max = 150)
    private String name;

    @Size(max = 500)
    private String description;

    @NotBlank
    private String definition;
}