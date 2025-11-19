package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * Main view for food_stock table
 */
public class FoodStockView extends JFrame {
    private DefaultListModel<String> foodListModel;
    private JList<String> foodList;

    private JButton addButton, removeButton, refreshButton, editButton, allergenButton, menuButton;
    private JTextField nameField, expiryField, boughtField, costField, calField, qtyField;
    private JLabel totalCostLabel;

    public FoodStockView() {
        super("Food Stock Manager");
        initComponents();
        layoutComponents();
    }

    // Getters
    public String getNameInput() { return nameField.getText(); }
    public String getExpiryInput() { return expiryField.getText(); }
    public String getCostInput() { return costField.getText(); }
    public String getCaloriesInput() { return calField.getText(); }
    public String getBoughtInput() { return boughtField.getText(); }
    public String getQtyInput() { return qtyField.getText(); }
    public int getSelectedIndex() { return foodList.getSelectedIndex(); }
    public String getSelectedFood() { return foodList.getSelectedValue(); }
    public int getSelectedFoodId() {
        String selected = foodList.getSelectedValue();
        if (selected == null) return -1;
        try {
            return Integer.parseInt(selected.split("\\|")[0].replace("ID:", "").trim());
        } catch (Exception e) {
            return -1;
        }
    }


    private void initComponents() {
        foodListModel = new DefaultListModel<>();
        foodList = new JList<>(foodListModel);

        addButton = new JButton("Add Product");
        addButton.setBackground(new Color(32,117,111));
        addButton.setForeground(new Color(255, 255, 245));

        removeButton = new JButton("Remove");
        removeButton.setBackground(new Color(200,40,33));
        removeButton.setForeground(new Color(255, 255, 245));

        refreshButton = new JButton("Refresh");
        refreshButton.setBackground(new Color (207, 172, 72));
        refreshButton.setForeground(new Color(255, 255, 245));

        editButton = new JButton("Edit"); 
        editButton.setBackground(new Color(32,117,111));
        editButton.setForeground(new Color(255, 255, 245));

        allergenButton = new JButton("View Allergens");
        allergenButton.setBackground(new Color(200,40,33));
        allergenButton.setForeground(new Color(255, 255, 245));

        menuButton = new JButton("Back to Menu");
        menuButton.setBackground(new Color(32,117,111));
        menuButton.setForeground(new Color(255, 255, 245));

        nameField = new JTextField(10);
        expiryField = new JTextField(10);
        costField = new JTextField(5);
        calField = new JTextField(5);
        boughtField = new JTextField(10);
        qtyField = new JTextField(5);
        totalCostLabel = new JLabel("Total Cost: 0.00");

        setSize(1500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void layoutComponents() {
        setLayout(new BorderLayout());
        setBackground(new Color(163,198,181));

        JPanel inputPanel = new JPanel(new GridLayout(2, 6, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Food Details"));
        inputPanel.setBackground(new Color(255,255,245));

        Color green = new Color(207, 233, 213); 
        JLabel nameLbl = new JLabel("Name");
        nameLbl.setOpaque(true);
        nameLbl.setBackground(green);

        JLabel expiryLbl = new JLabel("Expiry (YYYY-MM-DD)");
        expiryLbl.setOpaque(true);
        expiryLbl.setBackground(green);

        JLabel boughtLbl = new JLabel("Bought Date (YYYY-MM-DD)");
        boughtLbl.setOpaque(true);
        boughtLbl.setBackground(green);

        JLabel costLbl = new JLabel("Cost (PHP)");
        costLbl.setOpaque(true);
        costLbl.setBackground(green);

        JLabel calLbl = new JLabel("Calories (kCal)");
        calLbl.setOpaque(true);
        calLbl.setBackground(green);

        JLabel qtyLbl = new JLabel("Quantity (grams)");
        qtyLbl.setOpaque(true);
        qtyLbl.setBackground(green);
        
        inputPanel.add(nameLbl);
        inputPanel.add(nameField);
        inputPanel.add(expiryLbl);
        inputPanel.add(expiryField);
        inputPanel.add(boughtLbl);
        inputPanel.add(boughtField);
        inputPanel.add(costLbl);
        inputPanel.add(costField);
        inputPanel.add(calLbl);
        inputPanel.add(calField);
        inputPanel.add(qtyLbl);
        inputPanel.add(qtyField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(255,255,245));

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(editButton);   
        buttonPanel.add(allergenButton);
        buttonPanel.add(menuButton);

        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.setBorder(BorderFactory.createTitledBorder("Current Food Stock"));
        listPanel.setBackground(new Color(255,255,245));
        foodList.setBackground(new Color(255,255,245));  
        foodList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(foodList);
        scroll.getViewport().setBackground(new Color(255,255,245));

        listPanel.add(scroll, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(163,198,181));

        bottomPanel.add(buttonPanel, BorderLayout.WEST);
        JPanel costPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        costPanel.setBackground(new Color(255,255,245));

        costPanel.add(totalCostLabel);
        bottomPanel.add(costPanel, BorderLayout.EAST);

        add(inputPanel, BorderLayout.NORTH);
        add(listPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    public void updateFoodList(List<String> foods) {
        foodListModel.clear();
        for (String food : foods)
            foodListModel.addElement(food);
    }

    public void setTotalCost(double cost) {
        totalCostLabel.setText(String.format("Total Cost: %.2f", cost));
    }

    public void addActionListener(ActionListener l) {
        addButton.addActionListener(l);
        removeButton.addActionListener(l);
        refreshButton.addActionListener(l);
        editButton.addActionListener(l);
        allergenButton.addActionListener(l);
        menuButton.addActionListener(l);

        addButton.setActionCommand("Add");
        removeButton.setActionCommand("Remove");
        refreshButton.setActionCommand("Refresh");
        editButton.setActionCommand("Edit");
        allergenButton.setActionCommand("Allergen");
        menuButton.setActionCommand("Menu");
    }
    
    public String[] promptEditField() {
        String[] fields = {"food_name", "date_expiry", "date_bought", "cost", "caloric_count", "stock_qty"};

        JPanel panel = new JPanel(new GridLayout(4, 1, 5, 5));
        JComboBox<String> fieldDropdown = new JComboBox<>(fields);
        JTextField newValueField = new JTextField(10);

        panel.add(new JLabel("Select field to edit:"));
        panel.add(fieldDropdown);
        panel.add(new JLabel("Enter new value:"));
        panel.add(newValueField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Edit Food Field", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String field = (String) fieldDropdown.getSelectedItem();
            String newValue = newValueField.getText().trim();

            if (newValue.isEmpty()) return null;
            return new String[]{field, newValue};
        }
        return null; // user cancelled
    }

}


