package com.mehak.dietplanner;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Recipe {
    public String recipeID;
    public String recipeName;
    public List<String> ingredients;
    public String instructions;
    public NutritionalInfo nutritionalInfo;

    public Recipe() {

    }

    public boolean addRecipe(String recipeID,String recipeName, String recipeIngredients,String recipeInstructions, String recipeDietaryType, Double recipeProtein, Double recipeCarbs, Double recipeFat, String recipeVitamins, String recipeMinerals){

        NutritionalInfo nutritionalInfo = new NutritionalInfo(recipeProtein,recipeCarbs,recipeFat,recipeVitamins,recipeMinerals);

        try (Connection connection = SqliteConnection.connector()) {
            Statement statement = connection.createStatement();
            String createQuery = """
                    CREATE TABLE IF NOT EXISTS recipes (
                    recipeID TEXT PRIMARY KEY,
                    recipeName TEXT,
                    recipeIngredients TEXT,
                    recipeInstructions TEXT,
                    recipeDietaryType TEXT
                    );
                    """;
            statement.execute(createQuery);
            System.out.println("Recipe Table Created or Already Exists");
            statement.close();

            String insertQuery= """
                    INSERT INTO recipes (recipeID,recipeName, recipeIngredients, recipeInstructions, recipeDietaryType)
                    VALUES (?,?,?,?,?);
                    """;

            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, recipeID);
            preparedStatement.setString(2,recipeName);
            preparedStatement.setString(3,recipeIngredients);
            preparedStatement.setString(4,recipeInstructions);
            preparedStatement.setString(5,recipeDietaryType);
            preparedStatement.executeUpdate();

            nutritionalInfo.addNutritionalInfo(recipeID);

            System.out.println("Values added Successfully");
            preparedStatement.close();
            connection.close();
            return true;
        } catch(Exception ex){
            ex.printStackTrace();
            return false;
        }

    }

    public List<String> searchRecipes(String searchTerm) {
        List<String> results = new ArrayList<>();
        String query = """
            SELECT recipeID, recipeName, recipeIngredients, recipeInstructions, recipeDietaryType FROM recipes
            WHERE recipeName LIKE ? OR recipeIngredients LIKE ?
        """;
        String nutritionalQuery= """
                SELECT protein, carbs, fat, vitamins, minerals FROM nutritionalInfo
                WHERE recipeID = ?
                """;

        try (Connection conn = SqliteConnection.connector();
             PreparedStatement ps = conn.prepareStatement(query)) {

            String keyword = "%" + searchTerm.toLowerCase() + "%";
            ps.setString(1, keyword);
            ps.setString(2, keyword);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String recipeID = rs.getString("recipeID");
                String name = rs.getString("recipeName");
                String ingredients = rs.getString("recipeIngredients");
                String instructions = rs.getString("recipeInstructions");
                String dietaryType = rs.getString("recipeDietaryType");
                PreparedStatement preparedStatement = conn.prepareStatement(nutritionalQuery);
                preparedStatement.setString(1,recipeID);
                ResultSet res = preparedStatement.executeQuery();
                while(res.next()){
                    Double proteinD = res.getDouble("protein");
                    String protein = proteinD.toString();
                    Double carbsD = res.getDouble("carbs");
                    String carbs = carbsD.toString();
                    Double fatD = res.getDouble("fat");
                    String fat = fatD.toString();
                    String vitamins= res.getString("vitamins");
                    String minerals= res.getString("minerals");
                    if (name.toLowerCase().contains("salad")) {
                        results.add("🥗 " + name+"\n" +"Ingredients: "+ ingredients +"\n"+ "Nutritional Value: "+ "Protein: "+ protein + " Carbs: " + carbs + " Fat: "+ fat +"\n" + "Vitamins: "+ vitamins + " Minerals: "+ minerals
                        +"\n"+ "Instructions: "+ instructions + " Dietary Type: " + dietaryType);
                    } else if (name.toLowerCase().contains("pasta")) {
                        results.add("🍝 " + name+"\n" +"Ingredients: "+ ingredients +"\n"+ "Nutritional Value: "+ "Protein: "+ protein + " Carbs: " + carbs + " Fat: "+ fat +"\n" + "Vitamins: "+ vitamins + " Minerals: "+ minerals
                                +"\n"+ "Instructions: "+ instructions + " Dietary Type: " + dietaryType);
                    } else if (name.toLowerCase().contains("soup")) {
                        results.add("🍲 " + name+"\n" +"Ingredients: "+ ingredients +"\n"+ "Nutritional Value: "+ "Protein: "+ protein + " Carbs: " + carbs + " Fat: "+ fat +"\n" + "Vitamins: "+ vitamins + " Minerals: "+ minerals
                                +"\n"+ "Instructions: "+ instructions + " Dietary Type: " + dietaryType);
                    } else if (name.toLowerCase().contains("avocado")) {
                        results.add("🥑 " + name+"\n" +"Ingredients: "+ ingredients +"\n"+ "Nutritional Value: "+ "Protein: "+ protein + " Carbs: " + carbs + " Fat: "+ fat +"\n" + "Vitamins: "+ vitamins + " Minerals: "+ minerals
                                +"\n"+ "Instructions: "+ instructions + " Dietary Type: " + dietaryType);
                    } else if (name.toLowerCase().contains("bread")) {
                        results.add("🍞 " + name+"\n" +"Ingredients: "+ ingredients +"\n"+ "Nutritional Value: "+ "Protein: "+ protein + " Carbs: " + carbs + " Fat: "+ fat +"\n" + "Vitamins: "+ vitamins + " Minerals: "+ minerals
                                +"\n"+ "Instructions: "+ instructions + " Dietary Type: " + dietaryType);
                    } else if (name.toLowerCase().contains("rice")) {
                        results.add("🍚 " + name+"\n" +"Ingredients: "+ ingredients +"\n"+ "Nutritional Value: "+ "Protein: "+ protein + " Carbs: " + carbs + " Fat: "+ fat +"\n" + "Vitamins: "+ vitamins + " Minerals: "+ minerals
                                +"\n"+ "Instructions: "+ instructions + " Dietary Type: " + dietaryType);
                    } else if (name.toLowerCase().contains("tofu")) {
                        results.add("🧈 " + name+"\n" +"Ingredients: "+ ingredients +"\n"+ "Nutritional Value: "+ "Protein: "+ protein + " Carbs: " + carbs + " Fat: "+ fat +"\n" + "Vitamins: "+ vitamins + " Minerals: "+ minerals
                                +"\n"+ "Instructions: "+ instructions + " Dietary Type: " + dietaryType);
                    } else if (name.toLowerCase().contains("smoothie")) {
                        results.add("🥤 " + name+"\n" +"Ingredients: "+ ingredients +"\n"+ "Nutritional Value: "+ "Protein: "+ protein + " Carbs: " + carbs + " Fat: "+ fat +"\n" + "Vitamins: "+ vitamins + " Minerals: "+ minerals
                                +"\n"+ "Instructions: "+ instructions + " Dietary Type: " + dietaryType);
                    } else if (name.toLowerCase().contains("fruit")) {
                        results.add("🍓 " + name+"\n" +"Ingredients: "+ ingredients +"\n"+ "Nutritional Value: "+ "Protein: "+ protein + " Carbs: " + carbs + " Fat: "+ fat +"\n" + "Vitamins: "+ vitamins + " Minerals: "+ minerals
                                +"\n"+ "Instructions: "+ instructions + " Dietary Type: " + dietaryType);
                    } else if (name.toLowerCase().contains("veggie") || name.toLowerCase().contains("vegetable")) {
                        results.add("🥦 " + name+"\n" +"Ingredients: "+ ingredients +"\n"+ "Nutritional Value: "+ "Protein: "+ protein + " Carbs: " + carbs + " Fat: "+ fat +"\n" + "Vitamins: "+ vitamins + " Minerals: "+ minerals
                                +"\n"+ "Instructions: "+ instructions + " Dietary Type: " + dietaryType);
                    } else if (name.toLowerCase().contains("curry")) {
                        results.add("🍛 " + name+"\n" +"Ingredients: "+ ingredients +"\n"+ "Nutritional Value: "+ "Protein: "+ protein + " Carbs: " + carbs + " Fat: "+ fat +"\n" + "Vitamins: "+ vitamins + " Minerals: "+ minerals
                                +"\n"+ "Instructions: "+ instructions + " Dietary Type: " + dietaryType);
                    } else if (name.toLowerCase().contains("bean")) {
                        results.add("🫘 " + name+"\n" +"Ingredients: "+ ingredients +"\n"+ "Nutritional Value: "+ "Protein: "+ protein + " Carbs: " + carbs + " Fat: "+ fat +"\n" + "Vitamins: "+ vitamins + " Minerals: "+ minerals
                                +"\n"+ "Instructions: "+ instructions + " Dietary Type: " + dietaryType);
                    } else {
                        results.add("🥬 " + name+"\n" +"Ingredients: "+ ingredients +"\n"+ "Nutritional Value: "+ "Protein: "+ protein + " Carbs: " + carbs + " Fat: "+ fat +"\n" + "Vitamins: "+ vitamins + " Minerals: "+ minerals
                                +"\n"+ "Instructions: "+ instructions + " Dietary Type: " + dietaryType);
                    }
                }


            }
        } catch (SQLException e) {
            e.printStackTrace();
            results.add("Error: " + e.getMessage());
        }

        return results;
    }

    public List<String> searchRecipesByFilter(String searchTerm) {
        List<String> results = new ArrayList<>();
        String query = """
            SELECT recipeID, recipeName, recipeIngredients, recipeInstructions, recipeDietaryType FROM recipes
            WHERE recipeDietaryType LIKE ?
        """;
        String nutritionalQuery= """
                SELECT protein, carbs, fat, vitamins, minerals FROM nutritionalInfo
                WHERE recipeID = ?
                """;

        try (Connection conn = SqliteConnection.connector();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, searchTerm);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String recipeID = rs.getString("recipeID");
                String name = rs.getString("recipeName");
                String ingredients = rs.getString("recipeIngredients");
                String instructions = rs.getString("recipeInstructions");
                String dietaryType = rs.getString("recipeDietaryType");
                PreparedStatement preparedStatement = conn.prepareStatement(nutritionalQuery);
                preparedStatement.setString(1,recipeID);
                ResultSet res = preparedStatement.executeQuery();
                while(res.next()){
                    Double proteinD = res.getDouble("protein");
                    String protein = proteinD.toString();
                    Double carbsD = res.getDouble("carbs");
                    String carbs = carbsD.toString();
                    Double fatD = res.getDouble("fat");
                    String fat = fatD.toString();
                    String vitamins= res.getString("vitamins");
                    String minerals= res.getString("minerals");
                    if (name.toLowerCase().contains("salad")) {
                        results.add("🥗 " + name+"\n" +"Ingredients: "+ ingredients +"\n"+ "Nutritional Value: "+ "Protein: "+ protein + " Carbs: " + carbs + " Fat: "+ fat +"\n" + "Vitamins: "+ vitamins + " Minerals: "+ minerals
                                +"\n"+ "Instructions: "+ instructions + " Dietary Type: " + dietaryType);
                    } else if (name.toLowerCase().contains("pasta")) {
                        results.add("🍝 " + name+"\n" +"Ingredients: "+ ingredients +"\n"+ "Nutritional Value: "+ "Protein: "+ protein + " Carbs: " + carbs + " Fat: "+ fat +"\n" + "Vitamins: "+ vitamins + " Minerals: "+ minerals
                                +"\n"+ "Instructions: "+ instructions + " Dietary Type: " + dietaryType);
                    } else if (name.toLowerCase().contains("soup")) {
                        results.add("🍲 " + name+"\n" +"Ingredients: "+ ingredients +"\n"+ "Nutritional Value: "+ "Protein: "+ protein + " Carbs: " + carbs + " Fat: "+ fat +"\n" + "Vitamins: "+ vitamins + " Minerals: "+ minerals
                                +"\n"+ "Instructions: "+ instructions + " Dietary Type: " + dietaryType);
                    } else if (name.toLowerCase().contains("avocado")) {
                        results.add("🥑 " + name+"\n" +"Ingredients: "+ ingredients +"\n"+ "Nutritional Value: "+ "Protein: "+ protein + " Carbs: " + carbs + " Fat: "+ fat +"\n" + "Vitamins: "+ vitamins + " Minerals: "+ minerals
                                +"\n"+ "Instructions: "+ instructions + " Dietary Type: " + dietaryType);
                    } else if (name.toLowerCase().contains("bread")) {
                        results.add("🍞 " + name+"\n" +"Ingredients: "+ ingredients +"\n"+ "Nutritional Value: "+ "Protein: "+ protein + " Carbs: " + carbs + " Fat: "+ fat +"\n" + "Vitamins: "+ vitamins + " Minerals: "+ minerals
                                +"\n"+ "Instructions: "+ instructions + " Dietary Type: " + dietaryType);
                    } else if (name.toLowerCase().contains("rice")) {
                        results.add("🍚 " + name+"\n" +"Ingredients: "+ ingredients +"\n"+ "Nutritional Value: "+ "Protein: "+ protein + " Carbs: " + carbs + " Fat: "+ fat +"\n" + "Vitamins: "+ vitamins + " Minerals: "+ minerals
                                +"\n"+ "Instructions: "+ instructions + " Dietary Type: " + dietaryType);
                    } else if (name.toLowerCase().contains("tofu")) {
                        results.add("🧈 " + name+"\n" +"Ingredients: "+ ingredients +"\n"+ "Nutritional Value: "+ "Protein: "+ protein + " Carbs: " + carbs + " Fat: "+ fat +"\n" + "Vitamins: "+ vitamins + " Minerals: "+ minerals
                                +"\n"+ "Instructions: "+ instructions + " Dietary Type: " + dietaryType);
                    } else if (name.toLowerCase().contains("smoothie")) {
                        results.add("🥤 " + name+"\n" +"Ingredients: "+ ingredients +"\n"+ "Nutritional Value: "+ "Protein: "+ protein + " Carbs: " + carbs + " Fat: "+ fat +"\n" + "Vitamins: "+ vitamins + " Minerals: "+ minerals
                                +"\n"+ "Instructions: "+ instructions + " Dietary Type: " + dietaryType);
                    } else if (name.toLowerCase().contains("fruit")) {
                        results.add("🍓 " + name+"\n" +"Ingredients: "+ ingredients +"\n"+ "Nutritional Value: "+ "Protein: "+ protein + " Carbs: " + carbs + " Fat: "+ fat +"\n" + "Vitamins: "+ vitamins + " Minerals: "+ minerals
                                +"\n"+ "Instructions: "+ instructions + " Dietary Type: " + dietaryType);
                    } else if (name.toLowerCase().contains("veggie") || name.toLowerCase().contains("vegetable")) {
                        results.add("🥦 " + name+"\n" +"Ingredients: "+ ingredients +"\n"+ "Nutritional Value: "+ "Protein: "+ protein + " Carbs: " + carbs + " Fat: "+ fat +"\n" + "Vitamins: "+ vitamins + " Minerals: "+ minerals
                                +"\n"+ "Instructions: "+ instructions + " Dietary Type: " + dietaryType);
                    } else if (name.toLowerCase().contains("curry")) {
                        results.add("🍛 " + name+"\n" +"Ingredients: "+ ingredients +"\n"+ "Nutritional Value: "+ "Protein: "+ protein + " Carbs: " + carbs + " Fat: "+ fat +"\n" + "Vitamins: "+ vitamins + " Minerals: "+ minerals
                                +"\n"+ "Instructions: "+ instructions + " Dietary Type: " + dietaryType);
                    } else if (name.toLowerCase().contains("bean")) {
                        results.add("🫘 " + name+"\n" +"Ingredients: "+ ingredients +"\n"+ "Nutritional Value: "+ "Protein: "+ protein + " Carbs: " + carbs + " Fat: "+ fat +"\n" + "Vitamins: "+ vitamins + " Minerals: "+ minerals
                                +"\n"+ "Instructions: "+ instructions + " Dietary Type: " + dietaryType);
                    } else {
                        results.add("🥬 " + name+"\n" +"Ingredients: "+ ingredients +"\n"+ "Nutritional Value: "+ "Protein: "+ protein + " Carbs: " + carbs + " Fat: "+ fat +"\n" + "Vitamins: "+ vitamins + " Minerals: "+ minerals
                                +"\n"+ "Instructions: "+ instructions + " Dietary Type: " + dietaryType);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            results.add("Error: " + e.getMessage());
        }

        return results;
    }

//    public boolean
}
