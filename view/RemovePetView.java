package view;

import model.MainMenuModel1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class RemovePetView extends JFrame {
	private JPanel petButtonPanel;
	private JButton backButton;

	public RemovePetView() {
		super("Removing a pet");
		initComponents();
		layoutComponents();
	}

	private void initComponents() {
		petButtonPanel = new JPanel(new GridLayout(3,3,5,5));
		petButtonPanel.setBorder(BorderFactory.createTitledBorder("Choose a Pet:"));

		backButton = new JButton("Return to main menu");
		backButton.setBackground(new Color(255, 255, 245));

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
								   "<span style='color:#C82821'>R</span>" +
								   "<span style='color:#ad903b'>E</span>" +
								   "<span style='color:#20756F'>M</span>" +
								   "<span style='color:#C82821'>O</span>" +
								   "<span style='color:#ad903b'>V</span>" +
								   "<span style='color:#20756F'>E </span>" +
								   "<span style='color:#C82821'>A </span>" +
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
		headerWrapper.setBackground(new Color(209, 63, 63));


		JPanel petPanel = new JPanel(new BorderLayout());
		petPanel.add(petButtonPanel, BorderLayout.CENTER);
		petPanel.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));

		JPanel bottomPanel = new JPanel();
		bottomPanel.add(backButton);
		bottomPanel.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
		bottomPanel.setBackground(new Color(209, 63, 63));

		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(20,70,50,70));
		mainPanel.add(headerWrapper, BorderLayout.NORTH);
		mainPanel.add(petPanel, BorderLayout.CENTER);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);
		mainPanel.setBackground(new Color(209, 63, 63));
		add(mainPanel, BorderLayout.CENTER);
	}

	public void addActionListener(ActionListener l) {
		for (Component c : petButtonPanel.getComponents()) {
			if (c instanceof JButton petButton) {
				petButton.addActionListener(l);
			}
		}

		backButton.addActionListener(l);
		backButton.setActionCommand("Back");
	}

	public void updatePetList(List<MainMenuModel1.Pet> pets) {
		petButtonPanel.removeAll();

		if (pets.isEmpty()) {
			petButtonPanel.add(new JLabel("You have no pets!"));
		}
		else {
			int count = 0;
			for (MainMenuModel1.Pet pet : pets) {
				count++;
				JButton petButton = new JButton(pet.getName());
				petButton.putClientProperty("pet_id", pet.getId());
				petButton.putClientProperty("pet_name", pet.getName());
				petButton.setActionCommand("RemovePet");

				switch(count){
					case 1:
						petButton.setBackground(new Color(200,40,33));
						petButton.setForeground(new Color(255, 255, 245));
						break;
					case 2:
						petButton.setBackground(new Color(207, 172, 72));
						petButton.setForeground(new Color(255, 255, 245));
						break;
					case 3:
						petButton.setBackground(new Color(32,117,111));
						petButton.setForeground(new Color(255, 255, 245));
						count = 0;
						break;
				}

				petButtonPanel.add(petButton);
			}
		}

		petButtonPanel.revalidate();
		petButtonPanel.repaint();
	}

	public void showMessage (String msg) {
		JOptionPane.showMessageDialog(this, msg);
	}
}