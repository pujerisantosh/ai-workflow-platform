package com.santosh.aiworkflowplatform.repository;

import com.santosh.aiworkflowplatform.entity.WorkflowExecution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkflowExecutionRepository
        extends JpaRepository<WorkflowExecution, Long> {

    List<WorkflowExecution> findByWorkflowId(Long workflowId);
}