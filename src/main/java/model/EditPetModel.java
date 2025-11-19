package model;

import util.DBConnector;
import java.sql.*;
import java.util.*;

public class EditPetModel {

	public void changeGender(int pet_id, String gender) {
		String qry = "UPDATE pet SET gender = '" + gender + "' WHERE pet_id = " + pet_id;

		try (Connection conn = DBConnector.getConnection();
			 Statement stmt = conn.createStatement()) {
			stmt.executeUpdate(qry);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void changeName(int pet_id, String name) {
		String qry = "UPDATE pet SET pet_name = '" + name + "' WHERE pet_id = " + pet_id;

		try (Connection conn = DBConnector.getConnection();
			 Statement stmt = conn.createStatement()) {
			stmt.executeUpdate(qry);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void changeSpecies(int pet_id, String species) {
		String qry = "UPDATE pet SET species = '" + species + "' WHERE pet_id = " + pet_id;

		try (Connection conn = DBConnector.getConnection();
			 Statement stmt = conn.createStatement()) {
			stmt.executeUpdate(qry);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void changeAge(int pet_id, int age) {
		String qry = "UPDATE pet SET age = " + age + " WHERE pet_id = " + pet_id;

		try (Connection conn = DBConnector.getConnection();
			 Statement stmt = conn.createStatement()) {
			stmt.executeUpdate(qry);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

	}
}