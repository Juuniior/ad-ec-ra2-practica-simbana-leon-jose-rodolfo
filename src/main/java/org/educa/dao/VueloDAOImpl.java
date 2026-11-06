package org.educa.dao;

import org.educa.entity.AeropuertoEntity;
import org.educa.entity.VueloEntity;
import org.educa.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VueloDAOImpl implements VueloDAO {
    @Override
    public List<VueloEntity> findByOrigen(AeropuertoEntity origen) throws SQLException {
        String sql = "SELECT v.id_vuelo, v.num_vuelo, v.id_avion, v.fec_salida, v.fec_llegada, v.estado, " +
                "d.id_aeropuerto, d.cod_iata, d.nombre, d.ciudad, d.pais " +
                "FROM vuelos v JOIN aeropuertos d ON v.id_destino = d.id_aeropuerto " +
                "WHERE v.id_origen = ? AND v.estado <> 'CANCELADO' " +
                "ORDER BY v.fec_salida";
        List<VueloEntity> vuelos = new ArrayList<>();
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, origen.getIdAeropuerto());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    AeropuertoEntity destino = new AeropuertoEntity(
                            rs.getInt("id_aeropuerto"),
                            rs.getString("cod_iata"),
                            rs.getString("nombre"),
                            rs.getString("ciudad"),
                            rs.getString("pais"));
                    vuelos.add(new VueloEntity(
                            rs.getInt("id_vuelo"),
                            rs.getString("num_vuelo"),
                            origen,
                            destino,
                            rs.getInt("id_avion"),
                            rs.getTimestamp("fec_salida").toLocalDateTime(),
                            rs.getTimestamp("fec_llegada").toLocalDateTime(),
                            rs.getString("estado")));
                }
            }
        }

        return vuelos;
    }
}
