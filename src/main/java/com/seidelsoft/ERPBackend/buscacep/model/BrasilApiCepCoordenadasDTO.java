package com.seidelsoft.ERPBackend.buscacep.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BrasilApiCepCoordenadasDTO {
    private String longitude;
    private String latitude;

    @Override
    public String toString() {
        return "BrasilApiCepCoordenadasDTO{" +
                "longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                '}';
    }
}
