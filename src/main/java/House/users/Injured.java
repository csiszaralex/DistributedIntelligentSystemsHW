package House.users;

import House.fields.Field;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Injured implements Drawable {

    private final int id;
    private final float maxHealth = 20F;
    private int health = 20;
    private Field location;
    private final String imageName = "injured2.png";
    private BufferedImage img = null;

    public Injured(int id) {
        this.id = id;
    }

    public boolean step() {
        health--;
        if (health <= 0) {
            health = 0;
            //location.removeVisitor(this);
        }
        return health == 0;
    }

    public int getId() {
        return id;
    }

    public Field getLocation() {
        return location;
    }

    public float getHealthRatio() {
        return (health / maxHealth);
    }

    @Override
    public void draw(Graphics2D g, int fieldSize) {

        // Determine position
        int x = location.getCoordinate().x();
        int y = location.getCoordinate().y();

        //Draw the image
        g.drawImage(img, x * fieldSize + 6, y * fieldSize + 2, fieldSize - 6, fieldSize - 12, null);

        // Draw the health bar borders and white fill
        g.setColor(Color.black);
        g.drawRect(x * fieldSize + 3, (y + 1) * fieldSize - 10, fieldSize - 8, 6);
        g.setColor(Color.white);
        g.fillRect(x * fieldSize + 4, (y + 1) * fieldSize - 9, fieldSize - 9, 5);

        // Draw the health value
        if (this.health == 0) {
            g.setColor(Color.BLACK);
            g.fillRect(x * fieldSize + 4, (y + 1) * fieldSize - 9, (fieldSize - 9), 5);
        } else {
            g.setColor(calculateColor(1 - this.getHealthRatio()));
            g.fillRect(x * fieldSize + 4, (y + 1) * fieldSize - 9, (int) ((fieldSize - 10) * this.getHealthRatio()), 5);
        }
    }

    private Color calculateColor(float value) {
        double red = 0, green = 0, blue = 0;

        // First, green stays at 100%, red raises to 100%
        if (value < 0.5) {
            green = 1.0;
            red = 2 * value;
        }

        // Then red stays at 100%, green decays
        if (0.5 <= value) {
            red = 1.0f;
            green = 1.0 - 2 * (value - 0.5);
        }

        return new Color((int) (255 * red), (int) (255 * green), 0);
    }
}
