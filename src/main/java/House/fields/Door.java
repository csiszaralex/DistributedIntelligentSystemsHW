package House.fields;

import House.users.Stepable;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Door extends Field{

    public Door(Coordinate coordinate) {
        super(coordinate);
        this.isFlat = false;

        String imageName = "door.jpg";
        try {
            this.img = ImageIO.read(new File(baseDir + imageName));
        } catch (IOException ex) {
            System.err.println("Door image not found: " + baseDir + imageName);
        }
    }

    public boolean setTricot() {
        return false;
    }

    public void setState(cleaningState state) {}

    public void removeTricot() {}

    public boolean stepOn(Stepable character) {
        addVisitor(character);
        return true;
    }

    public boolean hasTricot() {
        return false;
    }
}
