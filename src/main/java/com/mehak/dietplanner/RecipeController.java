package com.mehak.dietplanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class RecipeController implements Initializable {

    Recipe recipe = new Recipe();

    @FXML
    private TextField textRecipeID;
    @FXML
    private TextField textRecipeName;
    @FXML
    private TextField textIngredients;
    @FXML
    private TextField textInstructions;
    @FXML
    private ComboBox<String> comboDietaryType;
    @FXML
    private TextField textProtein;
    @FXML
    private TextField textCarbs;
    @FXML
    private TextField textFat;
    @FXML
    private TextField textVitamins;
    @FXML
    private TextField textMinerals;
    @FXML
    private Label status1;

    @FXML
    private ListView<String> recipeList;
    @FXML
    private ComboBox<String> comboBoxSearchFilter;
    @FXML
    private TextField textSearch;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboDietaryType.getItems().addAll("Low Carb", "Balanced", "High Protein", "Low Fat");
        status1.setText("Search or Add");
        comboBoxSearchFilter.getItems().addAll("Low Carb","Balanced", "High Protein", "Low Fat");
    }
    public void addRecipe(){
        String recipeId= textRecipeID.getText();
        String recipeName=textRecipeName.getText();
        String recipeIngredients= textIngredients.getText();
        String recipeInstructions= textInstructions.getText();
        String recipeDietaryType = comboDietaryType.getValue();
        Double recipeProtein = Double.parseDouble(textProtein.getText());
        Double recipeCarbs = Double.parseDouble(textCarbs.getText());
        Double recipeFat = Double.parseDouble(textFat.getText());
        String recipeVitamins = textVitamins.getText();
        String recipeMinerals = textMinerals.getText();

        if (recipe.addRecipe(recipeId,recipeName,recipeIngredients,recipeInstructions, recipeDietaryType, recipeProtein,recipeCarbs,recipeFat,recipeVitamins,recipeMinerals)){
            status1.setText("Added Successfully");
        }else{
            status1.setText("Not Successful");
        }
    }
    public void searchRecipe(){
        String searchTerm = textSearch.getText().trim();

        ObservableList<String> items = FXCollections.observableArrayList();

        if(!searchTerm.isEmpty()) {
            items.clear();
            List<String> results = recipe.searchRecipes(searchTerm);
            if(results.isEmpty()){
                items.add("No recipes found.");
            }else{
                items.addAll(results);

            }
            recipeList.setItems(items);
        }
    }
    public void searchRecipeByFilter(){
        String searchTerm = comboBoxSearchFilter.getValue().trim();
        ObservableList<String> items = FXCollections.observableArrayList();
        System.out.println(searchTerm);

        if(!searchTerm.isEmpty()) {
            items.clear();
            List<String> results = recipe.searchRecipesByFilter(searchTerm);
            if(results.isEmpty()){
                items.add("No recipes found.");
            }else{
                items.addAll(results);

            }
            recipeList.setItems(items);
        }
    }

}
