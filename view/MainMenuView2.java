package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenuView2 extends JFrame {
	private JButton viewPetInfo, viewWeight, recordIntake, viewAllergies, goBack;

	// Sets up view
	public MainMenuView2(String pet_name) {
		super(pet_name);
		initComponents();
		layoutComponents();
	}

	// Initializes components
	private void initComponents() {
		viewPetInfo = new JButton("View Info");
		viewWeight = new JButton("View Weight");
		recordIntake = new JButton("Record Food Intake");
		viewAllergies = new JButton("View Allergies");
		goBack = new JButton("Return to last menu");

        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
	}

	// Layouts components
	private void layoutComponents() {
		JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
		buttonPanel.add(viewPetInfo);
		buttonPanel.add(recordIntake);
		buttonPanel.add(viewWeight);
		buttonPanel.add(viewAllergies);

		JPanel backPanel = new JPanel();
		backPanel.add(goBack);

		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 70, 50, 70));
		mainPanel.add(buttonPanel, BorderLayout.CENTER);
		mainPanel.add(backPanels,BorderLayout.SOUTH);
		add(mainPanel);
	}

	// Adds action listener
	public void addActionListener(ActionListener l) {
		viewPetInfo.addActionListener(l);
		recordIntake.addActionListener(l);
		viewWeight.addActionListener(l);
		viewAllergies.addActionListener(l);
		goBack.addActionListener(l);

		viewPetInfo.setActionCommand("ViewPetInfo");
		recordIntake.setActionCommand("RecordIntake");
		viewWeight.setActionCommand("ViewWeight");
		viewAllergies.setActionCommand("ViewAllergies");
		goBack.setActionCommand("Back");
	}
}