package org.educa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaEntity {
    private Integer idReserva;
    private String codReserva;
    private ClienteEntity cliente = new ClienteEntity();
    private LocalDateTime fecReserva;
    private String estado;
    private List<BilleteEntity> billetes = new ArrayList<>();
}
