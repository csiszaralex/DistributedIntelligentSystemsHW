package SmartHomeFramework;

import House.fields.Coordinate;
import House.fields.Field;
import House.fields.Map;
import House.fields.cleaningState;

import javax.swing.*;
import java.awt.*;

public class PaintPanel extends JPanel {
    private int fieldSize = 0;
    private Coordinate tricotLocation = new Coordinate(7, 1);

    public PaintPanel(){
        super();
        setDoubleBuffered(true);
        setBackground(new Color(2, 35, 145));
        Framework.setPaintPanel(this);
    }

    public Coordinate getTricotLocation() {
        return tricotLocation;
    }

    @Override
    public void paintComponent(Graphics g){
        Map map = Framework.getMap();
        if (map == null) {
            return;
        }
        fieldSize = 60;

        Graphics2D g2 = (Graphics2D) g;

        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                map.getField(x, y).draw(g2, fieldSize);
            }
        }
    }

    public void mouseClicked(int x, int y, int objectId) { //
        //Framework.log("Click at "+x+"x"+y);
        if (fieldSize == 0) return;

        // Determine cell
        int coordinateX = x/ fieldSize;
        int coordinateY = y/ fieldSize;

        Field f = Framework.getMap().getField(coordinateX, coordinateY);
        switch (objectId) {
            case 0:
                if (f.setTricot() && tricotLocation != null && f.getCoordinate().x() != tricotLocation.x() && f.getCoordinate().y() != tricotLocation.y()) {
                    Framework.getMap().getField(tricotLocation.x(), tricotLocation.y()).removeTricot();
                }
                tricotLocation = f.getCoordinate();
                break;
            case 1:
                f.setState(cleaningState.DIRTY);
                break;
            case 2:
                f.setState(cleaningState.VACUUMED);
                break;
            case 3:
                f.setState(cleaningState.CLEAN);
                break;
        }
    }
}
