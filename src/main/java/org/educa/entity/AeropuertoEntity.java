package org.educa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AeropuertoEntity {
    private Integer idAeropuerto;
    private String codIata;
    private String nombre;
    private String ciudad;
    private String pais;
}
