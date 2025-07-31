package com.seidelsoft.ERPBackend.buscacep.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BrasilApiLocationDTO {
    private String type;
    private BrasilApiCepCoordenadasDTO coordinates;

    @Override
    public String toString() {
        return "BrasilApiLocationDTO{" +
                "type='" + type + '\'' +
                ", coordinates=" + coordinates +
                '}';
    }
}
