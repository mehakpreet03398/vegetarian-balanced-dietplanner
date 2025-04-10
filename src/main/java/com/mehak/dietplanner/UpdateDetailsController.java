package com.mehak.dietplanner;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class UpdateDetailsController implements Initializable {

    @FXML
    private TextField TextAge;
    @FXML
    private TextField TextHeight;
    @FXML
    private TextField TextWeight;
    @FXML
    private Label Status;
    @FXML
    private Label Name;
    @FXML
    private Label Email;
    @FXML
    private Label Age;
    @FXML
    private Label Height;
    @FXML
    private Label Weight;
    @FXML
    private Label Dietary;
    @FXML
    private Label Activity;

    String userID;
    UpdateDetails updateDetails = new UpdateDetails();
    @Override
    public void initialize(URL location, ResourceBundle Resource){

    }
    public void getUser(String user){
        this.userID =user;
    }
    public void updateAge(){
        int age = Integer.parseInt(TextAge.getText());
        if(updateDetails.updateAge(userID,age)){
            Status.setText("Updated Age");
        }
    }

    public void updateHeight(){
        Double height = Double.parseDouble(TextHeight.getText());
        if(updateDetails.updateHeight(userID,height)){
            Status.setText("Updated Height");
        }
    }
    public void updateWeight(){
        Double weight = Double.parseDouble(TextWeight.getText());
        if(updateDetails.updateWeight(userID,weight)){
            Status.setText("Updated Weight");
        }
    }
    public void show(){
        try{
            Connection connection = SqliteConnection.connector();
            String showQuery="SELECT email,age,weight,height, activityLevel,dietaryPreferences FROM users where userID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(showQuery);
            preparedStatement.setString(1,userID);
            ResultSet resultSet = preparedStatement.executeQuery();
            Name.setText(userID);
            while (resultSet.next()){
                String email = resultSet.getString("email");
                Email.setText(email);
                int age= resultSet.getInt("age");
                Age.setText(Integer.toString(age));
                Double height = resultSet.getDouble("height");
                Height.setText(Double.toString(height));
                Double weight = resultSet.getDouble("weight");
                Weight.setText(Double.toString(weight));
                String activity = resultSet.getString("activityLevel");
                Activity.setText(activity);
                String diet= resultSet.getString("dietaryPreferences");
                Dietary.setText(diet);

            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
