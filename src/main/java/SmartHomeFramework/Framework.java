package SmartHomeFramework;

import Simulation.HouseEnvironment;
import Simulation.Simulator;
import House.fields.Map;

import javax.swing.*;

public class Framework {
    private static Map map = null;
    private static MainFrame mainFrame = null;
    private static HouseEnvironment houseEnvironment = null;
    private static Simulator simulator = null;
    private static int agentId = 0;
    private static PaintPanel PP;
    private static int stage = 0;

    public static void refresh() {
        if (mainFrame != null)
            mainFrame.refresh();
    }

    public static JLabel getBeers() {
        return mainFrame.getBeerLabel();
    }

    public static void setPaintPanel(PaintPanel p) {
        PP = p;
    }

    public static PaintPanel getPaintPanel() {
        return PP;
    }

    public static void setEnvironment(HouseEnvironment environment){
        houseEnvironment = environment;
    }

    public static HouseEnvironment getEnvironment(){
        return houseEnvironment;
    }

    public static Simulator getSimulator(){
        return simulator;
    }

    public static void start() {
        Loader loader = new Loader();
        loader.loadMap();
        map = loader.getMap();

        simulator = new Simulator();

        mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }

    public static int getStage() {
        return stage;
    }

    public static void setStage(int s) {
        stage = s;
    }

    public static Map getMap() {
        return map;
    }
}
