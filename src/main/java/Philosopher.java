import java.util.Random;

import static java.lang.Thread.sleep;

public class Philosopher implements Runnable {

    String name;
    private Fork right, left;
    Random random = new Random();

    public Philosopher(String name, Fork right, Fork left){
        this.name = name;
        this.right = right;
        this.left = left;
    }

    public void run() {
        do {
            try {
                System.out.println(name + " philosphiert.");    // Philosopher is thinking
                sleep((int) (random.nextDouble()*10000));
                System.out.println(name + " hat Hunger.");      // Philosopher is hungry
                PhilosophersDesk.forks.acquire();               // Makes a claim for two forks.
                right.get();                                    // taking right
                sleep((int) (random.nextDouble()*1000));          // turn left (critical moment)
                left.get();                                     // taking left
                System.out.println(name + " hat zwei Gabeln. Er kann essen.");
                sleep((int) (random.nextDouble()*1000));         // holding two forks -> can eat now
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            PhilosophersDesk.forks.release();                   // eating finished -> share the fork
            right.put();
            left.put();
        } while (true);
    }
}