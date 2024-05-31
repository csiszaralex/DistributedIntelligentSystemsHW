package SmartHomeFramework;

import Simulation.Simulator;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private JPanel jPanel1;
    private PaintPanel paintPanel;
    private Simulator simulator;
    private int objectId = 0;
    private JLabel beers;

    public MainFrame() {
        initComponents();
        simulator = new Simulator();
    }

    private void initComponents() {

        jPanel1 = new JPanel();
        paintPanel = new PaintPanel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Smart Home");
        setMinimumSize(new Dimension(917, 740));
        setSize(new Dimension(917, 740));
        getContentPane().setLayout(new BorderLayout());

        jPanel1.setPreferredSize(new Dimension(887, 40));
        jPanel1.setBackground(new Color(255, 255, 224)); // Light yellow background
        jPanel1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

        JButton switchButton = new JButton("Switch object");
        JButton startButton = new JButton("Start");
        startButton.addActionListener(event -> {
            Simulator.setStarted(true);
            startButton.setEnabled(false);
            switchButton.setEnabled(false);
        });
        jPanel1.add(startButton);



        String[] objects = {"placing tricot somewhere else", "planting dirt", "planting vacuumed floor", "planting clean floor"};
        JLabel label = new JLabel("Current " + objects[objectId]);

        switchButton.addActionListener(event -> {
            objectId = (++objectId) % 4;
            label.setText("Current " + objects[objectId]);
        });
        jPanel1.add(switchButton);
        jPanel1.add(label);

        JLabel beers2 = new JLabel("| Beers in the refrigerator: ");
        jPanel1.add(beers2);
        beers = new JLabel("12");
        jPanel1.add(beers);

        getContentPane().add(jPanel1, BorderLayout.NORTH);
        getContentPane().add(paintPanel, BorderLayout.CENTER);

        paintPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                paintPanelMouseClicked(evt);
                refresh();
            }
        });

        pack();
    }

    public JLabel getBeerLabel() {
        return beers;
    }

    private void paintPanelMouseClicked(java.awt.event.MouseEvent evt) {
        paintPanel.mouseClicked(evt.getX(), evt.getY(), objectId);
    }

    public void refresh() {
        this.revalidate();
        this.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
