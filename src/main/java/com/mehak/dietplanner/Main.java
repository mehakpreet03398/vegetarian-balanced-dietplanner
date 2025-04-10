package com.mehak.dietplanner;

import com.mehak.dietplanner.models.ConnectDB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private ConnectDB db= new ConnectDB();


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 430);
        stage.setTitle("Vegetarian Balanced DietPlanner!");
        stage.setScene(scene);
        stage.show();

        db.getConnection();
    }

    public static void main(String[] args) {
        launch(args);
    }
}