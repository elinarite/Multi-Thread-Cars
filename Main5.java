package thread.work6;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

//1 уровень сложности: Все участники должны стартовать одновременно,
// несмотря на то что на подготовку у каждого их них уходит разное время.
// В туннель не может заехать одновременно больше половины участников.
// Попробуйте всё это синхронизировать. Только после того как все завершат
// гонку нужно выдать объявление об окончании. Можете корректировать классы.
//Пример вывода:
//…
//Участник #3 закончил этап: Дорога 40 метров
//Участник #3 - WIN
//Участник #1 закончил этап: Дорога 40 метров
//Участник #4 закончил этап: Тоннель 80 метров
//Участник #4 начал этап: Дорога 40 метров
//Участник #2 закончил этап: Тоннель 80 метров
//Участник #2 начал этап: Дорога 40 метров
//Участник #2 закончил этап: Дорога 40 метров
//Участник #4 закончил этап: Дорога 40 метров
//ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!
public class Main5 {

    public static final int CARS_COUNT = 4;

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        CountDownLatch startLatch = new CountDownLatch(CARS_COUNT);
        CountDownLatch finishLatch = new CountDownLatch(1);

        Semaphore semaphore = new Semaphore(CARS_COUNT / 2);
        Race race = new Race(new Road(60), new Tunnel(semaphore), new Road(40 ));
        Car[] cars = new Car[CARS_COUNT];

        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10),  startLatch, finishLatch);
        }

        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }
        try {
            startLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            finishLatch.await();
            System.out.println(Car.WINNER + " WIN");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}