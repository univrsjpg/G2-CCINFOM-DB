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
		petButtonPanel = new JPanel(new GridLayout(3, 4, 5, 5));
		petButtonPanel.setBorder(BorderFactory.createTitledBorder("Select a Pet:"));

		viewStockButton = new JButton("View Stock");
		viewStockButton.setBackground(new Color(200,40,33));
		viewStockButton.setForeground(new Color(255, 255, 245));
		addPetButton = new JButton("Add New Pet");
		addPetButton.setBackground(new Color (207, 172, 72));
		addPetButton.setForeground(new Color(255, 255, 245));
		removePetButton = new JButton("Remove a Pet");
		removePetButton.setBackground(new Color(32,117,111));
		removePetButton.setForeground(new Color(255, 255, 245));

		setSize(800, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	// Layouts the view.
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

		headerPanel.setOpaque(false);  // important so rounded shape shows properly
		headerPanel.setBackground(new Color(255, 255, 245)); // header color
		headerPanel.setLayout(new BorderLayout());

		JLabel titleLabel = new JLabel("<html><div style='font-size: 30px; font-weight: bold; text-align: center;'>" +
								   "<span style='color:#C82821'>P</span>" +
								   "<span style='color:#ad903b'>E</span>" +
								   "<span style='color:#20756F'>T </span>" +
								   "<span style='color:#C82821'>T</span>" +
								   "<span style='color:#ad903b'>R</span>" +
								   "<span style='color:#20756F'>A</span>" +
								   "<span style='color:#C82821'>C</span>" +
								   "<span style='color:#ad903b'>K</span>" +
								   "<span style='color:#20756F'>E</span>" +
								   "<span style='color:#C82821'>R</span>" +
								   "</div></html>");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setVerticalAlignment(SwingConstants.CENTER);

		//ImageIcon corgiBread = new ImageIcon(getClass().getResource("/icons/Sticker - CorgiBread.png"));
		//JLabel corgiLabel = new JLabel(corgiBread);

		headerPanel.add(titleLabel);
		//headerPanel.add(corgiLabel);
		headerPanel.setBorder(BorderFactory.createEmptyBorder(2, 30, 2, 30));

		JPanel headerWrapper = new JPanel();
		headerWrapper.add(headerPanel);
		headerWrapper.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		headerWrapper.setBackground(new Color(163,198,181));

		JPanel petPanel = new JPanel(new BorderLayout());
		petPanel.add(petButtonPanel, BorderLayout.CENTER);
		petPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		petPanel.setBackground(new Color(163,198,181));

		JPanel actionPanel = new JPanel(new GridLayout(1, 3, 10, 10));
		actionPanel.add(viewStockButton);
		actionPanel.add(addPetButton);
		actionPanel.add(removePetButton);
		actionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		actionPanel.setBackground(new Color(255, 255, 245));

		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, petPanel, actionPanel);
		splitPane.setResizeWeight(0.85);
		splitPane.setDividerSize(0);
		splitPane.setEnabled(false);

		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 70, 50, 70));
		mainPanel.add(headerWrapper, BorderLayout.NORTH);
		mainPanel.add(splitPane, BorderLayout.CENTER);
		mainPanel.setBackground(new Color(163,198,181));
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
		int count = 0;

		if (pets.isEmpty()) {
			petButtonPanel.add(new JLabel("You have no pets!"));
		}
		else {
			for (MainMenuModel1.Pet pet : pets) {
				count += 1;
				JButton petButton = new JButton(pet.getName());
				petButton.putClientProperty("pet_id", pet.getId());
				petButton.putClientProperty("pet_name", pet.getName());
				petButton.setActionCommand("SelectPet");

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
}