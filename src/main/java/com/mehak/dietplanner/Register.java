package com.mehak.dietplanner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class Register {
    public boolean register(String userID, String password, String email, int age, Double weight, Double height, String dietaryPref, String activityLevel){
        try{
            Connection connection = SqliteConnection.connector();
            Statement statement = connection.createStatement();
            String createTable = """
            CREATE TABLE IF NOT EXISTS users (
            userID TEXT PRIMARY KEY,
            password TEXT NOT NULL,
            email TEXT NOT NULL UNIQUE,
            age INTEGER,
            weight REAL,
            height REAL,
            activityLevel TEXT,
            dietaryPreferences TEXT
            );
            """;
            statement.execute(createTable);
            System.out.println("Table Created or Already Exists.");

            statement.close();
            String insertQuery = """
                    INSERT INTO users (userID, password, email,age,weight,height,activityLevel,dietaryPreferences)
                    VALUES (?,?,?,?,?,?,?,?);
                    """;
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1,userID);
            preparedStatement.setString(2,password);
            preparedStatement.setString(3,email);
            preparedStatement.setInt(4,age);
            preparedStatement.setDouble(5,weight);
            preparedStatement.setDouble(6,height);
            preparedStatement.setString(7,dietaryPref);
            preparedStatement.setString(8,activityLevel);

            preparedStatement.executeUpdate();
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
