package com.example.applicationrecruitment.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.example.applicationrecruitment.config.ConnectionConfig;

public class DatabaseHandler extends ConnectionConfig {

    Connection connection;

    public Connection getDatabaseConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:postgresql://" + dbHost + ":" + dbPort + "/" + dbName;
        Class.forName(dbDriver);
        connection = DriverManager.getConnection(connectionString, dbUser, dbPass);
        return connection;
    }
}