package org.educa.service;

import org.educa.dao.PasajeroDAO;
import org.educa.dao.PasajeroDAOImpl;
import org.educa.entity.PasajeroEntity;

import java.sql.Connection;
import java.sql.SQLException;

public class PasajeroService {
        private final PasajeroDAO pasajeroDAO = new PasajeroDAOImpl();

    public void save(Connection connection, PasajeroEntity pasajero) throws SQLException {
        pasajeroDAO.save(connection, pasajero);
    }
}
