package org.educa.dao;

import org.educa.entity.AeropuertoEntity;
import org.educa.entity.VueloEntity;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface VueloDAO {
    List<VueloEntity> findByOrigen(AeropuertoEntity origen) throws SQLException;
}
