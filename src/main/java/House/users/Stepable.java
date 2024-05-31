package House.users;

import House.fields.Coordinate;
import House.fields.Field;
import House.fields.Map;
import House.fields.cleaningState;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Stepable implements Drawable {
    protected Field currentLocation;
    protected Map map;

    protected Set<Coordinate> goals;
    protected Coordinate currentGoal;
    protected int priority;

    public Stepable(Field location, Map map){
        this.currentLocation = location;
    }

    public void clean() { }

    public void addGoal(Coordinate newGoal) {
        goals.add(newGoal);
    }

    public Set<Coordinate> getGoals() {
        return goals;
    }

    public Coordinate getCurrentGoal() {
        return currentGoal;
    }

    public Field getCurrentLocation() {
        return this.currentLocation;
    }

    public abstract Direction step();

    public cleaningState getCleaningState() { return null; }

    public void fillList() {}
}
