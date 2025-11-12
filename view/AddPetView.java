package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddPetView extends JFrame {
	private JButton addButton, backButton, femaleButton, maleButton;
	private JTextField nameField, speciesField, ageField;

	public AddPetView() {
		super("Adding a new pet!");
		initComponents();
		layoutComponents();
	}

	// Getters
	public String getNameInput() {return nameField.getText();}
	public String getSpeciesInput() {return speciesField.getText();}
	public String getAgeInput() {return ageField.getText();}

	private void initComponents() {
		addButton = new JButton ("Add pet");
		backButton = new JButton ("Return to main menu");
		femaleButton = new JButton ("Female");
		maleButton = new JButton ("Male");

		nameField = new JTextField(30);
		speciesField = new JTextField(15);
		ageField = new JTextField(3);

		nameField.setPreferredSize(new Dimension(200, 25));
		speciesField.setPreferredSize(new Dimension(200, 25));
		ageField.setPreferredSize(new Dimension(85, 25));

        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
	}

	private void layoutComponents() {
		JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
		namePanel.add(new JLabel("Name:"));
		namePanel.add(nameField);

		JPanel speciesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
		speciesPanel.add(new JLabel("Species:"));
		speciesPanel.add(speciesField);

		JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
		genderPanel.add(new JLabel("Gender:"));
		genderPanel.add(femaleButton);
		genderPanel.add(maleButton);

		JPanel agePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
		agePanel.add(new JLabel("Age:"));
		agePanel.add(ageField);

		JPanel bottomPanel = new JPanel();
		bottomPanel.add(addButton);
		bottomPanel.add(backButton);

		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
		inputPanel.add(namePanel);
		inputPanel.add(speciesPanel);
		inputPanel.add(genderPanel);
		inputPanel.add(agePanel);

		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 70, 50, 70));
		mainPanel.add(inputPanel, BorderLayout.CENTER);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);
		add(mainPanel);
	}

	public void addActionListener(ActionListener l) {
		addButton.addActionListener(l);
		backButton.addActionListener(l);
		femaleButton.addActionListener(l);
		maleButton.addActionListener(l);

		addButton.setActionCommand("Add");
		backButton.setActionCommand("Back");
		femaleButton.setActionCommand("Female");
		maleButton.setActionCommand("Male");
	}

	public void showMessage(String msg) {
		JOptionPane.showMessageDialog(this, msg);
	}

	public void updateGender(String gender) {
		if (gender.equals("Female")) {
			femaleButton.setBackground(Color.LIGHT_GRAY);
			maleButton.setBackground(null);
		}

		else if (gender.equals("Male")) {
			maleButton.setBackground(Color.LIGHT_GRAY);
			femaleButton.setBackground(null);
		}
	}
}