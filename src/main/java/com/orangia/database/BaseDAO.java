package com.orangia.database;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class BaseDAO {

    protected Connection getConnection()
            throws SQLException {

        return DatabaseConnection.getConnection();
    }
}