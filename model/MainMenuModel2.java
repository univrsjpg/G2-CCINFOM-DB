package model;

import util.DBConnector;
import java.sql.*;
import java.util.*;

public class MainMenuModel2 {

	// Gets the name of pet
	public String getPetName(int pet_id) {
		String qry = "SELECT pet_name FROM pet WHERE pet_id = " + pet_id;

		try (Connection conn = DBConnector.getConnection();
			 Statement stmt = conn.createStatement();
			 ResultSet rs =stmt.executeQuery(qry)) {

			rs.next();
			String name = rs.getString("pet_name");

			return name;
		}

		catch (SQLException e) {
			e.printStackTrace();
			return "";
		}
	}
}