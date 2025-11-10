package model;

import util.DBConnector;
import java.sql.*;
import java.util.*;

public class FoodStockModel {

    public void addFood(String name, java.sql.Date expiry, java.sql.Date bought, double cost, double calories,  double qty) {
        String sql = "INSERT INTO food_stock (food_name, date_expiry, date_bought, cost, caloric_count, stock_qty) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setDate(2, expiry);
            pstmt.setDate(3, bought);
            pstmt.setDouble(4, cost);
            pstmt.setDouble(5, calories);
            pstmt.setDouble(6, qty);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> listFoods() {
        List<String> foods = new ArrayList<>();
        String qry = "SELECT * FROM food_stock";

        try (Connection conn = DBConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(qry)) {

            while (rs.next()) {
                foods.add(String.format(
                    "ID: %d | %s | Exp: %s | calPerServing: %d | Qty: %.2f",
                    rs.getInt("food_id"),
                    rs.getString("food_name"),
                    rs.getDate("date_expiry"),
                    rs.getInt("caloric_count"),
                    rs.getDouble("stock_qty")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foods;
    }

    public void deleteFood(int id) {
        String sql = "DELETE FROM food_stock WHERE food_id = ?";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateFood(int id, double newCost, double newQty) {
        String sql = "UPDATE food_stock SET cost = ?, stock_qty = ? WHERE food_id = ?";
        try (Connection conn = DBConnector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, newCost);
            pstmt.setDouble(2, newQty);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String updateFoodField(int id, String fieldName, String newValue) {
        List<String> allowed = List.of("food_name", "date_expiry", "date_bought", "cost", "caloric_count", "stock_qty");
        String message;
        if (!allowed.contains(fieldName)) {
            return message = String.format("Invalid field: " + fieldName);
        }

        String sql = "UPDATE food_stock SET " + fieldName + " = ? WHERE food_id = ?";

        try (Connection conn = DBConnector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // handle numeric fields
            if (fieldName.equals("cost") || fieldName.equals("caloric_count") || fieldName.equals("stock_qty")) {
                pstmt.setDouble(1, Double.parseDouble(newValue));
            }
            // handle date fields
            else if (fieldName.equals("date_expiry") || fieldName.equals("date_bought")) {
                pstmt.setDate(1, java.sql.Date.valueOf(newValue));
            }
            // handle string field
            else {
                pstmt.setString(1, newValue);
            }

            pstmt.setInt(2, id);
            int rows = pstmt.executeUpdate();

            if (rows > 0) return message = String.format("Updated " + fieldName + " for food ID " + id);
            else return message = String.format("No food found with ID " + id);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Invalid input format for field: " + fieldName);
        }
        return "Error!";
    }

}
