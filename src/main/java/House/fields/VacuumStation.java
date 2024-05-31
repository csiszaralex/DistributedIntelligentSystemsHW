package House.fields;

import House.users.Stepable;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class VacuumStation extends Field{

    private final String imageName = "vacuumhouse.png";

    public VacuumStation(Coordinate coordinate) {
        super(coordinate);
        this.isFlat = true;

        try {
            this.img = ImageIO.read(new File(baseDir + imageName));
        } catch (IOException ex) {
            System.err.println("VacuumStation image not found: " + baseDir + imageName);
        }
    }

    public void setState(cleaningState state) {}

    public void removeTricot() {}

    public boolean setTricot() {
        return false;
    }

    public boolean stepOn(Stepable character) {
        addVisitor(character);
        return true;
    }

    public boolean hasTricot() {
        return false;
    }
}
