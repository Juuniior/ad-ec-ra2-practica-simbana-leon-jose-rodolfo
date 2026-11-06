package org.educa.service;

import org.educa.dao.VueloDAO;
import org.educa.dao.VueloDAOImpl;
import org.educa.entity.AeropuertoEntity;
import org.educa.entity.VueloEntity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.time.Duration;
import java.util.List;

public class VueloService {
    private static final BigDecimal PRECIO_POR_MINUTO = new BigDecimal("0.75");
    private final VueloDAO vueloDAO = new VueloDAOImpl();

    public List<VueloEntity> findByOrigen(AeropuertoEntity origen) throws SQLException {
        return vueloDAO.findByOrigen(origen);
    }

    public BigDecimal getPrecioBase(VueloEntity vuelo) {
        long minutos = Duration.between(vuelo.getFecSalida(), vuelo.getFecLlegada()).toMinutes();
        return PRECIO_POR_MINUTO.multiply(BigDecimal.valueOf((minutos)).setScale(2, RoundingMode.HALF_UP));
    }
}
