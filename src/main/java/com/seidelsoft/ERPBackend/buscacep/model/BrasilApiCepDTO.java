package com.seidelsoft.ERPBackend.buscacep.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BrasilApiCepDTO {

	private String cep;
	private String state;
	private String city;
	private String neighborhood;
	private String street;
	private BrasilApiLocationDTO location;

	@Override
	public String toString() {
		return "BrasilApiCepDTO{" +
				"cep='" + cep + '\'' +
				", state='" + state + '\'' +
				", city='" + city + '\'' +
				", neighborhood='" + neighborhood + '\'' +
				", street='" + street + '\'' +
				", location=" + location +
				'}';
	}
}
