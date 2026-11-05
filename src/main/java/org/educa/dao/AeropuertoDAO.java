package org.educa.dao;

import org.educa.entity.AeropuertoEntity;

import java.sql.SQLException;
import java.util.List;

public interface AeropuertoDAO {
    List<AeropuertoEntity> findAll() throws SQLException;
}
