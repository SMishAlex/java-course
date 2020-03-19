package com.epam.javacource.sid.spring.dao;

import com.epam.javacource.sid.spring.exceptions.DatabaseCommunicationException;
import lombok.SneakyThrows;
import lombok.experimental.Delegate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JdbcConnectionHolder {

    private DataSource dataSource;

    private ThreadLocal<ConnectionWrapper> connection = ThreadLocal.withInitial(() -> {
        try {
            return new ConnectionWrapper(dataSource.getConnection());
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

    @SneakyThrows
    public Connection getConnection() {
        final Connection connection = this.connection.get();
        // refresh connection if closed or empty
        if (connection == null || connection.isClosed()) {
            this.connection.set(new ConnectionWrapper(dataSource.getConnection()));
        }
        return this.connection.get();
    }

    @SneakyThrows
    public void commitTransaction() {
        final ConnectionWrapper connection = this.connection.get();
        connection.commit();
        connection.closeIfCalledPreviously();
    }

    @SneakyThrows
    public void rollbackTransaction() {
        final ConnectionWrapper connection = this.connection.get();
        connection.rollback();
        connection.closeIfCalledPreviously();
    }

    public static class ConnectionWrapper implements Connection {

        @Delegate(excludes = AutoCloseable.class)
        private final Connection delegate;
        private boolean closeCalled = false;

        public ConnectionWrapper(Connection delegate) {
            this.delegate = delegate;
        }

        @SneakyThrows
        @Override
        public void close() {
            if (delegate.getAutoCommit()) {
                delegate.close();
            } else {
                closeCalled = true;
            }
        }

        @SneakyThrows
        public void closeIfCalledPreviously() {
            if (closeCalled) {
                delegate.close();
            }
        }
    }
}
