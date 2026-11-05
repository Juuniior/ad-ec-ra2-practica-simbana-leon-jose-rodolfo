package org.educa.dao;

import org.educa.entity.ClienteEntity;
import org.educa.entity.ReservaEntity;

import java.sql.SQLException;
import java.util.List;

public interface ReservaDAO {
    List<ReservaEntity> findByCliente (ClienteEntity cliente) throws SQLException;
}
