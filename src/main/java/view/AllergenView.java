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
    private JButton addButton, deleteButton, editButton, viewLinksButton, viewPetRecordButton, backToMenuButton;
    private JTextArea linkedFoods;

    private int pet_id; 

    public AllergenView(int petId) {
        super("Allergen Management");
        this.pet_id = petId; 

        initializeComponents();
        layoutComponents();
        setVisible(true);
    }

    public int getPetId() {
        return pet_id;
    }

    public void displayMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    private void initializeComponents() {

        // Table
        String[] col = {"ID", "Description"};
        tableModel = new DefaultTableModel(col, 0);
        allergenTable = new JTable(tableModel);

        // Input
        descTextField = new JTextField(20);

        // Buttons
        addButton = new JButton("Add Allergen");
        deleteButton = new JButton("Delete Allergen");
        editButton = new JButton("Edit Allergen");

        addButton.setActionCommand("Add");
        deleteButton.setActionCommand("Delete");
        editButton.setActionCommand("Edit");

        viewLinksButton = new JButton("View Food with Allergen");
        viewLinksButton.setActionCommand("View Food with Allergen");

        viewPetRecordButton = new JButton("View Pet Allergy Record");
        viewPetRecordButton.setActionCommand("View Pet Allergy Record");

        backToMenuButton = new JButton("Back to Main Menu");
        backToMenuButton.setActionCommand("BackToMainMenu");

        // Linked foods area
        linkedFoods = new JTextArea(5, 30);
        linkedFoods.setEditable(false);

        // Window settings
        setSize(900, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(170, 202, 185));

        getRootPane().setDefaultButton(addButton);
    }

    public void addActionListener(ActionListener al) {
        addButton.addActionListener(al);
        deleteButton.addActionListener(al);
        editButton.addActionListener(al);
        viewLinksButton.addActionListener(al);
        viewPetRecordButton.addActionListener(al);
        backToMenuButton.addActionListener(al);
    }

    public void layoutComponents() {

        setLayout(new BorderLayout(10, 10));

        Color sage = new Color(170, 202, 185);
        Color ivory = new Color(255, 255, 245);

        JPanel allergenPanel = new JPanel(new BorderLayout());
        allergenPanel.setBorder(BorderFactory.createTitledBorder("Allergens"));
        allergenPanel.setBackground(sage);

        JScrollPane scroll = new JScrollPane(allergenTable);
        scroll.setBackground(ivory);
        scroll.getViewport().setBackground(ivory);

        allergenPanel.add(scroll, BorderLayout.CENTER);

        add(allergenPanel, BorderLayout.CENTER);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(sage);

        // Description input
        JPanel descPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        descPanel.setBackground(sage);
        JLabel descLabel = new JLabel("Allergen Description:");
        descLabel.setForeground(Color.WHITE);
        descLabel.setFont(new Font("Arial", Font.BOLD, 14));
        descPanel.add(descLabel);
        descPanel.add(descTextField);

        // View linked foods button
        JPanel viewPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        viewPanel.setBackground(sage);
        viewPanel.add(viewLinksButton);

        // Add/Edit/Delete buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(sage);

        // Back to menu
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.add(backToMenuButton);
        add(bottomPanel, BorderLayout.SOUTH);

        styleButton(addButton, new Color(32, 117, 111));   
        styleButton(editButton, new Color(207, 171, 72));  
        styleButton(deleteButton, new Color(200, 41, 33)); 
        styleButton(viewLinksButton, new Color(32, 117, 111));
        styleButton(viewPetRecordButton, new Color(207, 171, 72));
        styleButton(backToMenuButton, new Color(170, 202, 185));

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        buttonPanel.add(viewPetRecordButton);
        topPanel.add(descPanel);
        topPanel.add(viewPanel);
        topPanel.add(buttonPanel);

        add(topPanel, BorderLayout.NORTH);

        JPanel foodPanel = new JPanel(new BorderLayout());
        foodPanel.setBorder(BorderFactory.createTitledBorder("Food Stock"));
        foodPanel.setBackground(sage);

        linkedFoods.setBackground(ivory);
        foodPanel.add(new JScrollPane(linkedFoods), BorderLayout.CENTER);

        add(foodPanel, BorderLayout.EAST);
    }

    private void styleButton(JButton b, Color bg) {
        b.setForeground(Color.WHITE);
        b.setBackground(bg);
        b.setOpaque(true);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
    }

    public void displayLinkedFoods(String text) {
        linkedFoods.setText(text);
    }

    public int getSelectedAllergenId() {
        int r = allergenTable.getSelectedRow();
        if (r == -1) return -1;
        return Integer.parseInt(tableModel.getValueAt(r, 0).toString());
    }

    public String getSelectedAllergenDescription() {
        int r = allergenTable.getSelectedRow();
        if (r == -1) return null;
        return tableModel.getValueAt(r, 1).toString();
    }

    public String getDescriptionInput() {
        return descTextField.getText();
    }

    public void clearDescriptionInput() {
        descTextField.setText("");
    }

    public void setAllergenData(List<String[]> allergens) {
        tableModel.setRowCount(0);
        for (String[] a : allergens) {
            tableModel.addRow(a);
        }
    }

    public String promptForNewDesc() {
        return JOptionPane.showInputDialog(this, "Enter new allergen description:");
    }
}
