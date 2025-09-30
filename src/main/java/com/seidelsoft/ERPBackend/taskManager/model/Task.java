package com.seidelsoft.ERPBackend.taskManager.model;

import com.seidelsoft.ERPBackend.system.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "task")
@SequenceGenerator(name = "task_generator", sequenceName = "seq_task", allocationSize = 1)
public class Task extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_generator")
    private Long id;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "description", length = 255, nullable = false)
    private String description;

    @Column(name = "cron_expression", length = 20, nullable = false)
    private String cronExpression;

    @Column(name = "task_type", nullable = false)
    private Long taskType;

    @Column(name = "active", nullable = false)
    private Boolean active;
}

