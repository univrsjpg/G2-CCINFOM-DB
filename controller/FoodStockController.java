package controller;

import view.FoodStockView;
import view.FoodAllergenDialog;
import model.FoodStockModel;

import java.awt.Frame;
import java.awt.event.*;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;

public class FoodStockController implements ActionListener {
    private FoodStockView view;
    private FoodStockModel model;

    public FoodStockController(FoodStockView view, FoodStockModel model) {
        this.view = view;
        this.model = model;
        this.view.addActionListener(this);
        refreshList();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd.equals("Add")) {
            addFood();
        } else if (cmd.equals("Remove")) {
            removeFood();
        } else if (cmd.equals("Refresh")) {
            refreshList();
        } else if (cmd.equals("Edit")) {
            editFood();
        } else if (cmd.equals("Allergen")) {
            viewAllergens();
        }
        else if (cmd.equals("Back")) {
            handleBack();
        }
    }

    /**
     * Add a new food item into food_stock table.
     */
    private void addFood() {
        try {
            String name = view.getNameInput();
            Date expiry = Date.valueOf(view.getExpiryInput());
            Date bought = Date.valueOf(view.getBoughtInput());
            double cost = Double.parseDouble(view.getCostInput());
            double cal = Double.parseDouble(view.getCaloriesInput());
            double qty = Double.parseDouble(view.getQtyInput());

            model.addFood(name, expiry, bought, cost, cal, qty); // Calls the SQL statement
            view.showMessage("Food added successfully!");
            refreshList();
        } catch (Exception ex) {
            ex.printStackTrace();
            view.showMessage("Invalid input. Please check your data and try again.");
        }
    }

    /**
     * Removes the selected food from the database.
     */
    private void removeFood() {
        int index = view.getSelectedIndex();
        if (index < 0) {
            view.showMessage("Please select an item to remove.");
            return;
        }

        String selected = view.getSelectedFood();
        int id = Integer.parseInt(selected.split("\\|")[0].replace("ID:", "").trim());

        model.deleteFood(id); // Calls the SQL statement
        view.showMessage("Food deleted successfully!");
        refreshList();
    }

    /**
     * Edits a specific field for a selected food item.
     */
    private void editFood() {
        int index = view.getSelectedIndex();
        if (index < 0) {
            view.showMessage("Please select an item to edit.");
            return;
        }

        String selected = view.getSelectedFood();
        int id = Integer.parseInt(selected.split("\\|")[0].replace("ID:", "").trim());

        String[] editData = view.promptEditField();
        if (editData == null) {
            return; // user cancelled edit
        }

        String field = editData[0];
        String newValue = editData[1];

        String message = model.updateFoodField(id, field, newValue); // Calls the SQL statement
        view.showMessage(message);
        refreshList();
    }

    /**
     * Opens the allergen management dialog for the selected food.
     */
    private void viewAllergens() {
        int foodId = view.getSelectedFoodId();
        if (foodId < 0) {
            view.showMessage("Please select a food item first.");
            return;
        }

        String foodName = model.getFoodName(foodId); 

        try {
            // Fetch all allergens and linked allergens
            Map<String, Integer> allAllergens = model.getAllAllergens();
            List<String> linkedAllergens = model.getLinkedAllergens(foodId);

            // Open the dialog
            FoodAllergenDialog dialog = new FoodAllergenDialog(
                (Frame) SwingUtilities.getWindowAncestor(view),
                foodName,
                new ArrayList<>(allAllergens.keySet()), // list of all allergen descriptions
                linkedAllergens,
                (description, actionType) -> {
                    int allergenId = allAllergens.get(description); // get ID from map

                    if (actionType.equals("add")) {
                        model.addAllergenToFood(foodId, allergenId); // Calls the SQL statement
                    } else if (actionType.equals("remove")) {
                        model.removeAllergenFromFood(foodId, allergenId); // Calls the SQL statement
                    }
                }
            );

        } catch (SQLException ex) {
            view.showMessage("Error loading allergens: " + ex.getMessage());
        }
    }

    /**
     * Refreshes the on-screen food list.
     */
    private void refreshList() {
        List<String> foods = model.listFoods();
        view.updateFoodList(foods);
    }

    private void handleBack() {
        SwingUtilities.getWindowAncestor(view).dispose(); // closes the current window/panel
    }
}
