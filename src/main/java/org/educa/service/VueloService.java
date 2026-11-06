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

    /**
     * Busca los vuelos cuyo aeropuerto de origen coincide con el indicado.
     * @param origen aeropuerto desde el que salen los vuelos.
     * @return lista de vuelos encontrados.
     * @throws SQLException si ocurre un error durante el acceso a la base de datos.
     */
    public List<VueloEntity> findByOrigen(AeropuertoEntity origen) throws SQLException {
        return vueloDAO.findByOrigen(origen);
    }

    /**
     * Calcula el precio base de un vuelo en función de su duración.
     * @param vuelo vuelo cuyo precio base se desea calcular.
     * @return precio base del vuelo redondeado a dos decimales.
     */
    public BigDecimal getPrecioBase(VueloEntity vuelo) {
        long minutos = Duration.between(vuelo.getFecSalida(), vuelo.getFecLlegada()).toMinutes();
        return PRECIO_POR_MINUTO.multiply(BigDecimal.valueOf((minutos)).setScale(2, RoundingMode.HALF_UP));
    }
}
