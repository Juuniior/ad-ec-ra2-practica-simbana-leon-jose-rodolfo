package org.educa.dao;

import org.educa.entity.PasajeroEntity;

import java.sql.*;

public class PasajeroDAOImpl implements PasajeroDAO {
    /**
     * Guarda un pasajero en la base de datos y recupera el identificador generado.
     * @param connection conexión con la base de datos.
     * @param pasajero pasajero que se desea guardar.
     * @throws SQLException si ocurre un error durante el acceso a la base de datos.
     */
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
