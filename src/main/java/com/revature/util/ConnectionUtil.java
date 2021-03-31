package com.revature.util;

import com.revature.config.DatabaseProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Should by used by ReimbursementDao
public class ConnectionUtil {
    private static ConnectionUtil instance;

    public static ConnectionUtil getInstance() {
        if(instance == null){
            instance = new ConnectionUtil();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            DatabaseProperties props = DatabaseProperties.getInstance();

            String connectionTemplate = props.getProfile();
            String url = props.getPropertyByKey(connectionTemplate+".url")+"?currentSchema="+props.getSchema();
            return DriverManager.getConnection(
                    url,
                    props.getPropertyByKey(connectionTemplate+".username"),
                    props.getPropertyByKey(connectionTemplate+".password"));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
