package com.santosh.aiworkflowplatform.repository;

import com.santosh.aiworkflowplatform.entity.Workflow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkflowRepository extends JpaRepository<Workflow, Long> {

    List<Workflow> findByOwnerId(Long ownerId);
}