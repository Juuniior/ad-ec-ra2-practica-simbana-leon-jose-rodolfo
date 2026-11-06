package org.educa.dao;

import org.educa.entity.PasajeroEntity;

import java.sql.*;

public class PasajeroDAOImpl implements PasajeroDAO {
    @Override
    public void save(Connection connection, PasajeroEntity pasajero) throws SQLException {
        String sql = "INSERT INTO pasajeros (id_reserva, dni, nombre, p_apellido, s_apellido, email, telefono, fec_nac) "+
                "(VALUES (?, ?, ?, ?, ?, ?, ?, ?))";
        try (PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id_pasajero"})){
            ps.setInt(1, pasajero.getIdReserva());
            ps.setString(2, pasajero.getDni());
            ps.setString(3, pasajero.getNombre());
            ps.setString(4, pasajero.getPrimerApellido());
            ps.setString(5, pasajero.getSegundoApellido());
            ps.setString(6, pasajero.getEmail());
            ps.setString(7, pasajero.getTelefono());
            ps.setDate(8, Date.valueOf(pasajero.getFecNac()));
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()){
                if (rs.next()) {
                    pasajero.setIdPasajero(rs.getInt(1));
                }
            }
        }
    }
}
