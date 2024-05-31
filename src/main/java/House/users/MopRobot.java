package House.users;

import House.fields.Coordinate;
import House.fields.Field;
import House.fields.Map;
import House.fields.cleaningState;
import SmartHomeFramework.Framework;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MopRobot extends Robot {

    private final String imageName = "mopbot.jpg";

    public MopRobot(Field location, Map map, java.util.Set<Coordinate> goals, Coordinate currentGoal) {
        super(location, map);
        priority = 0;
        this.goals = goals;
        this.currentGoal = currentGoal;
    }

    public void clean() {
        if (currentLocation.getCleaningState() == cleaningState.VACUUMED) {
            currentLocation.setState(cleaningState.CLEAN);
        }
    }

    public boolean isHome() {
        if (currentLocation.getCoordinate().x() == 1 && currentLocation.getCoordinate().y() == 1) {
            goingHome = false;
            return true;
        }
        return false;
    }

    @Override
    public Direction step() {
        Direction d = super.step();
        if (goingHome) {
            if (!(currentLocation.getCoordinate().x() - 1 < 0) &&
                    Framework.getMap().getField(currentLocation.getCoordinate().x() - 1, currentLocation.getCoordinate().y()).stepOn()) {
                d = Direction.LEFT;
            } else if (!(currentLocation.getCoordinate().y() - 1 < 0) &&
                    Framework.getMap().getField(currentLocation.getCoordinate().x() + 1, currentLocation.getCoordinate().y()).stepOn()){
                d = Direction.UP;
            }
        }
        if(goingHome && currentLocation.getCoordinate().x() == 1 && currentLocation.getCoordinate().y() == 2) {
            Framework.getMap().setOutIndex(2, true);
            Framework.getMap().getMopField().removeVisitor(
                    Framework.getMap().getMopField().getFirstVisitor());
            Framework.getPaintPanel().repaint();
            Framework.getMap().getField(1, 1).addVisitor(new MopRobot(Framework.getMap().getField(1, 1), Framework.getMap(), null, null));
        }

        if(!Framework.getMap().getOutIndex(2)) {
            Framework.getMap().setCharacterPos(d, Characters.MOP);
        } else {
            for (int i = 0; i < 3; i++) {
                Framework.getMap().setOutIndex(i, false);
            }
            d = null;
        }

        return d;
    }

    public void fillList() {
        for (int i = 0; i < Framework.getMap().getWidth(); i++) {
            for (int j = 0; j < Framework.getMap().getHeight(); j++) {
                if (Framework.getMap().getField(i, j).getCleaningState() == cleaningState.VACUUMED) {
                    goals.add(new Coordinate(i, j));
                }
            }
        }
    }

    @Override
    public void draw(Graphics2D g, int fieldSize) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(currentLocation.getBaseDir() + imageName));
        } catch (IOException ex) {
            System.err.println("MopRobot image not found: " + currentLocation.getBaseDir() + imageName);
        }
        g.drawImage(img, currentLocation.getCoordinate().x() * fieldSize + 6, currentLocation.getCoordinate().y()
                * fieldSize + 2, fieldSize - 6, fieldSize - 12, null);
    }
}
