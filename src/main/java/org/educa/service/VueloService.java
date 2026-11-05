package org.educa.service;

import org.educa.dao.VueloDAO;
import org.educa.dao.VueloDAOImpl;
import org.educa.entity.AeropuertoEntity;
import org.educa.entity.VueloEntity;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class VueloService {
    private final VueloDAO vueloDAO = new VueloDAOImpl();

    public List<VueloEntity> findByOrigen(AeropuertoEntity origen) throws SQLException {
        return vueloDAO.findByOrigen(origen);
    }

    public BigDecimal getPrecioBase(VueloEntity vuelo) {
        return null;
    }
}
