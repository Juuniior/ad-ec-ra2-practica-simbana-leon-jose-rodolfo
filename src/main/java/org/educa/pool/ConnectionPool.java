package org.educa.pool;

import org.apache.commons.dbcp.BasicDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConnectionPool {

    private static BasicDataSource basicDataSource;

    private ConnectionPool() {
        throw new IllegalStateException();
    }

    public static BasicDataSource getDataSource() {
        if (basicDataSource == null) {
            Properties properties = new Properties();
            try (InputStream input = ConnectionPool.class.getClassLoader().getResourceAsStream("database.properties")) {
                properties.load(input);

                basicDataSource = new BasicDataSource();
                basicDataSource.setUrl(properties.getProperty("db.url"));
                basicDataSource.setUsername(properties.getProperty("db.username"));
                basicDataSource.setPassword(properties.getProperty("db.password"));
                basicDataSource.setMaxWait(Long.parseLong(properties.getProperty("db.timeout"))); // 5000 milisegundos = 5 segundos
                basicDataSource.setMinIdle(Integer.parseInt(properties.getProperty("db.initialSize")));
                basicDataSource.setMaxIdle(Integer.parseInt(properties.getProperty("db.maxTotal")));

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return basicDataSource;
    }
}
