package com.santosh.aiworkflowplatform.workflow;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WorkflowDefinition {

    private List<WorkflowStep> steps;
}
