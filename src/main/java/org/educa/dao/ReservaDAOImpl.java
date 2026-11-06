package org.educa.dao;

import org.educa.entity.ClienteEntity;
import org.educa.entity.ReservaEntity;
import org.educa.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAOImpl implements ReservaDAO {
    @Override
    public List<ReservaEntity> findByCliente(ClienteEntity cliente) throws SQLException {
        String sql = "SELECT id_reserva, cod_reserva, fec_reserva, estado " +
                "FROM reservas WHERE id_cliente = ? ORDER BY fec_reserva";
        List<ReservaEntity> reservas = new ArrayList<>();
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, cliente.getIdCliente());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ReservaEntity reserva = new ReservaEntity();
                    reserva.setIdReserva(rs.getInt("id_reserva"));
                    reserva.setCodReserva(rs.getString("cod_reserva"));
                    reserva.setCliente(cliente);
                    reserva.setFecReserva(rs.getTimestamp("fec_reserva").toLocalDateTime());
                    reserva.setEstado(rs.getString("estado"));
                    reservas.add(reserva);
                }
            }
        }
        return reservas;
    }

    @Override
    public boolean existsByCodReserva(Connection connection, String codReserva) throws SQLException {
        String sql = "SELECT 1 FROM reservas WHERE cod_reserva = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, codReserva);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    @Override
    public void save(Connection connection, ReservaEntity reserva) throws SQLException {
        // fec_reserva y estado toman sus valores por defecto (fecha actual y CONFIRMADA)
        String sql = "INSERT INTO reservas (cod_reserva, id_cliente) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql,
                new String[]{"id_reserva", "fec_reserva", "estado"})) {
            ps.setString(1, reserva.getCodReserva());
            ps.setInt(2, reserva.getCliente().getIdCliente());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    reserva.setIdReserva(rs.getInt("id_reserva"));
                    reserva.setFecReserva(rs.getTimestamp("fec_reserva").toLocalDateTime());
                    reserva.setEstado(rs.getString("estado"));
                }
            }
        }
    }
}
