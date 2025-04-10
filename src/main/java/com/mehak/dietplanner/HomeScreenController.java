package com.mehak.dietplanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class HomeScreenController implements Initializable {
    String user;
    MealPlan mealPlan = new MealPlan();
    Recipe recipe = new Recipe();

    @FXML
    public Label userLabel;

    @FXML
    private ComboBox<String> comboDietPref;

    @FXML
    private DatePicker endDate;
    @FXML
    private TextField textMealID;
    @FXML
    private ListView<String> mealsListView;
    @FXML
    private TextField noMeals;
    @FXML
    private Label stat;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        //todo
        comboDietPref.getItems().addAll("Low Carb","Balanced", "High Protein", "Low Fat");
    }
    public void getUser(String user){
        this.user =user;
        userLabel.setText("Welcome," +user);
    }
    public void Signout(ActionEvent event){
        try{
            ((Node)event.getSource()).getScene().getWindow().hide();
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass().getResource("login.fxml").openStream());

            Scene scene = new Scene(root);
            primaryStage.setTitle("DietPlanner!");
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void showRecipes(ActionEvent ev){
        try{

            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass().getResource("recipe.fxml").openStream());

            Scene scene = new Scene(root);
            stage.setTitle("Manage Recipes");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public  void mealGenerator() {

    }
    public void generateMealPlan(){
        String planID= textMealID.getText();
        String dietPref = comboDietPref.getValue();
        LocalDate eDate= endDate.getValue();
        String countText = noMeals.getText();
        if (planID.isEmpty() || countText.isEmpty() || endDate == null) {
            stat.setText("Please fill all fields.");
        }
        int mealCount=0;
        try {
            mealCount = Integer.parseInt(countText);
        } catch (NumberFormatException ex) {
            stat.setText("Invalid number of meals.");
        }

        ObservableList<String> mealItems = FXCollections.observableArrayList();

        mealsListView.setItems(mealItems);
        mealItems.clear();
        List<String> results = mealPlan.generateMealPlan(planID, user, dietPref, mealCount, eDate);
        mealItems.addAll(results);
        stat.setText("Meal Plan Generated.");
    }

    public void viewMealPlan(ActionEvent v) {
        try {
            Stage primaryStage1 = new Stage();
            FXMLLoader loader1 = new FXMLLoader();
            Pane root = loader1.load(getClass().getResource("meal-viewer.fxml").openStream());
            MealViewerController mealViewerController = loader1.getController();
            mealViewerController.getUser(user);
            Scene scene = new Scene(root);
            primaryStage1.setTitle("DietPlanner!");
            primaryStage1.setScene(scene);
            primaryStage1.show();

        }catch (Exception eee){
            eee.printStackTrace();
        }
    }
    public void changeDetails(ActionEvent vv){
        try {
            Stage primaryStage1 = new Stage();
            FXMLLoader loader1 = new FXMLLoader();
            Pane root = loader1.load(getClass().getResource("update-details.fxml").openStream());
            UpdateDetailsController updateDetailsController = loader1.getController();
            updateDetailsController.getUser(user);
            Scene scene = new Scene(root);
            primaryStage1.setTitle("DietPlanner!");
            primaryStage1.setScene(scene);
            primaryStage1.show();

        }catch (Exception eee){
            eee.printStackTrace();
        }
    }
}

