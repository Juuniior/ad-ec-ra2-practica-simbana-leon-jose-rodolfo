package org.educa.service;

import org.educa.dao.ReservaDAO;
import org.educa.dao.ReservaDAOImpl;
import org.educa.entity.ClienteEntity;
import org.educa.entity.ReservaEntity;

import java.sql.SQLException;
import java.util.List;

public class ReservaService {
    private final ReservaDAO reservaDAO = new ReservaDAOImpl();

    public List<ReservaEntity> findByCliente(ClienteEntity cliente) throws SQLException {
        return reservaDAO.findByCliente(cliente);
    }

    public void save(ReservaEntity reserva) throws SQLException {
    }
}
