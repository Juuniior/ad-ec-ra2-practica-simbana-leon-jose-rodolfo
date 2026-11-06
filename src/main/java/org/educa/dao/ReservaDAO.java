package org.educa.dao;

import org.educa.entity.ClienteEntity;
import org.educa.entity.ReservaEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ReservaDAO {
    List<ReservaEntity> findByCliente (ClienteEntity cliente) throws SQLException;
    boolean existsByCodReserva(Connection connection, String codReserva) throws SQLException;
    void save(Connection connection, ReservaEntity reserva) throws SQLException;
}
