package com.mehak.dietplanner.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class ConnectDB {
    private Connection connection;
    private Logger logger = Logger.getLogger(this.getClass().getName());

    public void getConnection(){
        try{
            if(connection == null || connection.isClosed()){
                connection = DriverManager.getConnection("jdbc:sqlite:DietPlanner.db");
                logger.info("Connected to Database");
            }
        }catch (SQLException e){
            logger.info(e.toString());
        }
    }
    public void closeConnection(){
        try {
            if(connection != null){
                connection.close();
                logger.info("Disconnected from Database");
            }
        } catch (SQLException e){
            logger.info(e.toString());
        }
    }
}
