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

public class VacuumRobot extends Robot {
    private final String imageName = "vacuumbot.jpg";

    public VacuumRobot(Field location, Map map, java.util.Set<Coordinate> goals, Coordinate currentGoal) {
        super(location, map);
        priority = 1;
        this.goals = goals;
        this.currentGoal = currentGoal;
    }

    public void clean() {
        if (currentLocation.getCleaningState() == cleaningState.DIRTY) {
            currentLocation.setState(cleaningState.VACUUMED);
        }
    }

    public void fillList() {
        for (int i = 0; i < Framework.getMap().getWidth(); i++) {
            for (int j = 0; j < Framework.getMap().getHeight(); j++) {
                if (Framework.getMap().getField(i, j).getCleaningState() == cleaningState.DIRTY) {
                    goals.add(new Coordinate(i, j));
                }
            }
        }
    }

    @Override
    public Direction step() {
        Direction d = super.step();
        if (goingHome) {
            if (!(currentLocation.getCoordinate().x() + 1 > 14) &&
                    Framework.getMap().getField(currentLocation.getCoordinate().x() + 1, currentLocation.getCoordinate().y()).stepOn()) {
                d = Direction.RIGHT;
            } else if (!(currentLocation.getCoordinate().y() - 1 < 0) &&
                    Framework.getMap().getField(currentLocation.getCoordinate().x() + 1, currentLocation.getCoordinate().y()).stepOn()) {
                d = Direction.UP;
            }
        }

        if(goingHome && currentLocation.getCoordinate().x() == 13 && currentLocation.getCoordinate().y() == 2) {
            Framework.getMap().setOutIndex(1, true);
            Framework.getMap().getVaccumField().removeVisitor(
                    Framework.getMap().getVaccumField().getFirstVisitor());
            Framework.getMap().getField(13, 1).addVisitor(new VacuumRobot(Framework.getMap().getField(13, 1), Framework.getMap(), null, null));
        }

        if(!Framework.getMap().getOutIndex(1)) {
            Framework.getMap().setCharacterPos(d, Characters.VACUUM);
        } else {
            d = null;
        }

        return d;
    }

    @Override
    public void draw(Graphics2D g, int fieldSize) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(currentLocation.getBaseDir() + imageName));
        } catch (IOException ex) {
            System.err.println("VacuumRobot image not found: " + currentLocation.getBaseDir() + imageName);
        }
        g.drawImage(img, currentLocation.getCoordinate().x() * fieldSize + 6, currentLocation.getCoordinate().y()
                * fieldSize + 2, fieldSize - 6, fieldSize - 12, null);
    }
}
