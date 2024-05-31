package Simulation;

import House.fields.Map;
import House.users.Direction;
import House.users.Injured;
import House.users.Stepable;
import SmartHomeFramework.Framework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Simulator {
    private final HashMap<Integer, Injured> injureds = new HashMap<>();
    private final ArrayList<Injured> toRemove = new ArrayList<>();
    private final HashMap<Integer, Stepable> rescuers = new HashMap<>();
    private int time=0;
    private final int helicopterSpeed = 3;
    private TimeStepper timeStepper = null;
    private int savedPeople = 0;
    private int deadPeople = 0;
    private static boolean started = false;


    public static void setStarted(boolean a) {
        started = a;
    }

    public void step(){
        time++;
        boolean bothCanStep = time % helicopterSpeed == 0;
        boolean deathHappened = false;

        for (Injured injured : injureds.values()){
            boolean isDead = injured.step();
            if(isDead){
                injuredTragedy(injured);
                toRemove.add(injured);
                deathHappened = true;
            }
        }

        if (deathHappened){
            for (Injured injured : toRemove){
                injureds.remove(injured.getId());
            }
            toRemove.clear();
            optimization();
        }

        Framework.refresh();
    }

    public void allocateInjured(int rescuerId, int injuredId){
        List<Direction> path = Map.getPath(rescuers.get(rescuerId).getCurrentLocation(), injureds.get(injuredId).getLocation());
    }

    private void injuredTragedy(Injured injured){
        this.deadPeople++;
    }

    private void optimization(){
        HashMap<Integer, HashMap<Integer, Integer>> bids = new HashMap<>();
        for (Stepable stepable : rescuers.values()){
            HashMap<Integer, Integer> distances = new HashMap<>();
        }
    }

    public void stop() {
        if (timeStepper != null)
            timeStepper.stop();
    }
}
