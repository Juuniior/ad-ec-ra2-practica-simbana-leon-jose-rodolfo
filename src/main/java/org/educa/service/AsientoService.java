package org.educa.service;

import org.educa.dao.AsientoDAO;
import org.educa.dao.AsientoDAOImpl;
import org.educa.entity.AsientoEntity;
import org.educa.entity.VueloEntity;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class AsientoService {
    private static final Map<String, BigDecimal> SUPLEMENTOS = Map.of(
            "TURISTA", new BigDecimal("0.00"),
            "PREMIUM_ECONOMY", new BigDecimal("35.00"),
            "BUSINESS", new BigDecimal("120.00"),
            "FIRST", new BigDecimal("250.00"));
    private final AsientoDAO asientoDAO = new AsientoDAOImpl();

    /**
     * Llama al DAO. busca los asientos disponibles de un vuelo y asigna el suplemento.
     * @param vuelo vuelo del que se quieren consultar los asientos disponibles.
     * @return lista de asientos disponibles con su suplemento.
     * @throws SQLException si ocurre un error durante el acceso a la base de datos.
     */
    public List<AsientoEntity> findDisponibles(VueloEntity vuelo) throws SQLException {
        List<AsientoEntity> asientos = asientoDAO.findDisponibles(vuelo);
        for (AsientoEntity asiento : asientos) {
            asiento.setSuplemento(SUPLEMENTOS.getOrDefault(asiento.getClase(), BigDecimal.ZERO));
        }
        return asientos;
    }
}
