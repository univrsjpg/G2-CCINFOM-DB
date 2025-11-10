package controller;

import view.FoodStockView;
import model.FoodStockModel;

import java.awt.event.*;
import java.sql.Date;
import java.util.List;

public class FoodStockController implements ActionListener {
    private final FoodStockView view;
    private final FoodStockModel model;

    public FoodStockController(FoodStockView view, FoodStockModel model) {
        this.view = view;
        this.model = model;
        this.view.addActionListener(this);
        refreshList();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Add" -> addFood();
            case "Remove" -> removeFood();
            case "Refresh" -> refreshList();
            case "Edit" -> editFood();  
        }
    }

    private void addFood() {
        try {
            String name = view.getNameInput();
            Date expiry = Date.valueOf(view.getExpiryInput());
            double cost = Double.parseDouble(view.getCostInput());
            double cal = Double.parseDouble(view.getCaloriesInput());
            Date bought = Date.valueOf(view.getBoughtInput());
            double qty = Double.parseDouble(view.getQtyInput());

            model.addFood(name, expiry, bought, cost, cal, qty);
            view.showMessage("Food added!");
            refreshList();
        } catch (Exception ex) {
            view.showMessage("Invalid input. Please check your data.");
            ex.printStackTrace();
        }
    }

    private void removeFood() {
        int index = view.getSelectedIndex();
        if (index < 0) {
            view.showMessage("Please select an item to remove.");
            return;
        }

        // Parse ID from the displayed string
        String selected = view.getSelectedFood();
        int id = Integer.parseInt(selected.split("\\|")[0].replace("ID:", "").trim());

        model.deleteFood(id);
        view.showMessage("Food deleted!");
        refreshList();
    }

    private void editFood() {
        int index = view.getSelectedIndex();
        if (index < 0) {
            view.showMessage("Please select an item to edit.");
            return;
        }

        String selected = view.getSelectedFood();
        int id = Integer.parseInt(selected.split("\\|")[0].replace("ID:", "").trim());

        String[] editData = view.promptEditField();
        if (editData == null) return; // user cancelled

        String field = editData[0];
        String newValue = editData[1];
        
        view.showMessage(model.updateFoodField(id, field, newValue));
        refreshList();
    }



    private void refreshList() {
        List<String> foods = model.listFoods();
        view.updateFoodList(foods);
    }
}
