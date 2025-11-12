package view;

import model.MainMenuModel1;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class MainMenuView1 extends JFrame {
	private JPanel petButtonPanel;
	private JButton viewStockButton, addPetButton, removePetButton;

	// Sets up the view for users.
	public MainMenuView1() {
		super("Welcome to Pet Tracker!");
		initComponents();
		layoutComponents();
	}

	// Initializes components
	private void initComponents() {
		petButtonPanel = new JPanel(new GridLayout(3, 3, 5, 5));
		petButtonPanel.setBorder(BorderFactory.createTitledBorder("Select a Pet:"));

		viewStockButton = new JButton("View Stock");
		addPetButton = new JButton("Add New Pet");
		removePetButton = new JButton("Remove a Pet");

		setSize(800, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	// Layouts the view.
	private void layoutComponents() {
		JPanel petPanel = new JPanel(new BorderLayout());
		petPanel.add(petButtonPanel, BorderLayout.CENTER);
		petPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

		JPanel actionPanel = new JPanel(new GridLayout(1, 3, 10, 10));
		actionPanel.add(viewStockButton);
		actionPanel.add(addPetButton);
		actionPanel.add(removePetButton);

		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, petPanel, actionPanel);
		splitPane.setResizeWeight(0.85);
		splitPane.setDividerSize(0);
		splitPane.setEnabled(false);

		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 70, 50, 70));
		mainPanel.add(splitPane, BorderLayout.CENTER);
		add(mainPanel, BorderLayout.CENTER);
	}

	// Adds the action listener from the controller.
	public void addActionListener(ActionListener l) {
		for (Component c : petButtonPanel.getComponents()) {
			if (c instanceof JButton petButton) {
				petButton.addActionListener(l);
			}
		}

		viewStockButton.addActionListener(l);
		addPetButton.addActionListener(l);
		removePetButton.addActionListener(l);

		viewStockButton.setActionCommand("ViewStock");
		addPetButton.setActionCommand("AddPet");
		removePetButton.setActionCommand("RemovePet");
	}

	// Updates the list of pets and their names.
	public void updatePetList(List<MainMenuModel1.Pet> pets) {
		petButtonPanel.removeAll();

		if (pets.isEmpty()) {
			petButtonPanel.add(new JLabel("You have no pets!"));
		}
		else {
			for (MainMenuModel1.Pet pet : pets) {
				JButton petButton = new JButton(pet.getName());
				petButton.putClientProperty("pet_id", pet.getId());
				petButton.putClientProperty("pet_name", pet.getName());
				petButton.setActionCommand("SelectPet");
				petButtonPanel.add(petButton);
			}
		}

		petButtonPanel.revalidate();
		petButtonPanel.repaint();
	}
}