package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class FoodStockView extends JFrame {
    private DefaultListModel<String> foodListModel;
    private JList<String> foodList;

    private JButton addButton, removeButton, refreshButton, editButton;
    private JTextField nameField, expiryField, costField, calField, boughtField, qtyField;

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

    private void initComponents() {
        foodListModel = new DefaultListModel<>();
        foodList = new JList<>(foodListModel);

        addButton = new JButton("Add");
        removeButton = new JButton("Remove");
        refreshButton = new JButton("Refresh");
        editButton = new JButton("Edit"); 

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

        JPanel formPanel = new JPanel(new GridLayout(2, 6, 5, 5));
        formPanel.add(new JLabel("Name"));
        formPanel.add(new JLabel("Expiry (YYYY-MM-DD)"));
        formPanel.add(new JLabel("Bought Date"));
        formPanel.add(new JLabel("Cost"));
        formPanel.add(new JLabel("Calories"));
        formPanel.add(new JLabel("Quantity"));

        formPanel.add(nameField);
        formPanel.add(expiryField);
        formPanel.add(boughtField);
        formPanel.add(costField);
        formPanel.add(calField);
        formPanel.add(qtyField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(editButton);   

        add(formPanel, BorderLayout.NORTH);
        add(new JScrollPane(foodList), BorderLayout.CENTER);
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

        addButton.setActionCommand("Add");
        removeButton.setActionCommand("Remove");
        refreshButton.setActionCommand("Refresh");
        editButton.setActionCommand("Edit");
    }
    
    public String[] promptEditField() {
        String[] fields = {"food_name", "date_expiry", "date_bought", "cost", "caloric_count", "stock_qty"};

        JPanel panel = new JPanel(new GridLayout(2, 1, 5, 5));
        JComboBox<String> fieldDropdown = new JComboBox<>(fields);
        JTextField newValueField = new JTextField(10);

        panel.add(new JLabel("Select field to edit:"));
        panel.add(fieldDropdown);
        panel.add(new JLabel("Enter new value:"));
        panel.add(newValueField);

        int result = JOptionPane.showConfirmDialog(
            this, panel, "Edit Food Field", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String field = (String) fieldDropdown.getSelectedItem();
            String newValue = newValueField.getText().trim();

            if (newValue.isEmpty()) return null;
            return new String[]{field, newValue};
        }
        return null; // user cancelled
    }

}
