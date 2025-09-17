package com.seidelsoft.ERPBackend.endereco.model;

import com.seidelsoft.ERPBackend.system.model.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cidade")
@SequenceGenerator(name = "cidade_generator", sequenceName = "seq_cidade", allocationSize = 1)
public class Cidade extends BaseEntity {

    @Id
    @Override
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cidade_generator")
    public Long getId(){
        return super.getId();
    }

	@Column(name = "nome", length = 255, nullable = false)
	@NotNull(message = "Nome não pode ser null")
	private String nome;

	@Column(name = "ibge", length = 10, nullable = false)
	private String ibge;

	@Column(name = "latitude", nullable = true)
	private Double latitude;

	@Column(name = "longitude", nullable = true)
	private Double longitude;

	@ManyToOne
	@JoinColumn(name = "estado", referencedColumnName = "id")
	private Estado estado;

}
