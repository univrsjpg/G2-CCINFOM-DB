import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import org.jfree.chart.*;

public class WeightView extends JFrame
{
    // Mian Pannels
    private JPanel background;          // Layer 0  (root)
    private JPanel mainPanel;           // Layer 1 ... child of background
    private JPanel dogPanel;            // Layer 1 ... child of background
    private JPanel UIPanel;             // Layer 2 ... child of mainPanel
    private JPanel displayPanel;        // Layer 2 ... child of mainPanel

    // JFreeChart Stuff
    private ChartPanel chartPanel;      // Layer 3 ... child of displayPanel
    private JFreeChart yearChart;
    private JFreeChart monthChart;
    private JFreeChart dayChart;

    // Controller
    private WeightController controller;

    public WeightView()
    {
        setTitle("Weight History Window");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 500);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    public void addActionListener(WeightController weightController) {
        this.controller = weightController;

        Container content = getContentPane();
        content.setLayout(new BorderLayout());

        background = new JPanel();
        mainPanel = new JPanel();
        dogPanel = new JPanel();
        UIPanel = new JPanel();
        displayPanel = new JPanel();

        // bottom up init
        initBackground();
        initMainPanel();
        initDogPanel();

        initUIPanel();
        initDisplayPanel();

        // top to bottom adding
        mainPanel.add(UIPanel, BorderLayout.SOUTH);
        mainPanel.add(displayPanel, BorderLayout.NORTH);

        background.add(mainPanel, BorderLayout.WEST);
        background.add(dogPanel);

        content.add(background);
        setVisible(true);
    }

    public void updateGraphChoice(WeightController.graphChoice choice)
    {
        switch (choice)
        {
            case YEAR:
                chartPanel.setChart(yearChart);
                break;
            case MONTH:
                chartPanel.setChart(monthChart);
                break;
            case DAY:
                chartPanel.setChart(dayChart);
                break;
        }
    }

    // Private Initializer Methods .
    // Separated them just to make it more legible .

    private void initBackground()
    {
        background.setLayout(new BorderLayout());
        background.setBounds(0, 0, 800, 500);
        background.setBackground(new Color(207, 172, 72));
    }

    private void initMainPanel()
    {
        mainPanel.setLayout(new BorderLayout());

        mainPanel.setBackground(new Color(32, 117, 111));
        mainPanel.setBounds(0, 0, 550, 330);
        mainPanel.setPreferredSize(new Dimension(550, 330));    // leaves 250, 150 for dogPanel
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    }

    // subdivision of main panel
    // check if you can view per day, month, year
    private void initUIPanel()
    {
        UIPanel.setLayout(new FlowLayout());
        UIPanel.setBackground(new Color(200, 40, 33));
        UIPanel.setPreferredSize(new Dimension(40, 30));
        // border to like round rectangle if there's time

        // back button
        JButton toDayGraph = new JButton("Per Day");
        toDayGraph.setActionCommand("Day");
        // backGraph.setPreferredSize(new Dimension(20, 20));
        // border customization
        toDayGraph.addActionListener(controller);

        UIPanel.add(toDayGraph);

        JButton toMonthGraph = new JButton("Per Month");
        toMonthGraph.setActionCommand("Month");
        toMonthGraph.addActionListener(controller);

        UIPanel.add(toMonthGraph);

        JButton toYearGraph = new JButton("Per Year");
        toYearGraph.setActionCommand("Year");
        toYearGraph.addActionListener(controller);

        UIPanel.add(toYearGraph);

        JButton weightInputButt = new JButton("Add");
        weightInputButt.setPreferredSize(new Dimension(100, 20));
        weightInputButt.setBounds(0, 0, 100, 20);
        weightInputButt.setHorizontalAlignment(JTextField.CENTER);

        weightInputButt.setActionCommand("WeightInput");
        weightInputButt.addActionListener(controller);

        UIPanel.add(weightInputButt);
    }

    private void initDisplayPanel()
    {
        yearChart = controller.createYearGraph();
        monthChart = controller.createMonthGraph();
        dayChart = controller.createDayGraph();

        // default is dayChart
        chartPanel = new ChartPanel(dayChart);
        chartPanel.setPreferredSize(new Dimension(500, 400));

        displayPanel.add(chartPanel);
    }

    // edit this to go be able to go back if needed

    private void initDogPanel() {
        dogPanel.setLayout(new BorderLayout());
        dogPanel.setBackground(new Color(207, 172, 72));
        dogPanel.setPreferredSize(new Dimension(250, 150));

        JButton backButton = new JButton("Back");
        backButton.setActionCommand("Back");
        backButton.addActionListener(controller);

        /*
        try {
            BufferedImage theDog = ImageIO.read(new File("/home/celia/Documents/G2-CCINFOM-DB/weight_history/src/main/resources/icons/gymcat3.png"));
            JLabel doneIs = new JLabel(new ImageIcon(theDog));

            doneIs.setPreferredSize("");

            dogPanel.add(doneIs, BorderLayout.SOUTH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
         */

        dogPanel.add(backButton, BorderLayout.NORTH);
    }
}