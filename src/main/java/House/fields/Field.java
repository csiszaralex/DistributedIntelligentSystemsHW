package House.fields;

import House.users.*;
import SmartHomeFramework.Framework;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public abstract class Field {

    protected final List<Stepable> visitors = new ArrayList<>();

    protected boolean isFlat;
    protected final Coordinate coordinate;
    protected final HashMap<Direction, Field> neighbours = new HashMap<>();

    protected final String baseDir = "src/main/java/House/images/";
    protected BufferedImage img = null;

    public Field(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public void setNeighbour(Direction dir, Field field){
        neighbours.put(dir, field);
    }

    public boolean stepOn() { return false; }

    public void removeVisitor(Stepable visitor){
        visitors.remove(visitor);
    }

    public void addVisitor(Stepable visitor){
        if (visitors.size() == 1) {
            removeVisitor(visitors.getFirst());
        }
        this.visitors.add(visitor);
    }

    public Stepable getFirstVisitor() {
        if(visitors.size() == 0)
            return new MopRobot(Framework.getMap().getField(1, 2), Framework.getMap(), new HashSet<Coordinate>(), new Coordinate(1, 1));
        return visitors.getFirst();
    }

    public Coordinate getCoordinate(){
        return coordinate;
    }

    public Refrigerator getFreezer() {return null;}

    public void draw(Graphics2D g2, int fieldSize) {
        g2.drawImage(this.img, this.coordinate.x() * fieldSize + 1, this.coordinate.y() * fieldSize + 1, fieldSize - 1, fieldSize - 1, null);

        for (Drawable drawable : this.visitors) {
            drawable.draw(g2, fieldSize);
        }
    }

    public void clearVisitors() {
        visitors.clear();
    }

    public abstract void setState(cleaningState state);

    public abstract boolean setTricot();

    public abstract boolean hasTricot();

    public abstract void removeTricot();

    public String getBaseDir() {
        return baseDir;
    }

    public cleaningState getCleaningState() { return null; }

    public void step() {}
}
