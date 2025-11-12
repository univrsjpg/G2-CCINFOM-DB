package model;

import java.util.List;
import java.util.ArrayList;
import java.sql.*;

public class AllergenModel
{
    public void addAllergen(String desc)
    {
        String sql = "INSERT INTO allergen (description) VALUES (?)";
        try (Connection cn = util.DBConnector.getConnection();
             PreparedStatement pstmt = cn.prepareStatement(sql))
        {
            pstmt.setString(1, desc);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllergen(int allergen_id)
    {
        String sql = "DELETE FROM allergen WHERE allergen_id = ?";
        try (Connection cn = util.DBConnector.getConnection();
             PreparedStatement pstmt = cn.prepareStatement(sql)) {
            pstmt.setInt(1, allergen_id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Can only edit description since allergen_id is PK, and it increments automatically
    public void editAllergen(int allergen_id, String newDesc)
    {
        String sql = "UPDATE allergen SET description = ? WHERE allergen_id = ?";
        try (Connection cn = util.DBConnector.getConnection();
             PreparedStatement pstmt = cn.prepareStatement(sql))
        {
            pstmt.setString(1, newDesc);
            pstmt.setInt(2, allergen_id);
            pstmt.executeUpdate();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public List<String[]> listAllergensTable() {
        List<String[]> result = new ArrayList<>();
        String sql = "SELECT allergen_id, description FROM allergen";
        try (Connection cn = util.DBConnector.getConnection();
             Statement stmt = cn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                result.add(new String[] {
                        String.valueOf(rs.getInt("allergen_id")),
                        rs.getString("description")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

}