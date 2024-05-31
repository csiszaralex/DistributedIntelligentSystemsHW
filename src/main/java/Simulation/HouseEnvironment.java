package Simulation;

import House.users.Characters;
import House.users.Direction;
import SmartHomeFramework.Framework;
import jason.asSyntax.Literal;
import jason.asSyntax.Structure;
import jason.environment.Environment;

import java.util.ArrayList;
import java.util.HashMap;


public class HouseEnvironment extends Environment{

    private boolean startSession = false;
    private int stage = 0;

    private final HashMap<String, Integer> agentIds = new HashMap<>();
    private final String controllerName = "controller";


    @Override
    public void init(String[] args){
        super.init(args);
        Framework.setEnvironment(this);
        Framework.start();
    }

    @Override
    public boolean executeAction(String agName, Structure action){
        String actionName = action.getFunctor();
        stage = 0;
        switch (actionName) {
            case "step":
                if (Framework.getStage() == 0) {
                    Direction d = Framework.getMap().getDad().getFirstVisitor().step();
                    if (d == null) {
                        Framework.setStage(1);
                    }
                } else if (Framework.getStage() == 1) {
                    Direction d = Framework.getMap().getVaccumField().getFirstVisitor().step();
                    if (d == null) {
                        Framework.setStage(2);
                    }
                } else {
                    Direction d = null;
                    try{
                        d = Framework.getMap().getMopField().getFirstVisitor().step();
                    } catch (Exception e){
                        d = null;
                    }
                    if (d == null) {
                        Framework.setStage(0);
                    }
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return true;
            case "-beer(false)":
                Framework.getMap().getRefrigerator().getFreezer().addBeer(-2);
                addPercept(Literal.parseLiteral("notify_freezer"));
                return true;
            case "-tanktop(false)":
                Framework.getMap().getDad().setTricot();
                return true;
            case "start_vacuum":
                Framework.getMap().getVaccumField().getFirstVisitor().step(); //the first phase of step is the scouting
                return true;
            case "-home(dad, true)":
                Framework.getMap().getDad().getFirstVisitor().step();   //the last phase of step is the removing
                return true;
            case "-at(dad,home)":
                addPercept(Literal.parseLiteral("start_vacuum"));
                return true;
            case "fill_dirty_spots":
                Framework.getMap().getVaccumField().getFirstVisitor().fillList();
                return true;
            case "suck":
                Framework.getMap().getVaccumField().getFirstVisitor().clean();
                return true;
            case "notify_mop":
                Framework.getMap().getMopField().getFirstVisitor().step();
                return true;
            case "fill_vacuumed_spots":
                Framework.getMap().getMopField().getFirstVisitor().fillList();
                return true;
            case "clean":
                Framework.getMap().getMopField().getFirstVisitor().clean();
                return true;
            case "notify_freezer":
                Framework.getMap().getRefrigerator().getFreezer().addBeer(-1);
                return true;
            case "refill":
                Framework.getMap().getRefrigerator().getFreezer().refill();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void stop() {
        super.stop();

        Framework.getSimulator().stop();
    }

}
