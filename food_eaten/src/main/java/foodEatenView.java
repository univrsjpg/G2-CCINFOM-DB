import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * View component for the Food Eaten tracking system.
 * Displays two tables: one for available food stock and one for logged eaten food.
 */
public class foodEatenView extends JFrame {
    // Color scheme
    private final Color COLOR_LIGHT_CYAN = new Color(207, 255, 246);
    private final Color COLOR_TEAL = new Color(32, 117, 111);
    private final Color COLOR_GOLD = new Color(207, 172, 72);

    // Tables
    private JTable stockTable;
    private JTable eatenTable;
    private DefaultTableModel stockTableModel;
    private DefaultTableModel eatenTableModel;

    // Buttons
    private JButton backButton;
    private JButton addToLogButton;
    private JButton inspectButton;
    private JButton refreshButton;

    // Panels
    private JPanel mainPanel;
    private JPanel stockPanel;
    private JPanel eatenPanel;
    private JPanel buttonPanel;

    public foodEatenView() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Food Tracking System");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(COLOR_LIGHT_CYAN);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create split panel for two tables
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(380);
        splitPane.setBackground(COLOR_LIGHT_CYAN);

        // Stock table panel
        stockPanel = createStockPanel();
        splitPane.setLeftComponent(stockPanel);

        // Eaten table panel
        eatenPanel = createEatenPanel();
        splitPane.setRightComponent(eatenPanel);

        mainPanel.add(splitPane, BorderLayout.CENTER);

        // Button panel
        buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createStockPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(COLOR_LIGHT_CYAN);

        JLabel titleLabel = new JLabel("Available Food Stock", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setForeground(COLOR_TEAL);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Stock table columns: Food ID, Name, Quantity, Is Allergen, Available
        String[] stockColumns = {"ID", "Name", "Qty", "Allergen", "Available"};
        stockTableModel = new DefaultTableModel(stockColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        stockTable = new JTable(stockTableModel);
        stockTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        stockTable.setBackground(Color.WHITE);
        stockTable.setGridColor(COLOR_TEAL);
        stockTable.getTableHeader().setBackground(COLOR_GOLD);
        stockTable.getTableHeader().setForeground(Color.BLACK);

        // Set column widths
        stockTable.getColumnModel().getColumn(0).setPreferredWidth(40);
        stockTable.getColumnModel().getColumn(1).setPreferredWidth(120);
        stockTable.getColumnModel().getColumn(2).setPreferredWidth(50);
        stockTable.getColumnModel().getColumn(3).setPreferredWidth(70);
        stockTable.getColumnModel().getColumn(4).setPreferredWidth(70);

        JScrollPane scrollPane = new JScrollPane(stockTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(COLOR_TEAL, 2));
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createEatenPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(COLOR_LIGHT_CYAN);

        JLabel titleLabel = new JLabel("Logged Food Eaten", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setForeground(COLOR_TEAL);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Eaten table columns: Pet ID, Food ID, Date/Time, Serving Size
        String[] eatenColumns = {"Pet ID", "Food ID", "Date/Time", "Serving"};
        eatenTableModel = new DefaultTableModel(eatenColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        eatenTable = new JTable(eatenTableModel);
        eatenTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        eatenTable.setBackground(Color.WHITE);
        eatenTable.setGridColor(COLOR_TEAL);
        eatenTable.getTableHeader().setBackground(COLOR_GOLD);
        eatenTable.getTableHeader().setForeground(Color.BLACK);

        // Set column widths
        eatenTable.getColumnModel().getColumn(0).setPreferredWidth(60);
        eatenTable.getColumnModel().getColumn(1).setPreferredWidth(60);
        eatenTable.getColumnModel().getColumn(2).setPreferredWidth(120);
        eatenTable.getColumnModel().getColumn(3).setPreferredWidth(60);

        JScrollPane scrollPane = new JScrollPane(eatenTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(COLOR_TEAL, 2));
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panel.setBackground(COLOR_LIGHT_CYAN);

        // Back button
        backButton = createStyledButton("Back");
        panel.add(backButton);

        // Add to log button
        addToLogButton = createStyledButton("Add to Log");
        panel.add(addToLogButton);

        // Edit serving size button

        // Inspect button
        inspectButton = createStyledButton("Inspect");
        panel.add(inspectButton);

        // Refresh button
        refreshButton = createStyledButton("Refresh");
        panel.add(refreshButton);

        return panel;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(COLOR_TEAL);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_GOLD, 2),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(COLOR_GOLD);
                button.setForeground(COLOR_TEAL);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(COLOR_TEAL);
                button.setForeground(Color.WHITE);
            }
        });

        return button;
    }

    // Getters for controller access
    public JButton getBackButton() { return backButton; }
    public JButton getAddToLogButton() { return addToLogButton; }
    public JButton getInspectButton() { return inspectButton; }
    public JButton getRefreshButton() { return refreshButton; }

    public JTable getStockTable() { return stockTable; }
    public JTable getEatenTable() { return eatenTable; }

    // Methods to update tables
    public void clearStockTable() {
        stockTableModel.setRowCount(0);
    }

    public void clearEatenTable() {
        eatenTableModel.setRowCount(0);
    }

    public void addStockRow(int foodId, String foodName, double quantity, boolean isAllergen, boolean isAvailable) {
        Object[] row = {
                foodId,
                foodName,
                String.format("%.2f", quantity),
                isAllergen ? "YES" : "NO",
                isAvailable ? "YES" : "NO"
        };
        stockTableModel.addRow(row);
    }

    public void addEatenRow(int petId, int foodId, LocalDateTime dateTime, double servingSize) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd HH:mm");
        Object[] row = {
                petId,
                foodId,
                dateTime.format(formatter),
                String.format("%.2f", servingSize)
        };
        eatenTableModel.addRow(row);
    }

    public int getSelectedStockRow() {
        return stockTable.getSelectedRow();
    }

    public int getSelectedEatenRow() {
        return eatenTable.getSelectedRow();
    }

    public Object getStockValueAt(int row, int column) {
        return stockTableModel.getValueAt(row, column);
    }

    public Object getEatenValueAt(int row, int column) {
        return eatenTableModel.getValueAt(row, column);
    }

    public void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }

    public String showInputDialog(String message, String title, String initialValue) {
        return (String) JOptionPane.showInputDialog(
                this,
                message,
                title,
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                initialValue
        );
    }

    public void showInspectDialog(String title, String content) {
        JDialog dialog = new JDialog(this, title, true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        JTextArea textArea = new JTextArea(content);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setBackground(COLOR_LIGHT_CYAN);
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(textArea);
        dialog.add(scrollPane);

        dialog.setVisible(true);
    }
}