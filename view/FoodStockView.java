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

    private JButton addButton, removeButton, refreshButton, editButton, allergenButton;
    private JTextField nameField, expiryField, boughtField, costField, calField, qtyField;

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
        removeButton = new JButton("Remove");
        refreshButton = new JButton("Refresh");
        editButton = new JButton("Edit"); 
        allergenButton = new JButton("View Allergens");

        nameField = new JTextField(10);
        expiryField = new JTextField(10);
        costField = new JTextField(5);
        calField = new JTextField(5);
        boughtField = new JTextField(10);
        qtyField = new JTextField(5);

        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void layoutComponents() {
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(2, 6, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Food Details"));
        inputPanel.add(new JLabel("Name"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Expiry (YYYY-MM-DD)"));
        inputPanel.add(expiryField);
        inputPanel.add(new JLabel("Bought Date (YYYY-MM-DD)"));
        inputPanel.add(boughtField);
        inputPanel.add(new JLabel("Cost (PHP)"));
        inputPanel.add(costField);
        inputPanel.add(new JLabel("Calories (kCal)"));
        inputPanel.add(calField);
        inputPanel.add(new JLabel("Quantity (grams)"));
        inputPanel.add(qtyField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(editButton);   
        buttonPanel.add(allergenButton);

        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.setBorder(BorderFactory.createTitledBorder("Current Food Stock"));
        foodList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listPanel.add(new JScrollPane(foodList), BorderLayout.CENTER);

        add(inputPanel, BorderLayout.NORTH);
        add(listPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    public void updateFoodList(List<String> foods) {
        foodListModel.clear();
        for (String food : foods)
            foodListModel.addElement(food);
    }

    public void addActionListener(ActionListener l) {
        addButton.addActionListener(l);
        removeButton.addActionListener(l);
        refreshButton.addActionListener(l);
        editButton.addActionListener(l);
        allergenButton.addActionListener(l);

        addButton.setActionCommand("Add");
        removeButton.setActionCommand("Remove");
        refreshButton.setActionCommand("Refresh");
        editButton.setActionCommand("Edit");
        allergenButton.setActionCommand("Allergen");
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
