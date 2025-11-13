package model;

import util.DBConnector;
import java.sql.*;
import java.util.*;

public class PetInfoModel {

	public Pet getPetInfo(int pet_id) {
		String qry = "SELECT * FROM pet WHERE pet_id = ?";

		try (Connection conn = DBConnector.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(qry)) {
			pstmt.setInt(1, pet_id);
			
			ResultSet rs = pstmt.executeQuery(qry);
			String name = rs.getString("pet_name");
			String species = rs.getString("species");
			Stirng gender = rs.getString("gender");
			int age = rs.getInt("age");
			double weight = rs.getDouble("weight");

			Pet pet = new Pet(name, species, gender, age, weight);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		public static class Pet {
			private final int id;
			private String name;
			private String species;
			private String gender;
			private int age;
			private double weight;

			public Pet(int id, String name, String species, String gender, int age, double weight) {
				this.id = id;
				this.name = name;
				this.species = species;
				this.gender = gender;
				this.age = age;
				this.weight = weight;
			}

			// Getters
			public String getName() { return name; }
			public String getSpecies() { return species; }
			public String getGender() { return gender; }
			public int getAge() { return age; }
			public double getWeight() { return weight; }
		}
	}
}