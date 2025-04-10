package com.mehak.dietplanner;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    Connection connection;
    public User(){
            connection =SqliteConnection.connector();
            if (connection == null) System.exit(1);
    }
    public boolean isDBConnected(){
        try{
            return !connection.isClosed();
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    public boolean login(String user, String pass) throws SQLException{
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from users where userID =? and password = ?";
        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user);
            preparedStatement.setString(2,pass);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }else {
                return false;
            }
        }catch (Exception e) {
            return false;
        }finally {
            preparedStatement.close();
            resultSet.close();
        }
    }
}
