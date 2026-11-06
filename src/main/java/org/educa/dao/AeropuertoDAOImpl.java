package org.educa.dao;

import org.educa.entity.AeropuertoEntity;
import org.educa.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AeropuertoDAOImpl implements AeropuertoDAO {

    /**
     * Obtiene todos los aeropuertos registrados en la base de datos.
     * @return lista de aeropuertos encontrados.
     * @throws SQLException si ocurre un error durante el acceso a la base de datos.
     */
    @Override
    public List<AeropuertoEntity> findAll() throws SQLException {
        String sql = "SELECT id_aeropuerto, cod_iata, nombre, ciudad, pais " +
                "FROM aeropuertos ORDER BY id_aeropuerto";
        List<AeropuertoEntity> aeropuertos = new ArrayList<>();
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                aeropuertos.add(new AeropuertoEntity(
                        rs.getInt("id_aeropuerto"),
                        rs.getString("cod_iata"),
                        rs.getString("nombre"),
                        rs.getString("ciudad"),
                        rs.getString("pais")
            ));
            }

        }
        return aeropuertos;
    }
}
