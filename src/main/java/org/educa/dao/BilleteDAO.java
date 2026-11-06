package org.educa.dao;

import org.educa.entity.BilleteEntity;

import java.sql.Connection;
import java.sql.SQLException;

public interface BilleteDAO {
    void save(Connection connection, BilleteEntity billete) throws SQLException;
}
