package House.fields;

import House.users.Dad;
import House.users.MopRobot;
import House.users.Stepable;
import House.users.VacuumRobot;
import SmartHomeFramework.Framework;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Floor extends Field {

    private cleaningState state;
    private String imageName = "floor.jpg";
    boolean tricot = false;

    public Floor(Coordinate coordinate) {
        super(coordinate);
        this.isFlat = true;
        setImage();
        state = cleaningState.CLEAN;
    }

    @Override
    public void addVisitor(Stepable visitor) {
        super.addVisitor(visitor);
        if (visitor instanceof Dad) {
            state = cleaningState.DIRTY;
        } else if (visitor instanceof VacuumRobot && state == cleaningState.DIRTY) {
            state = cleaningState.VACUUMED;
        } else if (visitor instanceof MopRobot && state == cleaningState.VACUUMED) {
            state = cleaningState.CLEAN;
        }
        setState(state);
    }

    public Floor(Coordinate coordinate, boolean tricot) {
        super(coordinate);
        this.isFlat = true;
        setImage();
        state = cleaningState.CLEAN;
        if (tricot) {
            setTricot();
        }
    }

    public void setImage() {
        try {
            this.img = ImageIO.read(new File(baseDir + imageName));
        } catch (IOException ex) {
            System.err.println("Image not found: " + baseDir + imageName);
        }
    }

    @Override
    public boolean stepOn() {
        return true;
    }

    public void setState(cleaningState state) {
        if (imageName != "tricot.jpg") {
            this.state = state;
            switch (state) {
                case CLEAN:
                    imageName = "floor.jpg";
                    break;
                case DIRTY:
                    Framework.getMap().getVaccumField().getFirstVisitor().addGoal(coordinate);
                    imageName = "floor_dirty.jpg";
                    break;
                case VACUUMED:
                    Framework.getMap().getMopField().getFirstVisitor().addGoal(coordinate);
                    imageName = "floor_vacuumed.jpg";
                    break;
            }
            setImage();
        }
    }

    public boolean setTricot() {
        imageName = "tricot.jpg";
        tricot = true;
        setImage();
        return true;
    }

    public void removeTricot() {
        imageName = "floor.jpg";
        tricot = false;
        setImage();
    }

    public boolean hasTricot() {
        return tricot;
    }

    public boolean stepOn(Stepable character) {
        visitors.add(character);
        return true;
    }
}

