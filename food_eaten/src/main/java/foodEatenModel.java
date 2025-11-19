
import java.sql.*;              // SQL Functionality
import java.time.*;             // Date Conversion,
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 *  This class models the food_eaten table from the pet tracker
 *  database and provides additional methods for allowing easy
 *  access and CRUD operations in between SQL and java.
 */
public class foodEatenModel
{
    public Connection petTracker;

    public foodEatenModel()
    {
        petTracker = createConnection();
    }

    /**
     *  Subclass modelling a unit or row from the food_eaten table.
     */
    public class eatenFood
    {
        public eatenFood(int petId, int foodId, LocalDateTime dateTime, double servingSize)
        {
            this.petId = petId;
            this.foodId = foodId;
            this.dateTime = dateTime;
            this.servingSize = servingSize;
        }

        // getters
        public int getPetId() { return petId; }
        public int getFoodId() { return foodId; }
        public LocalDateTime getDateTime() { return dateTime; }
        public double getServingSize() { return servingSize; }

        private int petId;
        private int foodId;
        private LocalDateTime dateTime;
        private double servingSize;
    }

    // CRUD Operations

    /** read (R)
     * This function reads ALL possible food that was already eaten by the specified pet.
     * @param petId - integer value that allows unique identification of the pet.
     * @return - an array of type eatenFood . Represents all information received
     * from the database regarding all the food that was logged to be eaten by the pet.
     */
    public eatenFood[] readAllFoodEaten(int petId)
    {
        ArrayList<eatenFood> allFoodEaten = new ArrayList<eatenFood>(10);

        try {
            Statement statement = petTracker.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM food_eaten " +
                    "WHERE pet_id = " + petId);

            while (rs.next())
            {
                int foodId = rs.getInt("food_id");
                LocalDateTime dateTime = rs.getTimestamp("date_time").toLocalDateTime();
                double servingSize = rs.getDouble("serving_size");

                allFoodEaten.add(new eatenFood(petId, foodId, dateTime, servingSize));
            }

            statement.close();

        } catch (SQLException e) { e.printStackTrace(); }

        return allFoodEaten.toArray(new eatenFood[0]);
    }

    /** create (C)
     *  This function
     * @param petId        -
     * @param foodId       -
     * @param dateTime     -
     * @param servingSize  -
     */
    public void createFoodEaten(int petId, int foodId, LocalDateTime dateTime, double servingSize)
    {
        String query = "INSERT INTO food_eaten (pet_id, food_id, date_time, serving_size) VALUES" +
                " (?, ?, ?, ?)";

        Timestamp ts = Timestamp.valueOf(dateTime);

        try {
            PreparedStatement statement = petTracker.prepareStatement(query);

            statement.setInt(1, petId);
            statement.setDouble(2, foodId);
            statement.setTimestamp(3, ts);
            statement.setDouble(4, servingSize);

            statement.executeUpdate();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** update (U)
     *  This function edits information regarding the food_eaten table. As food_eaten identifies unique
     *  entries using a primary composite key consisting of the pet_id, food_id and date_time, this function
     *  accepts 3 parameters and only allows editing of the remaining data field // serving_size .
     *
     * @param petId     -
     * @param foodId    -
     * @param dateTime  -
     */
    public void editFoodEaten(int petId, int foodId, LocalDateTime dateTime, double servingSize)
    {
        String query = "UPDATE food_eaten " +
                "SET serving_size = ? " +
                "WHERE pet_id = ? AND " +
                "food_id = ? and date_time = ?";

        Timestamp ts = Timestamp.valueOf(dateTime);


        System.out.println("DEBUG : " + ts.toString());

        try {
            PreparedStatement statement = petTracker.prepareStatement(query);

            System.out.println(servingSize);

            statement.setDouble(1, servingSize);
            statement.setInt(2, petId);
            statement.setInt(3, foodId);
            statement.setTimestamp(4, ts);


            statement.executeUpdate();
            statement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Successfully Edited FoodEaten Value!");
    }

    /**
     * delete (D)
     * @param petId
     * @param foodId
     * @param dateTime
     */
    public void deleteFoodEaten(int petId, int foodId, LocalDateTime dateTime)
    {
        String query = "DELETE FROM food_eaten " +
                "WHERE pet_id = ? AND " +
                "food_id = ? AND date_time = ?";

        java.sql.Date sqlDate = java.sql.Date.valueOf(dateTime.toLocalDate());

        try {
            PreparedStatement statement = petTracker.prepareStatement(query);

            statement.setInt(1, petId);
            statement.setInt(2, foodId);
            statement.setDate(3, sqlDate);

            statement.executeUpdate();
            statement.close();

        } catch (SQLException e) { throw new RuntimeException(e); }

        System.out.println("Successfully Deleted FoodEaten Value!");
    }

    // additional methods

    /**
     *  okay to explain why this class is even remotely important, hear me out okay .
     *  imagine youre in a restaurant and you wanna order from the menu. you think okay
     *  you want a borgar but then they tell you were all out of borgars. dont you wish
     *  they said that before you ordered . exactly . this is meant to be used specifically
     *  for reading from SQL such that we instantly know what food can be logged to foodEaten
     *  without having to exit to stock then go back again.
     */
    public class genericFood
    {
        private int foodId;
        private boolean isAllergen;
        private boolean isAvailable;

        public genericFood(int foodId, boolean isAvailable, boolean isAllergen)
        {
            this.foodId = foodId;
            this.isAvailable = isAvailable;
            this.isAllergen = isAllergen;
        }

        public int getFoodId() { return foodId; }
        public boolean isAllergen() { return isAllergen; }
        public boolean isAvailable() { return isAvailable; }
    }

    /**
     * This function gets all the food that can be eaten or all the food that was already
     * eaten but not both.
     *
     * @return
     */
    public genericFood[] readAllOuterFood(int petId)                      // get all food exclusive, tag which ones are eaten and allergens
    {
        ArrayList<genericFood> eatenXorNotEaten = new ArrayList<genericFood>(10);

        try {

            String subquery = "SELECT food_id FROM food_allergen fa " +
                    "JOIN pet_allergy pa ON fa.allergen_id = pa.allergen_id " +
                    "WHERE pa.pet_id = ?";

            String query = "SELECT " +
                    "fs.food_id, " +
                    "(fs.stock_qty > 0) AS is_available, "  +
                    "(fs.food_id IN (" + subquery + ")) AS is_allergen " +                 // this disregards all already eaten allergen
                    "FROM food_stock fs " +
                    "LEFT OUTER JOIN food_eaten fe " +
                    "ON fs.food_id = fe.food_id AND fe.pet_id = ? " +
                    "WHERE fe.food_id IS NULL OR fs.stock_qty = 0";

                    /*
                    "FULL OUTER JOIN food_eaten fe " +
                    "ON fs.food_id = fe.food_id " +
                    "WHERE fe.serving_size = 0 OR " +
                    "(fs.cost = 0 AND pet_id = ?)"; */

            PreparedStatement statement = petTracker.prepareStatement(query);

            statement.setInt(1, petId);
            statement.setInt(2, petId);

            ResultSet rs = statement.executeQuery();

            while (rs.next())
            {
                int foodId = rs.getInt("food_id");
                boolean isAvailable = rs.getBoolean("is_available");
                boolean isAllergen = rs.getBoolean("is_allergen");

                eatenXorNotEaten.add(new genericFood(foodId, isAvailable, isAllergen));
            }

            statement.close();

        } catch (SQLException e) { e.printStackTrace(); }

        return eatenXorNotEaten.toArray(new genericFood[0]);
    }

    /**
     * This function returns all the food that is both still available
     * and also have been eaten before.
     * @return
     */
    public genericFood[] readAllInnerFood(int petId)
    {
        ArrayList<genericFood> eatenAndNotEaten = new ArrayList<genericFood>(10);

        try {
            String subquery = "SELECT food_id FROM food_allergen fa " +
                    "JOIN pet_allergy pa ON fa.allergen_id = pa.allergen_id " +
                    "WHERE pa.pet_id = ?";

            String query = "SELECT " +
                    "fs.food_id, " +
                    "(fs.stock_qty > 0) AS is_available, "  +           // thisll return all true
                    "(fe.food_id IN (" + subquery + ")) AS is_allergen " +
                    "FROM food_stock fs " +
                    "INNER JOIN food_eaten fe " +
                    "ON fs.food_id = fe.food_id " +
                    "WHERE pet_id = ?";

            PreparedStatement statement = petTracker.prepareStatement(query);
            statement.setInt(1, petId);
            statement.setInt(2, petId);
            ResultSet rs = statement.executeQuery();

            while (rs.next())
            {
                int foodId = rs.getInt("food_id");
                boolean isAvailable = rs.getBoolean("is_available");
                boolean isAllergen = rs.getBoolean("is_allergen");

                eatenAndNotEaten.add(new genericFood(foodId, isAvailable, isAllergen));
            }

            statement.close();

        } catch (SQLException e) { e.printStackTrace(); }

        return eatenAndNotEaten.toArray(new genericFood[0]);
    }

    public genericFood[] combineAllFood(genericFood[] eatenXorNotEaten, genericFood[] eatenAndNotEaten)
    {
        ArrayList<genericFood> allFood = new ArrayList<genericFood>(Arrays.asList(eatenXorNotEaten));

        Collections.addAll(allFood, eatenAndNotEaten);

        return allFood.toArray(new genericFood[0]);
    }

    public void deductFoodstock(int foodId)
    {
        String query = "DELETE FROM food_stock " +
                "WHERE food_id = ?";

        try {
            PreparedStatement statement = petTracker.prepareStatement(query);

            statement.setInt(1, foodId);

            statement.executeUpdate();
            statement.close();

        } catch (SQLException e) { throw new RuntimeException(e); }

        System.out.println("Successfully Deleted From FoodStock!");
    }


      public void updateStockQuantity(int foodId, double newQuantity) {
          String query = "UPDATE food_stock SET stock_qty = ? WHERE food_id = ?";
          try {
              PreparedStatement statement = petTracker.prepareStatement(query);
              statement.setDouble(1, newQuantity);
              statement.setInt(2, foodId);
              statement.executeUpdate();
              statement.close();
              System.out.println("Successfully Updated Stock Quantity!");
          } catch (SQLException e) {
              e.printStackTrace();
          }
      }

     public class foodStockDetails {
          public int foodId;
          public String foodName;
          public double stockQty;
          public double cost;
          public double caloricCount;
          public String dateExpiry;
          public String dateBought;

          public foodStockDetails(int foodId, String foodName, double stockQty,
                                  double cost, double caloricCount,
                                  String dateExpiry, String dateBought) {
              this.foodId = foodId;
              this.foodName = foodName;
              this.stockQty = stockQty;
              this.cost = cost;
              this.caloricCount = caloricCount;
              this.dateExpiry = dateExpiry;
              this.dateBought = dateBought;
          }
      }

      public foodStockDetails getFoodStockById(int foodId) {
          try {
              String query = "SELECT food_id, food_name, stock_qty, cost, caloric_count, " +
                            "date_expiry, date_bought FROM food_stock WHERE food_id = ?";
              PreparedStatement stmt = petTracker.prepareStatement(query);
              stmt.setInt(1, foodId);
              ResultSet rs = stmt.executeQuery();

              if (rs.next()) {
                  foodStockDetails details = new foodStockDetails(
                      rs.getInt("food_id"),
                      rs.getString("food_name"),
                      rs.getDouble("stock_qty"),
                      rs.getDouble("cost"),
                      rs.getDouble("caloric_count"),
                      rs.getDate("date_expiry").toString(),
                      rs.getDate("date_bought").toString()
                  );
                  stmt.close();
                  return details;
              }
              stmt.close();
          } catch (SQLException e) {
              e.printStackTrace();
          }
          return null;
      }

    // reminder to change this for sql once it works out
    private Connection createConnection() throws RuntimeException
    {
        Connection connection = null;
        try {
            // loading the JDBC Driver .. Change mariadb to mysql hyperlink when necessary
            Class.forName("org.mariadb.jdbc.Driver");
            System.out.println("driver loaded");

            // this is for my laptop , change user and password args as needed ,
            // also the jdbc:mariadb
            connection = DriverManager.getConnection
                    ("jdbc:mariadb://localhost/pettracker", "celia", "Myckelyn29");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        System.out.println("It works!");
        return connection;
    }
}
