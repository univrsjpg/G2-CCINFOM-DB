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
		addButton.setBackground(new Color(200,40,33));
		addButton.setForeground(new Color(255, 255, 245));
		backButton = new JButton ("Return to main menu");
		backButton.setBackground(new Color(32,117,111));
		backButton.setForeground(new Color(255, 255, 245));
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
		JPanel headerPanel = new JPanel(new BorderLayout()){
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

				g2.setColor(getBackground());
				g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);

				g2.dispose();
			}
		};

		headerPanel.setOpaque(false);
		headerPanel.setBackground(new Color(255, 255, 245));
		headerPanel.setLayout(new BorderLayout());

		JLabel titleLabel = new JLabel("<html><div style='font-size: 30px; font-weight: bold; text-align: center;'>" +
								   "<span style='color:#C82821'>A</span>" +
								   "<span style='color:#ad903b'>D</span>" +
								   "<span style='color:#20756F'>D </span>" +
								   "<span style='color:#C82821'>A </span>" +
								   "<span style='color:#ad903b'>N</span>" +
								   "<span style='color:#20756F'>E</span>" +
								   "<span style='color:#C82821'>W </span>" +
								   "<span style='color:#ad903b'>P</span>" +
								   "<span style='color:#20756F'>E</span>" +
								   "<span style='color:#C82821'>T</span>" +
								   "</div></html>");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setVerticalAlignment(SwingConstants.CENTER);

		headerPanel.add(titleLabel);
		headerPanel.setBorder(BorderFactory.createEmptyBorder(2, 30, 2, 30));

		JPanel headerWrapper = new JPanel();
		headerWrapper.add(headerPanel);
		headerWrapper.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		headerWrapper.setBackground(new Color(163,198,181));



		JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
		namePanel.add(new JLabel("<html><h2 style='color:#ad903b'>Name:</h2></html>"));
		namePanel.add(nameField);

		JPanel speciesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
		speciesPanel.add(new JLabel("<html><h2 style='color:#20756F'>Species:</h2></html>"));
		speciesPanel.add(speciesField);

		JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
		genderPanel.add(new JLabel("<html><h2 style='color:#C82821'>Gender:</h2></html>"));
		genderPanel.add(femaleButton);
		genderPanel.add(maleButton);

		JPanel agePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
		agePanel.add(new JLabel("<html><h2 style='color:#ad903b'>Age:</h2></html>"));
		agePanel.add(ageField);

		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
		inputPanel.add(namePanel);
		inputPanel.add(speciesPanel);
		inputPanel.add(genderPanel);
		inputPanel.add(agePanel);
		inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));



		JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 10, 10));
		bottomPanel.add(addButton);
		bottomPanel.add(backButton);
		bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		bottomPanel.setBackground(new Color(163,198,181));



		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, inputPanel, bottomPanel);
		splitPane.setResizeWeight(0.80);
		splitPane.setDividerSize(0);
		splitPane.setEnabled(false);
		splitPane.setBackground(new Color(163,198,181));




		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 70, 50, 70));
		mainPanel.add(headerWrapper, BorderLayout.NORTH);
		mainPanel.add(splitPane, BorderLayout.CENTER);
		mainPanel.setBackground(new Color(163,198,181));
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
}