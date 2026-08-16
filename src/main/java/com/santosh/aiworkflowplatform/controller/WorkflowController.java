package com.santosh.aiworkflowplatform.controller;

import com.santosh.aiworkflowplatform.dto.request.CreateWorkflowRequest;
import com.santosh.aiworkflowplatform.dto.request.UpdateWorkflowRequest;
import com.santosh.aiworkflowplatform.dto.request.UpdateWorkflowStatusRequest;
import com.santosh.aiworkflowplatform.dto.response.WorkflowResponse;
import com.santosh.aiworkflowplatform.service.WorkflowService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workflows")
@RequiredArgsConstructor
public class WorkflowController {

    private final WorkflowService workflowService;

    @PostMapping
    public ResponseEntity<WorkflowResponse> createWorkflow(
            @Valid @RequestBody CreateWorkflowRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(workflowService.createWorkflow(request));
    }

    @GetMapping
    public ResponseEntity<List<WorkflowResponse>> getMyWorkflows() {

        return ResponseEntity.ok(
                workflowService.getMyWorkflows()
        );
    }

    @GetMapping("/{workflowId}")
    public ResponseEntity<WorkflowResponse> getWorkflow(
            @PathVariable Long workflowId) {

        return ResponseEntity.ok(
                workflowService.getWorkflow(workflowId)
        );
    }

    @PutMapping("/{workflowId}")
    public ResponseEntity<WorkflowResponse> updateWorkflow(
            @PathVariable Long workflowId,
            @Valid @RequestBody UpdateWorkflowRequest request) {

        return ResponseEntity.ok(
                workflowService.updateWorkflow(
                        workflowId,
                        request
                )
        );
    }

    @DeleteMapping("/{workflowId}")
    public ResponseEntity<Void> deleteWorkflow(
            @PathVariable Long workflowId) {

        workflowService.deleteWorkflow(workflowId);

        return ResponseEntity.noContent().build();
    }


    @PatchMapping("/{workflowId}/status")
    public ResponseEntity<WorkflowResponse> updateWorkflowStatus(
            @PathVariable Long workflowId,
            @Valid @RequestBody UpdateWorkflowStatusRequest request) {

        return ResponseEntity.ok(
                workflowService.updateWorkflowStatus(workflowId, request)
        );
    }
}