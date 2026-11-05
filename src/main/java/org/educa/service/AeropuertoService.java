package org.educa.service;

import org.educa.dao.AeropuertoDAO;
import org.educa.dao.AeropuertoDAOImpl;
import org.educa.entity.AeropuertoEntity;

import java.sql.SQLException;
import java.util.List;

public class AeropuertoService {
    private final AeropuertoDAO aeropuertoDAO = new AeropuertoDAOImpl();

    public List<AeropuertoEntity> findAll() throws SQLException {
        return aeropuertoDAO.findAll();
    }
}
