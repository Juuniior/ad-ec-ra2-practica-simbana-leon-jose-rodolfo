package org.educa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsientoEntity {
    private Integer idAsiento;
    private Integer idAvion;
    private String codAsiento;
    private String clase;
    private BigDecimal suplemento;
}
