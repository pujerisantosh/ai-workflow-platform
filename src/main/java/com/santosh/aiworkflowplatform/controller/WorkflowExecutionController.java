package com.santosh.aiworkflowplatform.controller;

import com.santosh.aiworkflowplatform.dto.response.WorkflowExecutionResponse;
import com.santosh.aiworkflowplatform.service.WorkflowExecutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workflows")
@RequiredArgsConstructor
public class WorkflowExecutionController {

    private final WorkflowExecutionService workflowExecutionService;

    @PostMapping("/{workflowId}/execute")
    public ResponseEntity<WorkflowExecutionResponse> executeWorkflow(
            @PathVariable Long workflowId) {

        return ResponseEntity.ok(
                workflowExecutionService.executeWorkflow(workflowId)
        );
    }

    @GetMapping("/{workflowId}/executions")
    public ResponseEntity<List<WorkflowExecutionResponse>> getExecutionHistory(
            @PathVariable Long workflowId) {

        return ResponseEntity.ok(
                workflowExecutionService.getExecutionHistory(workflowId)
        );
    }
}