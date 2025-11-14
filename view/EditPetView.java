package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class EditPetView extends JFrame {
	private JButton editButton, exitButton, femaleButton, maleButton;
	private JTextField nameField, speciesField, ageField;

	public EditPetView() {
		super("Editing pet information.");
		initComponents();
		layoutComponents();
	}

	private void initComponents(){
		editButton = new JButton("Save changes");
		editButton.setBackground(new Color(32,117,111));
		editButton.setForeground(new Color(255, 255, 245));

		exitButton = new JButton("Exit");
		exitButton.setBackground(new Color(200,40,33));
		exitButton.setForeground(new Color(255, 255, 245));

		femaleButton = new JButton ("Female");
		maleButton = new JButton ("Male");

		nameField = new JTextField(30);
		speciesField = new JTextField(15);
		ageField = new JTextField(3);

		nameField.setPreferredSize(new Dimension(80, 25));
		speciesField.setPreferredSize(new Dimension(100, 25));
		ageField.setPreferredSize(new Dimension(85, 25));

		setSize(500, 300);
		setLocationRelativeTo(null);
	}

	private void layoutComponents() {
		JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
		namePanel.add(new JLabel("<html><p style='color:#ad903b'>Name:</p></html>"));
		namePanel.add(nameField);

		JPanel speciesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
		speciesPanel.add(new JLabel("<html><p style='color:#20756F'>Species:</p></html>"));
		speciesPanel.add(speciesField);

		JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
		genderPanel.add(new JLabel("<html><p style='color:#C82821'>Gender:</p></html>"));
		genderPanel.add(femaleButton);
		genderPanel.add(maleButton);

		JPanel agePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
		agePanel.add(new JLabel("<html><p style='color:#ad903b'>Age:</p></html>"));
		agePanel.add(ageField);

		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
		inputPanel.add(namePanel);
		inputPanel.add(speciesPanel);
		inputPanel.add(genderPanel);
		inputPanel.add(agePanel);
		inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));



		JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 10, 10));
		bottomPanel.add(editButton);
		bottomPanel.add(exitButton);
		bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		bottomPanel.setBackground(new Color(163,198,181));

		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		mainPanel.add(inputPanel, BorderLayout.CENTER);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);
		mainPanel.setBackground(new Color(163,198,181));
		add(mainPanel);
	}

	public void addActionListener(ActionListener l) {
		editButton.addActionListener(l);
		exitButton.addActionListener(l);
		femaleButton.addActionListener(l);
		maleButton.addActionListener(l);

		editButton.setActionCommand("Save");
		exitButton.setActionCommand("Exit");
		femaleButton.setActionCommand("Female");
		maleButton.setActionCommand("Male");
	}

	public void showMessage(String msg) {
		JOptionPane.showMessageDialog(this, msg);
	}

	public void updateGender(String gender) {
		if (gender.equals("Female")) {
			femaleButton.setBackground(new Color(200,40,33));
			femaleButton.setForeground(new Color(255, 255, 245));
			maleButton.setBackground(null);
			maleButton.setForeground(Color.BLACK);
		}

		else if (gender.equals("Male")) {
			maleButton.setBackground(new Color(200,40,33));
			maleButton.setForeground(new Color(255, 255, 245));
			femaleButton.setBackground(null);
			femaleButton.setForeground(Color.BLACK);
		}
	}

	public ArrayList<ArrayList<String>> sendInfo() {
		ArrayList<ArrayList<String>> changes = new ArrayList<>();

		if (!nameField.getText().isEmpty()) {
			ArrayList<String> sublist = new ArrayList<>();
			sublist.add("pet_name");
			sublist.add(nameField.getText());
			changes.add(sublist);
		}

		if (!speciesField.getText().isEmpty()) {
			ArrayList<String> sublist = new ArrayList<>();
			sublist.add("species");
			sublist.add(speciesField.getText());
			changes.add(sublist);
		}

		if (!ageField.getText().isEmpty()) {
			ArrayList<String> sublist = new ArrayList<>();
			sublist.add("age");
			sublist.add(ageField.getText());
			changes.add(sublist);
		}

		return changes;
	} 
}