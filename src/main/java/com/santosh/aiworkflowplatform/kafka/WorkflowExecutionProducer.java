package com.santosh.aiworkflowplatform.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkflowExecutionProducer {

    private static final String TOPIC = "workflow-execution";

    private final KafkaTemplate<String, Long> kafkaTemplate;

    public void publish(Long executionId) {
        kafkaTemplate.send(
                TOPIC,
                String.valueOf(executionId),
                executionId
        );
    }
}