package com.mehak.dietplanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;

import static com.mehak.dietplanner.SqliteConnection.connector;



public class RegisterScreenController implements Initializable {
    Register register = new Register();
    @FXML
    private TextField textUserID;
    @FXML
    private TextField textPassword;
    @FXML
    private TextField textEmail;
    @FXML
    private TextField textAge;
    @FXML
    private TextField textWeight;
    @FXML
    private TextField textHeight;
    @FXML
    private ComboBox<String> comboActivityLevel;
    @FXML
    private ComboBox<String> comboDietaryPreferences;
    @FXML
    private Label status;


    public void initialize(URL location, ResourceBundle resources){

        comboDietaryPreferences.getItems().addAll("Low Carb", "High Protein", "Balanced");
        comboActivityLevel.getItems().addAll("Sedentary", "Moderate", "Active");
    }

    public void register(){

        String userID = textUserID.getText();
        String password = textPassword.getText();
        String email = textEmail.getText();
        int age = Integer.parseInt(textAge.getText());
        Double weight = Double.parseDouble(textWeight.getText());
        Double height = Double.parseDouble(textHeight.getText());
        String dietaryPref = comboDietaryPreferences.getValue();
        String activityLevel = comboActivityLevel.getValue();

        if(userID.isEmpty()|| password.isEmpty()|| email.isEmpty()|| dietaryPref==null||activityLevel==null) {
            status.setText("Please fill all the fields");
        }
        if (register.register(userID, password, email, age, weight, height, dietaryPref, activityLevel)) {
            status.setText("User Created");
            System.out.println("User Added");
        } else {
            status.setText("Error");
            System.out.println("Error Adding User");
        }

    }
    public void login (ActionEvent e){
        try{
            ((Node)e.getSource()).getScene().getWindow().hide();
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass().getResource("login.fxml").openStream());

            Scene scene = new Scene(root);
            primaryStage.setTitle("DietPlanner!");
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
