package com.mehak.dietplanner;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class MealPlan {
    String planID;

    public MealPlan (){
        try(Connection connection = SqliteConnection.connector()){
            Statement statement = connection.createStatement();
            String createQuery = """
                    CREATE TABLE IF NOT EXISTS mealplans (
                    planID TEXT,
                    userID TEXT,
                    recipeID TEXT,
                    dateRange TEXT
                    );
                    """;
            statement.execute(createQuery);
            statement.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public List<String> generateMealPlan(String planID, String userID, String dietPref, int mealCount, LocalDate endDate) {
        List<String> result = new ArrayList<>();
        String query = "SELECT recipeID, recipeName FROM recipes WHERE recipeDietaryType LIKE ?";
        String insert = "INSERT INTO mealplans (planID, userID, recipeID, dateRange) VALUES (?, ?, ?, ?)";

        try (Connection conn = SqliteConnection.connector();
             PreparedStatement ps = conn.prepareStatement(query);
             PreparedStatement insertStmt = conn.prepareStatement(insert)) {

            ps.setString(1, "%" + dietPref + "%");
            ResultSet rs = ps.executeQuery();

            List<String> recipeNames = new ArrayList<>();
            List<String> recipeIDs = new ArrayList<>();

            while (rs.next()) {
                recipeIDs.add(rs.getString("recipeID"));
                recipeNames.add(rs.getString("recipeName"));
            }

            if (recipeNames.isEmpty()) {
                result.add("⚠️ No recipes found for: " + dietPref);
                return result;
            }

            Random random = new Random();
            LocalDate today = LocalDate.now();
            int daysAvailable = (int) today.until(endDate).getDays() + 1;

            for (int i = 0; i < mealCount; i++) {
                int randIndex = random.nextInt(recipeNames.size());
                String recipeID = recipeIDs.get(randIndex);
                String recipeName = recipeNames.get(randIndex);

                LocalDate assignedDate = today.plusDays(i % daysAvailable);
                String dateString = assignedDate.toString();

                insertStmt.setString(1, planID);
                insertStmt.setString(2, userID);
                insertStmt.setString(3, recipeID);
                insertStmt.setString(4, dateString);
                insertStmt.executeUpdate();

                result.add("📅 " + dateString + " → " + recipeName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            result.add("❌ Error generating meal plan.");
        }

        return result;
    }
}
