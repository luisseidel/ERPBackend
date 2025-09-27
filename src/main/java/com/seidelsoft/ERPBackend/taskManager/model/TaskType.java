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
@Table(name = "task_type")
@SequenceGenerator(name = "task_type_generator", sequenceName = "seq_task_type", allocationSize = 1)
public class TaskType extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_type_generator")
    private Long id;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

}
