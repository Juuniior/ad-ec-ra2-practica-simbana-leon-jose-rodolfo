package org.educa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VueloEntity {
    private Integer idVuelo;
    private String numVuelo;
    private AeropuertoEntity origen = new AeropuertoEntity();
    private AeropuertoEntity destino = new AeropuertoEntity();
    private Integer idAvion;
    private LocalDateTime fecSalida;
    private LocalDateTime fecLlegada;
    private String estado;
}
