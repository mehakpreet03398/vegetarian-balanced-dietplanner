package com.mehak.dietplanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UserController implements Initializable {
    public User user = new User();
    @FXML
    private TextField textUsername;
    @FXML
    private TextField textPassword;
    @FXML
    private Label isConnected;
    @Override
    public void initialize(URL location, ResourceBundle resources){
        //TODO
        if(user.isDBConnected()){
            isConnected.setText("Connected");
        }else{
            isConnected.setText("Not Connected");
        }
    }
    public void login (ActionEvent e){
        try{
            if(user.login(textUsername.getText(), textPassword.getText())){
                isConnected.setText("Login Successful");

                ((Node)e.getSource()).getScene().getWindow().hide();

                Stage primaryStage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                Pane root = loader.load(getClass().getResource("homeScreen.fxml").openStream());
                HomeScreenController homeScreenController= loader.getController();
                homeScreenController.getUser(textUsername.getText());
                Scene scene = new Scene(root);
                primaryStage.setTitle("DietPlanner!");
                primaryStage.setScene(scene);
                primaryStage.show();


            } else {
                isConnected.setText("Login Failed");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }   catch (IOException ae){
            ae.printStackTrace();
        }
    }
    public void onRegister(ActionEvent event){
        try {

            ((Node)event.getSource()).getScene().getWindow().hide();

            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass().getResource("registerScreen.fxml").openStream());
            RegisterScreenController registerScreenController = loader.getController();
            Scene scene = new Scene(root);
            primaryStage.setTitle("DietPlanner!");
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
