package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class AllergenView extends JFrame {
    private JTable allergenTable;
    private DefaultTableModel tableModel;
    private JTextField descTextField;
    private JButton addButton, deleteButton, editButton;

    public AllergenView() {
        super("Allergen Management");
        initializeComponents();
        layoutComponents();
        setVisible(true);
    }

    public void displayMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    private void initializeComponents() {
        String[] columnNames = {"ID", "Description"};
        tableModel = new DefaultTableModel(columnNames, 0);
        allergenTable = new JTable(tableModel);

        descTextField = new JTextField(20);

        addButton = new JButton("Add Allergen");
        deleteButton = new JButton("Delete Allergen");
        editButton = new JButton("Edit Allergen");

        addButton.setActionCommand("Add");
        deleteButton.setActionCommand("Delete");
        editButton.setActionCommand("Edit");

        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getRootPane().setDefaultButton(addButton);
    }

    public void addActionListener(ActionListener al) {
        addButton.addActionListener(al);
        deleteButton.addActionListener(al);
        editButton.addActionListener(al);
    }

    public void layoutComponents() {
        setLayout(new BorderLayout(10, 10));

        JScrollPane tableScrollPane = new JScrollPane(allergenTable);
        add(tableScrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Allergen Description:"));
        inputPanel.add(descTextField);
        add(inputPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(editButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public int getSelectedAllergenId() {
        int row = allergenTable.getSelectedRow();
        if (row == -1) return -1;
        return Integer.parseInt(tableModel.getValueAt(row, 0).toString());
    }

    public String getSelectedAllergenDescription() {
        int row = allergenTable.getSelectedRow();
        if (row == -1) return null;
        return tableModel.getValueAt(row, 1).toString();
    }

    public String getDescriptionInput() {
        return descTextField.getText();
    }

    public void clearDescriptionInput() {
        descTextField.setText("");
    }

    public void setAllergenData(List<String[]> allergens) {
        tableModel.setRowCount(0);
        for (String[] allergen : allergens) {
            tableModel.addRow(allergen);
        }
    }

    public String promptForNewDesc() {
        return JOptionPane.showInputDialog(this, "Enter new allergen description:");
    }
}
