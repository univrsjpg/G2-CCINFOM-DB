package view;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class PetAllergenView extends JDialog {

    private JTextPane displayPane;
    private StyledDocument doc;
    private JButton backButton;

    public PetAllergenView(JFrame parent) {
        super(parent, "Pet Allergy Record", true);

        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(170, 202, 185)); // same sage green

        backButton = new JButton("Close");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(32, 117, 111)); // dark teal
        backButton.setOpaque(true);
        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);

        backButton.addActionListener(e -> setVisible(false)); // hide dialog
        topPanel.add(backButton);

        add(topPanel, BorderLayout.NORTH);

        displayPane = new JTextPane();
        displayPane.setEditable(false);
        displayPane.setBackground(new Color(255, 255, 245)); // ivory
        doc = displayPane.getStyledDocument();

        JScrollPane scrollPane = new JScrollPane(displayPane);
        add(scrollPane, BorderLayout.CENTER);

        setSize(400, 500);
        setLocationRelativeTo(parent);
    }

    private void append(String text, Color color, boolean bold, int size) {
        SimpleAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setForeground(attrs, color);
        StyleConstants.setBold(attrs, bold);
        StyleConstants.setFontSize(attrs, size);
        try {
            doc.insertString(doc.getLength(), text, attrs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showRecord(String petName, String species, String gender,
                           String age, String weight,
                           java.util.List<String> allergens,
                           java.util.List<String> unsafeFoods) {

        displayPane.setText(""); // reset

        // HEADER
        append("     🐾 PET INFORMATION 🐾\n\n", new Color(32, 117, 111), true, 25);

        append("Name: ", new Color(160, 82, 45), true, 14);
        append(petName + "\n", Color.BLACK, false, 14);

        append("Species: ", new Color(160, 82, 45), true, 14);
        append(species + "\n", Color.BLACK, false, 14);

        append("Gender: ", new Color(160, 82, 45), true, 14);
        append(gender + "\n", Color.BLACK, false, 14);

        append("Age: ", new Color(160, 82, 45), true, 14);
        append(age + "\n", Color.BLACK, false, 14);

        append("Weight: ", new Color(160, 82, 45), true, 14);
        append(weight + "\n\n", Color.BLACK, false, 14);

        // ALLERGENS
        append("⚠ ALLERGENS\n", new Color(200, 41, 33), true, 18);

        if (allergens.isEmpty()) {
            append("• None\n\n", Color.DARK_GRAY, false, 14);
        } else {
            for (String a : allergens)
                append("• " + a + "\n", new Color(200, 41, 33), false, 14);
            append("\n", Color.BLACK, false, 12);
        }

        // UNSAFE FOODS
        append("❌ UNSAFE FOODS\n", new Color(207, 171, 72), true, 18);

        if (unsafeFoods.isEmpty()) {
            append("• None\n", Color.DARK_GRAY, false, 14);
        } else {
            for (String f : unsafeFoods)
                append("• " + f + "\n", new Color(207,171,72), false, 14);
        }

        setVisible(true);
    }

    public void ifEmptyRecord(String message) {
        displayPane.setText(message);
        setVisible(true);
    }
}
