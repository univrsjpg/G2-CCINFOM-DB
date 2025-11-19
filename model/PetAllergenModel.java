package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PetAllergenModel {

        public List<String[]> getPetAllergyRecord(int petId) {
            List<String[]> result = new ArrayList<>();

            String sql = "SELECT p.pet_name, p.species, p.gender, "
                    + "p.age, p.weight, a.description AS allergen, "
                    + "fs.food_name AS unsafe_food "
                    + "FROM pet p "
                    + "LEFT JOIN pet_allergy pa ON p.pet_id = pa.pet_id "
                    + "LEFT JOIN allergen a ON pa.allergen_id = a.allergen_id "
                    + "LEFT JOIN food_allergen fa ON a.allergen_id = fa.allergen_id "
                    + "LEFT JOIN food_stock fs ON fa.food_id = fs.food_id "
                    + "WHERE p.pet_id = ?";

            try (Connection cn = util.DBConnector.getConnection();
                 PreparedStatement pstmt = cn.prepareStatement(sql)) {

                pstmt.setInt(1, petId);

                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    result.add(new String[]{
                            rs.getString("pet_name"),
                            rs.getString("species"),
                            rs.getString("gender"),
                            rs.getString("age"),
                            rs.getString("weight"),
                            rs.getString("allergen"),
                            rs.getString("unsafe_food")
                    });
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return result;
        }
    }
