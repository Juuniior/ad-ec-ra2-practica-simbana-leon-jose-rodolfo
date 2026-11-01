package org.educa.service;

import org.educa.entity.AeropuertoEntity;
import org.educa.entity.VueloEntity;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class VueloService {


    public List<VueloEntity> findByOrigen(AeropuertoEntity origen) throws SQLException {
        return null;
    }

    public BigDecimal getPrecioBase(VueloEntity vuelo) {
        return null;
    }
}
