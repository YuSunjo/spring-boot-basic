package com.example.springbasic.DB_1.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.example.springbasic.DB_1.connection.ConnectionConst.*;

public class DBConnectionUtil {

    public static Connection getConnection() {
        try {
            // 커넥션이 필요하면 요청
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
    }

}
