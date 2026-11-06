package org.educa.service;

import org.educa.dao.AeropuertoDAO;
import org.educa.dao.AeropuertoDAOImpl;
import org.educa.entity.AeropuertoEntity;

import java.sql.SQLException;
import java.util.List;

public class AeropuertoService {
    private final AeropuertoDAO aeropuertoDAO = new AeropuertoDAOImpl();

    /**
     * Llama al DAO y obtiene los aeropuertos registrados en la base de datos.
     * @return lista de todos los aeropuertos encontrados.
     * @throws SQLException si ocurre un error durante el acceso a la base de datos.
     */
    public List<AeropuertoEntity> findAll() throws SQLException {
        return aeropuertoDAO.findAll();
    }
}
