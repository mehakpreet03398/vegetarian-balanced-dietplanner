package com.mehak.dietplanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MealViewerController implements Initializable{
    String user;
    @FXML
    private ListView<String> currentList;
    public MealViewerController(){

    }

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle){

    }
    public void getUser(String user){
        this.user =user;
    }
    public void show(){
        try {
            Connection connection = SqliteConnection.connector();
            String selectQuery = "SELECT * FROM mealplans WHERE userID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);

            preparedStatement.setString(1,user);
            ResultSet rs = preparedStatement.executeQuery();
            ObservableList<String> mealItems = FXCollections.observableArrayList();
            currentList.setItems(mealItems);
            mealItems.clear();
            List<String> results = new ArrayList<>();
            while(rs.next()){
                String planID = rs.getString("planID");
                String recipeID= rs.getString("recipeID");
                String dateRange =rs.getString("dateRange");
                String recipeName = "";

                String getString = "SELECT recipeName FROM recipes WHERE recipeID = ?";
                PreparedStatement preparedStatement1 = connection.prepareStatement(getString);
                preparedStatement1.setString(1,recipeID);
                ResultSet rs1 = preparedStatement1.executeQuery();
                while(rs1.next()){
                    recipeName=rs1.getString("recipeName");
                }
                results.add("MEAL ID: "+ planID + " RECIPE NAME" + recipeName+ "Date: "+ dateRange);
            }
            mealItems.addAll(results);


        }catch (Exception ee){
            ee.printStackTrace();
        }
    }

}
