package org.educa.service;

import org.educa.dao.PasajeroDAO;
import org.educa.dao.PasajeroDAOImpl;
import org.educa.entity.PasajeroEntity;

import java.sql.Connection;
import java.sql.SQLException;

public class PasajeroService {
    private final PasajeroDAO pasajeroDAO = new PasajeroDAOImpl();

    /**
     * Llama al DAO y guarda un pasajero en la base de datos.
     * @param connection conexión con la base de datos.
     * @param pasajero pasajero que se desea guardar.
     * @throws SQLException si ocurre un error durante el acceso a la base de datos.
     */
    public void save(Connection connection, PasajeroEntity pasajero) throws SQLException {
        pasajeroDAO.save(connection, pasajero);
    }
}
