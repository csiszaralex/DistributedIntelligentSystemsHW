package House.fields;

import House.users.Stepable;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Wall extends Field{

    private final String imageName = "wall.jpg";

    public Wall(Coordinate coordinate) {
        super(coordinate);
        this.isFlat = false;

        try {
            this.img = ImageIO.read(new File(baseDir + imageName));
        } catch (IOException ex) {
            System.err.println("Wall image not found: " + baseDir + imageName);
        }
    }

    public void setState(cleaningState state) {}

    public void removeTricot() {}

    public boolean setTricot() {
        return false;
    }

    public boolean stepOn(Stepable character) {
        return false;
    }

    public boolean hasTricot() {
        return false;
    }
}
