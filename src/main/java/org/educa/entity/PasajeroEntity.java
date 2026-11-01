package org.educa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasajeroEntity {
    private Integer idPasajero;
    private Integer idReserva;
    private String dni;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private String email;
    private String telefono;
    private LocalDate fecNac;
}
