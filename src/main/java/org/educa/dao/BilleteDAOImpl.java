package org.educa.dao;

import org.educa.entity.BilleteEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BilleteDAOImpl implements BilleteDAO {
    /**
     * Guarda un billete en la base de datos y recupera el identificador generado.
     * @param connection conexión con la base de datos.
     * @param billete billete que se desea guardar.
     * @throws SQLException si ocurre un error durante el acceso a la base de datos.
     */
    @Override
    public void save(Connection connection, BilleteEntity billete) throws SQLException {
        String sql = "INSERT INTO billetes (id_pasajero, id_vuelo, id_asiento, precio) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id_billete"})) {
            ps.setInt(1, billete.getPasajero().getIdPasajero());
            ps.setInt(2, billete.getVuelo().getIdVuelo());
            ps.setInt(3, billete.getAsiento().getIdAsiento());
            ps.setBigDecimal(4, billete.getPrecio());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    billete.setIdBillete(rs.getInt(1));
                }
            }
        }

    }
}
