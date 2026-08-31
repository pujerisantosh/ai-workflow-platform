package com.santosh.aiworkflowplatform.kafka;

import com.santosh.aiworkflowplatform.service.WorkflowExecutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkflowExecutionConsumer {

    private final WorkflowExecutionService workflowExecutionService;

    @KafkaListener(
            topics = "workflow-execution",
            groupId = "workflow-execution-group"
    )
    public void consume(Long executionId) {

        workflowExecutionService.executeWorkflow(executionId);
    }
}