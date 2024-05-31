package House.fields;

import House.users.*;
import SmartHomeFramework.Framework;

import java.util.ArrayList;
import java.util.List;

public class Map {
    private int heightY;
    private int widthX;
    private Field[][] fields;
    private Coordinate[] importantCharacters = new Coordinate[] {new Coordinate(1, 1), new Coordinate(13, 1), new Coordinate(1, 9), new Coordinate(13, 10)}; //Mopbot, Vacuumbot, Tricot, Dad
    private boolean[] isOut = {false, false, false};

    public void setOutIndex(int index, boolean isOut) {
        this.isOut[index] = isOut;
    }

    public boolean getOutIndex(int index) {
        return isOut[index];
    }

    public void setFields(Field[][] fields){
        this.fields = fields;
        for (Field[] field : fields){
            for (Field f : field){
                if(f.getCoordinate().y()!=0)
                    f.setNeighbour(Direction.UP, fields[f.getCoordinate().x()][f.getCoordinate().y() - 1]);
                if(f.getCoordinate().y()!=(heightY - 1))
                    f.setNeighbour(Direction.DOWN, fields[f.getCoordinate().x()][f.getCoordinate().y() + 1]);
                if(f.getCoordinate().x()!=0)
                    f.setNeighbour(Direction.LEFT, fields[f.getCoordinate().x() - 1][f.getCoordinate().y()]);
                if(f.getCoordinate().x()!=(widthX - 1))
                    f.setNeighbour(Direction.RIGHT, fields[f.getCoordinate().x() + 1][f.getCoordinate().y()]);
            }
        }
    }

    public Field getVaccumField() {
        return fields[importantCharacters[1].x()][importantCharacters[1].y()];
    }

    public Field getMopField() {
        return fields[importantCharacters[0].x()][importantCharacters[0].y()];
    }

    public Field getRefrigerator() {
        return fields[importantCharacters[2].x()][importantCharacters[2].y()];
    }

    public Field getDad() {
        return fields[importantCharacters[3].x()][importantCharacters[3].y()];
    }

    public void setCharacterPos(Direction d, Characters c) {

        int charIndex = -1;
        if (c == Characters.MOP) {
            charIndex = 0;
        } else if (c == Characters.VACUUM) {
            charIndex = 1;
        } else {
            charIndex = 3;
        }
        int cx = importantCharacters[charIndex].x();
        int cy = importantCharacters[charIndex].y();
        Stepable s = fields[cx][cy].getFirstVisitor();
        int cxprev = cx;
        int cyprev = cy;
        if (d == Direction.UP) {
            cy -= 1;
        } else if (d == Direction.DOWN) {
            cy += 1;
        } else if (d == Direction.LEFT) {
            cx -= 1;
        } else {
            cx += 1;
        }
        if (fields[cx][cy].stepOn()){
            fields[cxprev][cyprev].removeVisitor(s);
            importantCharacters[charIndex] = new Coordinate(cx, cy);
            if (c == Characters.MOP) {
                MopRobot mr = new MopRobot(fields[cx][cy], this, ((MopRobot) s).getGoals(), ((MopRobot) s).getCurrentGoal());
                fields[cx][cy].addVisitor(mr);
            } else if (c == Characters.VACUUM) {
                VacuumRobot vr = new VacuumRobot(fields[cx][cy], this, ((VacuumRobot) s).getGoals(), ((VacuumRobot) s).getCurrentGoal());
                fields[cx][cy].addVisitor(vr);
            } else {
                Dad d1 = new Dad(fields[cx][cy], this, ((Dad) s).getStage());
                fields[cx][cy].addVisitor(d1);
            }
            Framework.getPaintPanel().repaint();
        }
    }

    public int getWidth() {
        return widthX;
    }

    public int getHeight() {
        return heightY;
    }

    public Field getField(int width, int height){
        return fields[width][height];
    }

    public void setHeight(int height) {
        this.heightY = height;
    }

    public void setWidth(int width) {
        this.widthX = width;
    }

    public static List<Direction> getPath(Field start, Field end){
        ArrayList<Direction> path = new ArrayList<>();
        //UP
        for (int i = start.getCoordinate().y(); i > end.getCoordinate().y(); i--){
            path.add(Direction.UP);
        }
        //DOWN
        for (int i = start.getCoordinate().y(); i < end.getCoordinate().y(); i++){
            path.add(Direction.DOWN);
        }
        //LEFT
        for (int i = start.getCoordinate().x(); i > end.getCoordinate().x(); i--){
            path.add(Direction.LEFT);
        }
        //RIGHT
        for (int i = start.getCoordinate().x(); i < end.getCoordinate().x(); i++){
            path.add(Direction.RIGHT);
        }
        return path;
    }

    public void reset() {
        for (Field[] field : fields){
            for (Field f : field){
                f.clearVisitors();
            }
        }
    }
}
