# G2-CCINFOM-DB-APP
<br> S13 GROUP 2 - Chelsea Dimaunahan, Jacoba Luna, Gabriel Salamida, Ashley Reyes
<br>GITHUB LINK
<br>https://github.com/univrsjpg/G2-CCINFOM-DB/tree/submission

## To create database:
1. Please run 'pettracker-script-v2.sql' to get the schema and its data inserts.

## To run program:
1. The program makes a connection to the root with the password 'password', please set your root password to this to ensure proper connection.
2. Compile the program. Ensure that you have Maven installed in order for the program to run smoothly.
<br>      To compile: `mvn clean compile`
3. Run the program.
<br>      To run: `mvn exec:java`

## Business Constraints:
1. The app currently does not support automated animal-specific and food-specific allergy and allergen tracking. Everything must be manually input by the user.
2. A new weight record can only be inserted once a day.
3. If a food item is not present in the food_stock table, a record using it for food_intake cannot be made.
4. Records to food_eaten define serving_size to be the amount of the food_item served in GRAMS. Likewise, the involved stock quantity is represented in grams.
