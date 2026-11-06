package org.educa.dao;

import org.educa.entity.AsientoEntity;
import org.educa.entity.VueloEntity;
import org.educa.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AsientoDAOImpl implements AsientoDAO {
    @Override
    public List<AsientoEntity> findDisponibles(VueloEntity vuelo) throws SQLException {
        String sql = "SELECT a.id_asiento, a.id_avion, a.cod_asiento, a.clase " +
                "FROM asientos a " +
                "WHERE a.id_avion = ? " +
                "AND NOT EXISTS (SELECT 1 FROM billetes b " +
                "WHERE b.id_asiento = a.id_asiento AND b.id_vuelo = ?) " +
                "ORDER BY a.id_asiento";
        List<AsientoEntity> asientos = new ArrayList<>();
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, vuelo.getIdAvion());
            ps.setInt(2, vuelo.getIdVuelo());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    AsientoEntity asiento = new AsientoEntity();
                    asiento.setIdAsiento(rs.getInt("id_asiento"));
                    asiento.setIdAvion(rs.getInt("id_avion"));
                    asiento.setCodAsiento(rs.getString("cod_asiento"));
                    asiento.setClase(rs.getString("clase"));
                    asientos.add(asiento);
                }
            }
        }
        return asientos;
    }
}
