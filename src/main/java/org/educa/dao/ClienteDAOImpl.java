package org.educa.dao;

import org.educa.entity.ClienteEntity;
import org.educa.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteDAOImpl implements ClienteDAO {

    @Override
    public ClienteEntity findByDni(String dni) throws SQLException {
        String sql = "SELECT id_cliente, dni, nombre, p_apellido, s_apellido, email, telefono " +
                "FROM clientes WHERE dni = ?";
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, dni);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new ClienteEntity(
                            rs.getInt("id_cliente"),
                            rs.getString("dni"),
                            rs.getString("nombre"),
                            rs.getString("p_apellido"),
                            rs.getString("s_apellido"),
                            rs.getString("email"),
                            rs.getString("telefono"));
                }
                return null;
            }
        }
    }

    @Override
    public void saveCliente(ClienteEntity cliente) throws SQLException {
        String sql = "INSERT INTO clientes (dni, nombre, p_apellido, s_apellido, email, telefono) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id_cliente"})) {
            ps.setString(1, cliente.getDni());
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getPrimerApellido());
            ps.setString(4, cliente.getSegundoApellido());
            ps.setString(5, cliente.getEmail());
            ps.setString(6, cliente.getTelefono());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    cliente.setIdCliente(rs.getInt(1));
                }
            }
        }
    }
}
