package com.mehak.dietplanner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class NutritionalInfo {
    public double protein;
    public double carbs;
    public double fat;
    public String vitamins;
    public String minerals;

    public NutritionalInfo(double protein, double carbs, double fat, String vitamins, String minerals){
        this.protein=protein;
        this.carbs=carbs;
        this.fat=fat;
        this.vitamins=vitamins;
        this.minerals=minerals;
    }

    public void addNutritionalInfo(String recipeID){
        try(Connection connection = SqliteConnection.connector()){
            Statement statement =connection.createStatement();
            String createTable = """
                    CREATE TABLE IF NOT EXISTS nutritionalInfo(
                    recipeID TEXT PRIMARY KEY,
                    protein REAL,
                    carbs REAL,
                    fat REAL,
                    vitamins TEXT,
                    minerals TEXT
                    );
                    """;
            statement.execute(createTable);
            System.out.println("Nutritional Info Table created or Already");
            statement.close();

            String insertQuery = """
                    INSERT INTO nutritionalInfo (recipeID, protein, carbs, fat, vitamins, minerals)
                    VALUES(?,?,?,?,?,?);
                    """;
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, recipeID);
            preparedStatement.setDouble(2, protein);
            preparedStatement.setDouble(3,carbs);
            preparedStatement.setDouble(4,fat);
            preparedStatement.setString(5,vitamins);
            preparedStatement.setString(6,minerals);
            preparedStatement.executeUpdate();

            System.out.println("Value Inserted");

            preparedStatement.close();

            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public double getCalories(){
        return (4* protein) + (4* carbs)+(9*fat);
    }
    public String compareNutrition(Recipe other){
        double thisCalories = this.getCalories();
//        double otherCalories = other.getNutritionalInfo().getCalories();

//        return thisCalories > otherCalories
//                ? "This Recipe has more Calories"
//                : thisCalories < otherCalories
//                ? "This recipe has fewer Calories"
//                : "Both recipes have same Calorie count.";
        return null;
    }
}
