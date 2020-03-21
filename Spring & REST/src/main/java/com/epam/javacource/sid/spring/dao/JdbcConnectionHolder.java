package com.epam.javacource.sid.spring.dao;

import com.epam.javacource.sid.spring.exceptions.DatabaseCommunicationException;
import lombok.SneakyThrows;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JdbcConnectionHolder {

    private DataSource dataSource;

    private ThreadLocal<Connection> connection = ThreadLocal.withInitial(() -> {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new DatabaseCommunicationException("Could not create connection", e);
        }
    });

    public JdbcConnectionHolder(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @SneakyThrows
    public void startTransaction() {
        getConnection().setAutoCommit(false);
    }

    /**
     * Don't use try-with-resources with this connection,
     * otherwise you will not be able to manage transaction outside try-with-resources block.
     */
    @SneakyThrows
    public Connection getConnection() {
        final Connection connection = this.connection.get();
        // refresh connection if closed or empty
        if (connection == null || connection.isClosed()) {
            this.connection.set(dataSource.getConnection());
        }
        return this.connection.get();
    }

    @SneakyThrows
    public void commitTransaction() {
        final Connection connection = this.connection.get();
        connection.commit();
    }

    @SneakyThrows
    public void rollbackTransaction() {
        final Connection connection = this.connection.get();
        connection.rollback();
    }

    @SneakyThrows
    public void closeConnection() {
        this.connection.get().close();
    }
}
