package org.educa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BilleteEntity {
    private Integer idBillete;
    private PasajeroEntity pasajero = new PasajeroEntity();
    private VueloEntity vuelo = new VueloEntity();
    private AsientoEntity asiento = new AsientoEntity();
    private BigDecimal precio;
}
