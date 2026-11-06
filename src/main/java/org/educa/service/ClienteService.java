package org.educa.service;

import org.educa.dao.ClienteDAO;
import org.educa.dao.ClienteDAOImpl;
import org.educa.entity.ClienteEntity;

import java.sql.SQLException;

public class ClienteService {
    ClienteDAO clienteDAO = new ClienteDAOImpl();

    /**
     * Llama al DAO y busca un cliente por su DNI.
     * @param dni DNI del usuario que se va a buscar.
     * @return Cliente encontrado o null si no ha encontrado un cliente con ese DNI.
     * @throws SQLException si ocurre un error durante el acceso a la base de datos.
     */
    public ClienteEntity findByDNI(String dni) throws SQLException {
        return clienteDAO.findByDni(dni);
    }

    /**
     * LLama al DAO, para guardar un cliente nuevo.
     * @param cliente objeto ClienteEntity con los datos del cliente que se quiere guardar.
     * @throws SQLException si ocurre un error durante el acceso a la base de datos.
     */
    public void saveCliente(ClienteEntity cliente) throws SQLException {
        clienteDAO.saveCliente(cliente);
    }
}
