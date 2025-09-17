package com.seidelsoft.ERPBackend.endereco.model;

import com.seidelsoft.ERPBackend.system.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "estado")
@SequenceGenerator(name = "estado_generator", sequenceName = "seq_estado", allocationSize = 1)
public class Estado extends BaseEntity {

    @Id
    @Override
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "estado_generator")
    public Long getId(){
        return super.getId();
    }

	@Column(name = "nome", length = 255, nullable = false)
	private String nome;

	@Column(name = "uf", length = 2, nullable = false)
	private String uf;

	@Column(name = "ibge", nullable = false)
	private Integer ibge;

	@ManyToOne
	@JoinColumn(name = "pais", referencedColumnName = "id")
	private Pais pais;
}
