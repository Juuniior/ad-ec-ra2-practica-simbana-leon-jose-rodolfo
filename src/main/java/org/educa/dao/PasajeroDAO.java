package org.educa.dao;

import org.educa.entity.PasajeroEntity;

import java.sql.Connection;
import java.sql.SQLException;

public interface PasajeroDAO {
    void save(Connection connection, PasajeroEntity pasajero) throws SQLException;
}
