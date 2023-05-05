package br.com.sinqia.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnetionFactory {

    String url;
    String username;
    String password;

    public ConnetionFactory() {
        this.url = "jdbc:mysql://localhost:3306/tapronto";
        this.username = "root";
        this.password = "rootpassword";
    }

    public Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
