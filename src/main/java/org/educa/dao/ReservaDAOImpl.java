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
}
