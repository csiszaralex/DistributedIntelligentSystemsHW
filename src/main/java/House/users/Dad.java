package House.users;

import House.fields.*;
import SmartHomeFramework.Framework;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Dad extends Stepable {
    private final String imageName = "dad.jpg";
    private int stage;

    public Dad(Field location, Map map, int stage) {
        super(location, map);
        priority = 2;
        this.stage = stage;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public Direction step() {
        Direction d = null;

        int x = currentLocation.getCoordinate().x();
        int y = currentLocation.getCoordinate().y();

        //dad stands next to the freezer coords
        if (x == 1 && y == 8 || x == 2 && y == 9) {
            ((Refrigerator)(Framework.getMap().getField(1, 9))).addBeer(-1);
        }

        if (stage == 0) {
            var goal = dadLooksAround();
            Field currentField = Framework.getMap().getField(x, y);

            if(currentField.hasTricot()) {
                stage = 1;
                currentField.removeTricot();
                currentField.setState(cleaningState.DIRTY);
                return step();
            }

            if (goal == null) {
                return Direction.LEFT;
            }
            if (!(y - 1 < 0) && y > goal.y() && Framework.getMap().getField( x, y - 1).stepOn()) {
                d = Direction.UP;
            } else if (!(y + 1 > 10) && y < goal.y() && Framework.getMap().getField( x, y + 1).stepOn()) {
                d = Direction.DOWN;
            } else if (!(x - 1 < 0) && x > goal.x() && Framework.getMap().getField( x - 1, y).stepOn()) {
                d = Direction.LEFT;
            } else if (!(x + 1 > 14) && x < goal.x() && Framework.getMap().getField(x + 1, y).stepOn()) {
                d = Direction.RIGHT;
            } else {
                d = Direction.LEFT;
            }


        } else if (stage == 1) {
            if (Framework.getMap().getField(currentLocation.getCoordinate().x(), currentLocation.getCoordinate().y() + 1).stepOn()) {
                d = Direction.DOWN;
            } else if (Framework.getMap().getField(currentLocation.getCoordinate().x() - 1, currentLocation.getCoordinate().y()).stepOn()) {
                d = Direction.LEFT;
            } else {
                stage = 2;
                return step();
            }
        } else {
            if (Framework.getMap().getField(currentLocation.getCoordinate().x() + 1, currentLocation.getCoordinate().y()).stepOn()) {
                d = Direction.RIGHT;
            } else if (Framework.getMap().getField(currentLocation.getCoordinate().x(), currentLocation.getCoordinate().y() + 1).stepOn()) {
                d = Direction.DOWN;
            } else {
                d = Direction.LEFT;
                //remove dad from the map
                Framework.getMap().getField(x, y).removeVisitor(
                        Framework.getMap().getField(x, y).getFirstVisitor());
                Framework.getMap().getField(13, 10).addVisitor(new Dad(Framework.getMap().getField(13, 10), Framework.getMap(), 0));
                Framework.getMap().setOutIndex(0, true);
            }
        }
        if (!Framework.getMap().getOutIndex(0)) {
            Framework.getMap().setCharacterPos(d, Characters.DAD);
        } else {
            d = null;
        }
        return d;
    }

    public Coordinate dadLooksAround() {
        return Framework.getPaintPanel().getTricotLocation();
    }

    @Override
    public void draw(Graphics2D g, int fieldSize) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(currentLocation.getBaseDir() + imageName));
        } catch (IOException ex) {
            System.err.println("Dad image not found: " + currentLocation.getBaseDir() + imageName);
        }
        g.drawImage(img, currentLocation.getCoordinate().x() * fieldSize + 6, currentLocation.getCoordinate().y()
                * fieldSize + 2, fieldSize - 6, fieldSize - 12, null);
    }
}
