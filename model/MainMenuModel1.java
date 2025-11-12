package model;

import util.DBConnector;
import java.sql.*;
import java.util.*;

public class MainMenuModel1 {

	// Gets the names of the pets currently in the database
	public List<Pet> getPetNames() {
		List<Pet> pets = new ArrayList<>();
		String qry = "SELECT pet_id, pet_name FROM pet";

		try (Connection conn = DBConnector.getConnection();
			 Statement stmt = conn.createStatement();
			 ResultSet rs =stmt.executeQuery(qry)) {

			while (rs.next()) {
				int id = rs.getInt("pet_id");
				String name = rs.getString("pet_name");
				pets.add(new Pet(id, name));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return pets;
	}

	// A mini class for pets, not the full pet structure, only the ones required here.
	public static class Pet {
		private final int id;
		private final String name;

		public Pet(int id, String name) {
			this.id = id;
			this.name = name;
		}

		public int getId() { return id; }
		public String getName() { return name; }
	}
}