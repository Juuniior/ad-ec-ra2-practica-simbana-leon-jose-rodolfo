package org.educa.dao;

import org.educa.entity.ClienteEntity;

import java.sql.SQLException;

public interface ClienteDAO {
    ClienteEntity findByDni(String dni) throws SQLException;
    void saveCliente(ClienteEntity cliente) throws SQLException;
}
