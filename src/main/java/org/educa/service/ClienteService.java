package org.educa.service;

import org.educa.dao.ClienteDAO;
import org.educa.dao.ClienteDAOImpl;
import org.educa.entity.ClienteEntity;

import java.sql.SQLException;

public class ClienteService {
    ClienteDAO clienteDAO = new ClienteDAOImpl();

    public ClienteEntity findByDNI(String dni) throws SQLException {
        return clienteDAO.findByDni(dni);
    }

    public void saveCliente(ClienteEntity cliente) throws SQLException {
        clienteDAO.saveCliente(cliente);
    }
}
