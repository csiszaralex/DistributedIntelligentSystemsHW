package SmartHomeFramework;

import House.fields.*;
import House.users.Dad;
import House.users.MopRobot;
import House.users.VacuumRobot;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

public class Loader {

    private final Map map = new Map();
    private final String configPath = "src\\main\\java\\House\\tiles\\tiles.txt";

    public void loadMap()  {
        try {
            File configFile = new File(configPath);
            Scanner scanner = new Scanner(configFile);

            int width = Integer.parseInt(scanner.nextLine());
            int height = Integer.parseInt(scanner.nextLine());

            map.setHeight(height);
            map.setWidth(width);

            Field[][] fields = new Field[width][height];

            int row = 0;
            while(scanner.hasNextLine()) {
                String[] splitLine = scanner.nextLine().split(" ");

                for(int col=0; col < splitLine.length; col++) {
                    switch (splitLine[col]) {
                        case "+":
                            fields[col][row] = new Floor(new Coordinate(col, row));
                            break;
                        case "x":
                            fields[col][row] = new Wall(new Coordinate(col, row));
                            break;
                        case "r":
                            fields[col][row] = new Refrigerator(new Coordinate(col, row));
                            break;
                        case "v":
                            fields[col][row] = new VacuumStation(new Coordinate(col, row));
                            fields[col][row].addVisitor(new VacuumRobot(fields[col][row], map, new HashSet<Coordinate>(), null));
                            break;
                        case "m":
                            fields[col][row] = new MopStation(new Coordinate(col, row));
                            fields[col][row].addVisitor(new MopRobot(fields[col][row], map, new HashSet<Coordinate>(), null));
                            break;
                        case "t":
                            fields[col][row] = new Floor(new Coordinate(col, row), true);
                            break;
                        default:
                            Field station = new Door(new Coordinate(col, row));
                            fields[col][row] = station;
                            fields[col][row].addVisitor(new Dad(fields[col][row], map, 0));
                            //tiles.addStation(station);
                            break;
                    }
                }
                row++;
            }

            map.setFields(fields);
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Couldn't read the tiles config file.");
        }
    }

    public Map getMap(){
        return this.map;
    }
}
