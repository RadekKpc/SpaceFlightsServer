package com.spaceflights.connection;

import java.sql.*;

public class ConnectionUrl {

    public final String connectionUrl;

    public ConnectionUrl() {
        this.connectionUrl  = "jdbc:sqlserver://localhost\\DESKTOP-SIJST5Q:1433;user=root;password=1234";
    }

    public String getConnectionUrl() {
        return connectionUrl;
    }
}
