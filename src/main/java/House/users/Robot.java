package House.users;

import House.fields.Coordinate;
import House.fields.Field;
import House.fields.cleaningState;
import House.fields.Map;
import SmartHomeFramework.Framework;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public abstract class Robot extends Stepable {
    boolean goingHome;

    public Robot(Field location, Map map) {
        super(location, map);
        goals = new HashSet<Coordinate>();
        goingHome = false;
    }

    public void testSendHome() {
        goingHome = true;
        if (currentGoal == null && goals.size() == 0) {
            goingHome = true;
        }
    }

    public Coordinate getCurrentGoal() {
        if (goals.isEmpty()) {
            goingHome = true;
            return new Coordinate(1, 1);
        }
        if (currentGoal == null) {
            int index = -1;
            double value = Double.MAX_VALUE;
            for (int i = 0; i < goals.size(); i++) {
                double newVal = Math.pow((new ArrayList<Coordinate>(goals)).get(i).x()-currentLocation.getCoordinate().x(), 2) + Math.pow((new ArrayList<Coordinate>(goals)).get(i).y() - currentLocation.getCoordinate().y(), 2);
                if (value > newVal) {
                    value = newVal;
                    index = i;
                }
            }
            currentGoal = (new ArrayList<Coordinate>(goals)).get(index);
            List<Coordinate> list = new ArrayList<Coordinate>(goals);
            list.remove(index);
            goals = new HashSet<Coordinate>(list);

        }
        return currentGoal;
    }

    public Direction step() {
        currentGoal = getCurrentGoal();

        int x = currentLocation.getCoordinate().x();
        int y = currentLocation.getCoordinate().y();

        if (x == currentGoal.x() && y == currentGoal.y()) {
            currentGoal = null;
            clean();
            currentGoal = getCurrentGoal();
        }

        if(currentGoal == null) {
            return null;
        } else if (!(y - 1 < 0) && y > currentGoal.y() && Framework.getMap().getField( x, y - 1).stepOn()) {
            return Direction.UP;
        } else if (!(y + 1 > 10) && y < currentGoal.y() && Framework.getMap().getField( x, y + 1).stepOn()) {
            return Direction.DOWN;
        } else if (!(x - 1 < 0) && x > currentGoal.x() && Framework.getMap().getField( x - 1, y).stepOn()) {
            return Direction.LEFT;
        } else if (!(x + 1 > 14) && x < currentGoal.x() && Framework.getMap().getField(x + 1, y).stepOn()) {
            return Direction.RIGHT;
        }
        return null;
    }

    @Override
    public cleaningState getCleaningState() {
        return this.currentLocation.getCleaningState();
    }

    public abstract void clean();
}
