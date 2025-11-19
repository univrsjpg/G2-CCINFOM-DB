# G2-CCINFOM-DB
<br> GROUP 2 - Chai, Gab, Coby, Ash

## To create database:
1. Please run 'pettracker-structure.sql' to get the schema and table structures.
<br> 2. Please run 'pettracker-data-inserts.sql' to get the sample data inserts.

## To run program:
1. The program makes a connection to the root with the password 'DnDLSU_Help', please set your root password to this to ensure proper connection.
<br>2. Compile the program.
<br>      To compile in WINDOWS: `javac -cp ".;lib/mysql-connector-j-9.5.0.jar" Main.java controller/*.java model/*.java view/*.`
<br>      To compile in LINUX or MAC: `javac -cp ".:lib/mysql-connector-j-9.5.0.jar" Main.java controller/*.java model/*.java view/*.java`
<br>3. Run the program.
<br>      To run in WINDOWS: `java -cp ".;lib/mysql-connector-j-9.5.0.jar" Main`
<br>      To run in LINUX or MAC: `java -cp ".:lib/mysql-connector-j-9.5.0.jar" Main`

## Business Constraints:
1. The app currently does not support automated animal-specific and food-specific allergy and allergen tracking. Everything must be manually input by the user.
2. A new weight record can only be inserted once a day.
3. If a food item is not present in the food_stock table, a record using it for food_intake cannot be made.
