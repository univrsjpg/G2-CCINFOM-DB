package model;

import util.DBConnector;
import java.sql.*;
import java.util.*;

/**
 * All the SQL statements.
 */
public class FoodStockModel {
    // Add a food_stock record (one food item)
    public void addFood(String name, java.sql.Date expiry, java.sql.Date bought,
                        double cost, double calories, double qty) {
        String sql = """
            INSERT INTO food_stock 
            (food_name, date_expiry, date_bought, cost, caloric_count, stock_qty)
            VALUES (?, ?, ?, ?, ?, ?)
        """;
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
    /**
     * Show all the food_stock records.
     */
    public List<String> listFoods() {
        List<String> foods = new ArrayList<>();
        String qry = "SELECT * FROM food_stock";

        try (Connection conn = DBConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(qry)) {

            while (rs.next()) {
                foods.add(String.format(
                    "ID: %d | %s | Exp: %s | kCal: %d | Stock: %.2f | Cost: %.2f",
                    rs.getInt("food_id"),
                    rs.getString("food_name"),
                    rs.getDate("date_expiry"),
                    rs.getInt("caloric_count"),
                    rs.getDouble("stock_qty"),
                    rs.getDouble("cost")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foods;
    }
    /**
     * Remove a food_stock record
     * @param id food_ID
     */
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
    /**
     * Updates a food_stock record
     * @param id
     * @param fieldName
     * @param newValue
     * @return
     */
    public String updateFoodField(int id, String fieldName, String newValue) {
        List<String> allowed = List.of(
            "food_name", "date_expiry", "date_bought", "cost", "caloric_count", "stock_qty"
        );
        if (!allowed.contains(fieldName)) {
            return "Invalid field: " + fieldName;
        }

        String sql = "UPDATE food_stock SET " + fieldName + " = ? WHERE food_id = ?";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (fieldName.equals("cost") || fieldName.equals("caloric_count") || fieldName.equals("stock_qty")) {
                pstmt.setDouble(1, Double.parseDouble(newValue));
            } else if (fieldName.equals("date_expiry") || fieldName.equals("date_bought")) {
                pstmt.setDate(1, java.sql.Date.valueOf(newValue));
            } else {
                pstmt.setString(1, newValue);
            }

            pstmt.setInt(2, id);
            int rows = pstmt.executeUpdate();

            if (rows > 0)
                return "Updated " + fieldName + " for food ID " + id;
            else
                return "No food found with ID " + id;

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Invalid input format for field: " + fieldName);
        }
        return "Error updating field.";
    }
    /**
     * Get the name of a specific food_stock record.
     */
    public String getFoodName(int id) {
        String sql = "SELECT food_name FROM food_stock WHERE food_id = ?";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getString("food_name");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "(Unknown)";
    }

    /**
     * Returns a map of all allergens (description -> id) 
    */
    public Map<String, Integer> getAllAllergens() throws SQLException {
        Map<String, Integer> map = new HashMap<>();
        String sql = "SELECT allergen_id, description FROM allergen ORDER BY description";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                map.put(rs.getString("description"), rs.getInt("allergen_id"));
            }
        }
        return map;
    }

    /** 
     * Returns a list of allergen descriptions linked to a food item 
    */
    public List<String> getLinkedAllergens(int foodId) throws SQLException {
        List<String> list = new ArrayList<>();
        String sql = "SELECT a.description FROM allergen a "
                   + "JOIN food_allergen fa ON a.allergen_id = fa.allergen_id "
                   + "WHERE fa.food_id = ?";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, foodId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(rs.getString("description"));
                }
            }
        }
        return list;
    }

    /** 
     * Link an allergen to a food item 
    */
    public void addAllergenToFood(int foodId, int allergenId) throws SQLException {
        String sql = "INSERT INTO food_allergen (food_id, allergen_id) VALUES (?, ?)";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, foodId);
            ps.setInt(2, allergenId);
            ps.executeUpdate();
        }
    }

    /** 
     * Remove an allergen link from a food item 
    */
    public void removeAllergenFromFood(int foodId, int allergenId) throws SQLException {
        String sql = "DELETE FROM food_allergen WHERE food_id = ? AND allergen_id = ?";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, foodId);
            ps.setInt(2, allergenId);
            ps.executeUpdate();
        }
    }

}
