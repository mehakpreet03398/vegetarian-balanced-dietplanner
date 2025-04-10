package com.mehak.dietplanner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UpdateDetails {

    public boolean updateAge(String userID, int age){
        String user=userID;
        int a = age;
        try{
            Connection connection = SqliteConnection.connector();
            String query= "UPDATE users SET age = ? WHERE userID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, a);
            preparedStatement.setString(2,user);
            int rowsAffected = preparedStatement.executeUpdate();
            if(rowsAffected > 0){
                return true;
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return false;
    }
    public boolean updateHeight(String userID, Double height){
        String user=userID;
        Double h = height;
        try{
            Connection connection = SqliteConnection.connector();
            String query= "UPDATE users SET height = ? WHERE userID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDouble(1, h);
            preparedStatement.setString(2,user);

            int rowsAffected = preparedStatement.executeUpdate();
            if(rowsAffected > 0){
                return true;
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return false;
    }
    public boolean updateWeight(String userID, Double weight){
        String user=userID;
        Double w = weight;
        try{
            Connection connection = SqliteConnection.connector();
            String query= "UPDATE users SET weight = ? WHERE userID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDouble(1, w);
            preparedStatement.setString(2,user);

            int rowsAffected = preparedStatement.executeUpdate();
            if(rowsAffected > 0){
                return true;
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return false;
    }

}
