package org.educa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteEntity {
    private Integer idCliente;
    private String dni;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private String email;
    private String telefono;
}
