package controller;

import model.foodEatenModel;
import view.foodEatenView;

import view.MainMenuView2;
import model.MainMenuModel2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.swing.JOptionPane;

/**
 * Controller component for the Food Eaten tracking system.
 * Handles all business logic and coordinates between View and Model.
 */
public class foodEatenController {
    private foodEatenView view;
    private foodEatenModel model;
    private int currentPetId;

    // Store food stock information for validation
    private FoodStockInfo[] currentStockInfo;

    /**
     * Inner class to store complete food stock information
     */
    private class FoodStockInfo {
        int foodId;
        String foodName;
        double stockQty;
        double cost;
        double caloricCount;
        String dateExpiry;
        String dateBought;
        boolean isAllergen;
        boolean isAvailable;

        public FoodStockInfo(int foodId, String foodName, double stockQty, double cost,
                             double caloricCount, String dateExpiry, String dateBought,
                             boolean isAllergen, boolean isAvailable) {
            this.foodId = foodId;
            this.foodName = foodName;
            this.stockQty = stockQty;
            this.cost = cost;
            this.caloricCount = caloricCount;
            this.dateExpiry = dateExpiry;
            this.dateBought = dateBought;
            this.isAllergen = isAllergen;
            this.isAvailable = isAvailable;
        }
    }

    public foodEatenController(foodEatenView view, int petId) {
        this.currentPetId = petId;
        this.model = new foodEatenModel();
        this.view = view;

        initializeController();
        loadData();
        view.setVisible(true);
    }

    private void initializeController() {
        // Back button - left empty for merging
        view.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();

                MainMenuView2 menu2View = new MainMenuView2();
                MainMenuModel2 menu2Model = new MainMenuModel2();
                new MainMenuController2(menu2View, menu2Model, currentPetId);

                menu2View.setVisible(true);
            }
        });

        // Add to log button
        view.getAddToLogButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAddToLog();
            }
        });

        // Inspect button
        view.getInspectButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleInspect();
            }
        });

        // Refresh button
        view.getRefreshButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadData();
                loadStockTable();
                loadEatenTable();
            }
        });
    }

    /**
     * Loads all data from database through model
     */
    private void loadData() {
        loadStockTable();
        loadEatenTable();
    }

    /**
     * Loads available food stock into the stock table
     */
    private void loadStockTable() {
        view.clearStockTable();

        try {
            // Get all food items with allergen and availability info
            foodEatenModel.genericFood[] outerFood = model.readAllOuterFood(currentPetId);
            foodEatenModel.genericFood[] innerFood = model.readAllInnerFood(currentPetId);
            foodEatenModel.genericFood[] allFood = model.combineAllFood(outerFood, innerFood);

            // Get detailed stock information from database
            currentStockInfo = getFoodStockDetails(allFood);

            // Populate table
            for (FoodStockInfo info : currentStockInfo) {
                view.addStockRow(
                        info.foodId,
                        info.foodName,
                        info.stockQty,
                        info.isAllergen,
                        info.isAvailable
                );
            }

        } catch (Exception e) {
            view.showMessage("Error loading stock data: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private FoodStockInfo[] getFoodStockDetails(foodEatenModel.genericFood[] genericFoods) {
        java.util.ArrayList<FoodStockInfo> stockList = new java.util.ArrayList<>();

        for (foodEatenModel.genericFood gf : genericFoods) {
            foodEatenModel.foodStockDetails details = model.getFoodStockById(gf.getFoodId());

            if (details != null) {
                FoodStockInfo info = new FoodStockInfo(
                        details.foodId,
                        details.foodName,
                        details.stockQty,
                        details.cost,
                        details.caloricCount,
                        details.dateExpiry,
                        details.dateBought,
                        gf.isAllergen(),
                        gf.isAvailable()
                );
                stockList.add(info);
            }
        }

        return stockList.toArray(new FoodStockInfo[0]);
    }

    /**
     * Loads logged eaten food into the eaten table
     */
    private void loadEatenTable() {
        view.clearEatenTable();

        try {
            foodEatenModel.eatenFood[] allEaten = model.readAllFoodEaten(currentPetId);

            for (foodEatenModel.eatenFood eaten : allEaten) {
                view.addEatenRow(
                        eaten.getPetId(),
                        eaten.getFoodId(),
                        eaten.getDateTime(),
                        eaten.getServingSize()
                );
            }

        } catch (Exception e) {
            view.showMessage("Error loading eaten food data: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * Handles adding selected food from stock to eaten log
     */
    private void handleAddToLog() {
        int selectedRow = view.getSelectedStockRow();

        if (selectedRow == -1) {
            view.showMessage("Please select a food item from the stock table.",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Get food info from selected row
        int foodId = (Integer) view.getStockValueAt(selectedRow, 0);
        String allergenStatus = (String) view.getStockValueAt(selectedRow, 3);
        String availableStatus = (String) view.getStockValueAt(selectedRow, 4);

        // Validate allergen status
        if (allergenStatus.equals("YES")) {
            view.showMessage("Cannot log this food: It is an allergen for this pet!",
                    "Allergen Alert", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate availability
        if (availableStatus.equals("NO")) {
            view.showMessage("Cannot log this food: It is not available/out of stock!",
                    "Not Available", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get stock quantity for validation
        FoodStockInfo stockInfo = findStockInfo(foodId);
        if (stockInfo == null) {
            view.showMessage("Error: Could not find food stock information.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Prompt for serving size
        String servingSizeStr = view.showInputDialog(
                "Enter serving size (Available: " + String.format("%.2f", stockInfo.stockQty) + "):",
                "Add Food to Log",
                "1.0"
        );

        if (servingSizeStr == null) return; // User cancelled

        try {
            double servingSize = Double.parseDouble(servingSizeStr);

            // Validate serving size
            if (servingSize <= 0) {
                view.showMessage("Serving size must be greater than 0!",
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (servingSize > stockInfo.stockQty) {
                view.showMessage("Serving size cannot exceed available stock quantity!",
                        "Insufficient Stock", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Create food eaten entry
            LocalDateTime now = LocalDateTime.now();
            model.createFoodEaten(currentPetId, foodId, now, servingSize);

            // Deduct from stock if serving size matches or exceeds stock quantity
            if (servingSize >= stockInfo.stockQty) {
                model.deductFoodstock(foodId);
            } else {
                // Update stock quantity (reduce by serving size)
                updateStockQuantity(foodId, stockInfo.stockQty - servingSize);
            }

            view.showMessage("Food successfully logged!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);

            // Refresh tables
            loadData();

        } catch (NumberFormatException e) {
            view.showMessage("Invalid serving size format. Please enter a number.",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            view.showMessage("Error adding food to log: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * Handles editing serving size of logged food
     */
    private void handleEditServing() {
        int selectedRow = view.getSelectedEatenRow();

        if (selectedRow == -1) {
            view.showMessage("Please select a food entry from the eaten table.",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Get current values
        int petId = (Integer) view.getEatenValueAt(selectedRow, 0);
        int foodId = (Integer) view.getEatenValueAt(selectedRow, 1);
        String dateTimeStr = (String) view.getEatenValueAt(selectedRow, 2);
        String currentServing = (String) view.getEatenValueAt(selectedRow, 3);

        // Parse date time
        LocalDateTime dateTime;
        try {
            String withTheYearrrrr = LocalDate.now().getYear() + "/" + dateTimeStr;

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

            dateTime = LocalDateTime.parse(withTheYearrrrr, formatter);
        } catch (DateTimeParseException e) {
            view.showMessage("Error parsing date/time.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Prompt for new serving size
        String newServingStr = view.showInputDialog(
                "Enter new serving size:",
                "Edit Serving Size",
                currentServing
        );

        if (newServingStr == null) return; // User cancelled

        try {
            double newServing = Double.parseDouble(newServingStr);

            if (newServing <= 0) {
                view.showMessage("Serving size must be greater than 0!",
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Update in database
            model.editFoodEaten(petId, foodId, dateTime, newServing);

            view.showMessage("Serving size updated successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);

            // Refresh eaten table
            loadData();
            loadEatenTable();

        } catch (NumberFormatException e) {
            view.showMessage("Invalid serving size format. Please enter a number.",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            view.showMessage("Error updating serving size: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * Handles inspection of selected food item
     */
    private void handleInspect() {
        int stockRow = view.getSelectedStockRow();
        int eatenRow = view.getSelectedEatenRow();

        if (stockRow != -1) {
            // Inspect stock item
            inspectStockItem(stockRow);
        } else if (eatenRow != -1) {
            // Inspect eaten item
            inspectEatenItem(eatenRow);
        } else {
            view.showMessage("Please select an item to inspect.",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Shows detailed inspection of stock item
     */
    private void inspectStockItem(int row) {
        int foodId = (Integer) view.getStockValueAt(row, 0);
        FoodStockInfo info = findStockInfo(foodId);

        if (info == null) {
            view.showMessage("Error: Could not find food information.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        StringBuilder details = new StringBuilder();
        details.append("=== FOOD STOCK DETAILS ===\n\n");
        details.append(String.format("Food ID: %d\n", info.foodId));
        details.append(String.format("Food Name: %s\n", info.foodName));
        details.append(String.format("Stock Quantity: %.2f\n", info.stockQty));
        details.append(String.format("Cost: $%.2f\n", info.cost));
        details.append(String.format("Caloric Count: %.2f\n", info.caloricCount));
        details.append(String.format("Date Bought: %s\n", info.dateBought));
        details.append(String.format("Date Expiry: %s\n", info.dateExpiry));
        details.append(String.format("Is Allergen: %s\n", info.isAllergen ? "YES" : "NO"));
        details.append(String.format("Is Available: %s\n", info.isAvailable ? "YES" : "NO"));

        view.showInspectDialog("Stock Item Details", details.toString());
    }

    /**
     * Shows detailed inspection of eaten item
     */
    private void inspectEatenItem(int row) {
        int petId = (Integer) view.getEatenValueAt(row, 0);
        int foodId = (Integer) view.getEatenValueAt(row, 1);
        String dateTimeStr = (String) view.getEatenValueAt(row, 2);
        String servingStr = (String) view.getEatenValueAt(row, 3);

        // Get food name from stock info
        FoodStockInfo stockInfo = findStockInfo(foodId);
        String foodName = stockInfo != null ? stockInfo.foodName : "Unknown";

        StringBuilder details = new StringBuilder();
        details.append("=== EATEN FOOD DETAILS ===\n\n");
        details.append(String.format("Pet ID: %d\n", petId));
        details.append(String.format("Food ID: %d\n", foodId));
        details.append(String.format("Food Name: %s\n", foodName));
        details.append(String.format("Date/Time: %s\n", dateTimeStr));
        details.append(String.format("Serving Size: %s\n", servingStr));

        if (stockInfo != null) {
            details.append(String.format("\nCaloric Count (per unit): %.2f\n", stockInfo.caloricCount));
            double totalCalories = stockInfo.caloricCount * Double.parseDouble(servingStr);
            details.append(String.format("Total Calories: %.2f\n", totalCalories));
        }

        view.showInspectDialog("Eaten Food Details", details.toString());
    }

    /**
     * Finds stock info by food ID
     */
    private FoodStockInfo findStockInfo(int foodId) {
        if (currentStockInfo == null) return null;

        for (FoodStockInfo info : currentStockInfo) {
            if (info.foodId == foodId) {
                return info;
            }
        }
        return null;
    }

    private void updateStockQuantity(int foodId, double newQuantity) {
        model.updateStockQuantity(foodId, newQuantity);
    }
}