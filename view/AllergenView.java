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
    private JButton addButton, deleteButton, editButton, viewLinksButton;
    private JTextArea linkedFoods;


    public AllergenView() {
        super("Allergens");
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
        viewLinksButton = new JButton("View Food with Allergen");

        linkedFoods = new JTextArea(5, 30);
        linkedFoods.setEditable(false);

        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(170, 202, 185));

        getRootPane().setDefaultButton(addButton);
    }

    public void addActionListener(ActionListener al) {
        addButton.addActionListener(al);
        deleteButton.addActionListener(al);
        editButton.addActionListener(al);
        viewLinksButton.addActionListener(al);
        viewLinksButton.setActionCommand("View Food with Allergen");

    }

    public void layoutComponents() {
        setLayout(new BorderLayout(10, 10));

        // left table: allergen id & desc
        JPanel allergenPanel = new JPanel(new BorderLayout());
        allergenPanel.setBorder(BorderFactory.createTitledBorder("Allergens"));

        Color ivory = new Color(255, 255, 245);
        allergenTable.setBackground(ivory);
        allergenTable.setOpaque(true);

        JScrollPane allergenScroll = new JScrollPane(allergenTable);
        allergenScroll.getViewport().setBackground(ivory);
        allergenScroll.setBackground(ivory);

        allergenPanel.setBackground(new Color(170, 202, 185));
        allergenPanel.add(allergenScroll, BorderLayout.CENTER);
        add(allergenPanel, BorderLayout.CENTER);

        // top panel: input fields and buttons
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(new Color(170, 202, 185));

        //input description
        JPanel descPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        descPanel.setBackground(new Color(170, 202, 185));
        JLabel descLabel = new JLabel("Allergen Description:");
        descLabel.setForeground(Color.WHITE);
        descLabel.setFont(new Font("Arial", Font.BOLD, 15));
        descPanel.add(descLabel);
        descPanel.add(descTextField);

        // viewing linked food stock button
        JPanel viewPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        viewPanel.add(viewLinksButton);
        viewPanel.setBackground(new Color(170, 202, 185));
        viewLinksButton.setBackground(new Color(207, 171, 72));
        viewLinksButton.setForeground(Color.WHITE);
        // mac stuff; remove setopaque and setBorderPainted for ALL buttons if not on mac bc mac
        // ignores jbutton bg color, not sure sa windows tho
        viewLinksButton.setOpaque(true);
        viewLinksButton.setBorderPainted(false);

        // add/edit/delete buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        buttonPanel.setBackground(new Color(170, 202, 185));
        addButton.setBackground(new Color(32, 117, 111));
        addButton.setForeground(Color.WHITE);
        // remove
        addButton.setOpaque(true);
        addButton.setBorderPainted(false);

        editButton.setBackground(new Color(207, 171, 72));
        editButton.setForeground(Color.WHITE);
        // remove
        editButton.setOpaque(true);
        editButton.setBorderPainted(false);

        deleteButton.setBackground(new Color(200, 41, 33));
        deleteButton.setForeground(Color.WHITE);
        // remove
        deleteButton.setOpaque(true);
        deleteButton.setBorderPainted(false);

        topPanel.add(descPanel);
        topPanel.add(viewPanel);
        topPanel.add(buttonPanel);

        add(topPanel, BorderLayout.NORTH);

        // linked food stock
        JPanel foodPanel = new JPanel(new BorderLayout());
        foodPanel.setBorder(BorderFactory.createTitledBorder("Food stock"));
        foodPanel.add(new JScrollPane(linkedFoods), BorderLayout.CENTER);
        foodPanel.setBackground(new Color(170, 202, 185));
        linkedFoods.setBackground(new Color(255, 255, 245));

        add(foodPanel, BorderLayout.EAST);
    }


    public void displayLinkedFoods(String text) {
        linkedFoods.setText(text);
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
