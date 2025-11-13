
package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * Dialog for managing allergens linked to a specific food item.
 * This is both a view and a controller.
 */
public class FoodAllergenDialog extends JDialog implements ActionListener {

    private DefaultListModel<String> availableModel;
    private DefaultListModel<String> linkedModel;
    private JList<String> availableList, linkedList;

    private JButton addButton, removeButton, closeButton;

    // Handler for delegating add/remove actions
    private AllergenActionHandler handler;

    /**
     * Functional interface for handling allergen actions. 
     */
    @FunctionalInterface
    public interface AllergenActionHandler {
        void handle(String description, String actionType) throws Exception;
    }

    public FoodAllergenDialog(Frame owner, String foodName, List<String> allAllergens, List<String> linkedAllergens, AllergenActionHandler handler) {
        super(owner, "Manage Allergens for " + foodName, true);
        this.handler = handler;

        initComponents();
        loadInitialData(allAllergens, linkedAllergens);
        layoutComponents();
    }

    /**
     * UI Setup and Initialization
     */

    private void initComponents() {
        availableModel = new DefaultListModel<>();
        linkedModel = new DefaultListModel<>();
        availableList = new JList<>(availableModel);
        linkedList = new JList<>(linkedModel);

        addButton = new JButton("Add →");
        removeButton = new JButton("← Remove");
        closeButton = new JButton("Close");
        // Adds action listeners
        addButton.setActionCommand("Add");
        removeButton.setActionCommand("Remove");
        closeButton.setActionCommand("Close");

        addButton.addActionListener(this);
        removeButton.addActionListener(this);
        closeButton.addActionListener(this);

        setPreferredSize(new Dimension(500, 350));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(getOwner());
    }

    private void layoutComponents() {

        setLayout(new BorderLayout(10, 10));

        JPanel listPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        listPanel.add(new JScrollPane(availableList));
        listPanel.add(new JScrollPane(linkedList));

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(closeButton);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(listPanel, BorderLayout.CENTER);
        centerPanel.add(buttonPanel, BorderLayout.EAST);

        add(centerPanel, BorderLayout.CENTER);

        pack();
        setVisible(true);

    }

    // Load initial data into lists
    private void loadInitialData(List<String> allAllergens, List<String> linkedAllergens) {
        availableModel.clear();
        linkedModel.clear();

        for (String allergen : allAllergens) {
            if (!linkedAllergens.contains(allergen)) {
                availableModel.addElement(allergen);
            }
        }

        for (String linked : linkedAllergens) {
            linkedModel.addElement(linked);
        }
    }

    // Action Handling
    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        switch (cmd) {
            case "Add" -> addAllergen();
            case "Remove" -> removeAllergen();
            case "Close" -> dispose();
        }
    }

    private void addAllergen() {
        String selected = availableList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Please select an allergen to add.");
            return;
        }

        try {
            handler.handle(selected, "add");
            linkedModel.addElement(selected);
            availableModel.removeElement(selected);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error adding allergen: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeAllergen() {
        String selected = linkedList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Please select an allergen to remove.");
            return;
        }

        try {
            handler.handle(selected, "remove");
            availableModel.addElement(selected);
            linkedModel.removeElement(selected);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error removing allergen: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    
}
