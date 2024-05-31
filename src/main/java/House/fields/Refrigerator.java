package House.fields;

import House.users.Stepable;
import SmartHomeFramework.Framework;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Refrigerator extends Field{

    private final String imageName = "refrigerator.jpg";

    private int beerCount;

    public void addBeer(int amount) {
        beerCount += amount;
        if (beerCount <  6) {
            refill();
        }
        resetBeerCount();
    }

    public void refill(){
        addBeer(6);
        resetBeerCount();
    }

    private void resetBeerCount() {
        Framework.getBeers().setText(String.valueOf(beerCount));
    }

    public Refrigerator(Coordinate coordinate) {
        super(coordinate);
        this.isFlat = true;
        beerCount = 12;

        try {
            this.img = ImageIO.read(new File(baseDir + imageName));
        } catch (IOException ex) {
            System.err.println("Refrigerator image not found: " + baseDir + imageName);
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
