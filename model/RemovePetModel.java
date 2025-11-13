package model;

import util.DBConnector;
import java.sql.*;
import java.util.*;

public class RemovePetModel {

	public void removePet(int petId) {
		String qry = "DELETE FROM pet WHERE pet_id = ?";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(qry)) {
            pstmt.setInt(1, petId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

	public List<MainMenuModel1.Pet> getPets() {
		List<MainMenuModel1.Pet> pets = new ArrayList<>();
		String qry = "SELECT pet_id, pet_name FROM pet";

		try (Connection conn = DBConnector.getConnection();
			 Statement stmt = conn.createStatement();
			 ResultSet rs =stmt.executeQuery(qry)) {

			while (rs.next()) {
				int id = rs.getInt("pet_id");
				String name = rs.getString("pet_name");
				pets.add(new MainMenuModel1.Pet(id, name));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return pets;
	}
}