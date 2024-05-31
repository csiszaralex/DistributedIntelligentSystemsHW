package Simulation;

public class TimeStepper implements Runnable{

    private boolean stopped;
    private int speed;
    private final Simulator simulator;

    public TimeStepper(Simulator simulator){
        stopped = false;
        speed = 1000;

        this.simulator = simulator;
    }

    public void run() {

        while(!stopped)
        {
            simulator.step();

            try {
                Thread.sleep(speed);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        stopped = true;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
