package thread.work6;


import java.util.concurrent.CountDownLatch;

public class Car implements Runnable {
    private static int CARS_COUNT = 0;
    public static String WINNER;
    private String name;
    private int speed;
    private Race race;

    CountDownLatch startLatch;
    CountDownLatch finishLatch;


    public Car(Race race, int speed, CountDownLatch startLatch, CountDownLatch finishLatch) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник " + CARS_COUNT;
        this.startLatch = startLatch;
        this.finishLatch = finishLatch;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep((int) (1500 * Math.random() + 500));
            System.out.println(this.name + " готов");
            Thread.sleep((int) (1500 * Math.random() + 500));
            startLatch.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
        finishLatch.countDown();
        WINNER = this.name;
    }

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

}