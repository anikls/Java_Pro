package main.java.ru.example.example3_;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author NAgafonov
 */
public class CustomTask implements Runnable {

    private int number;

    public CustomTask(int number) {
        this.number = number;
    }

    @Override
    public void run() {
        System.out.println("Start Task #"+ number);
        delay(1, 10);
        System.out.println("Stop Task #"+ number);
    }

    private void delay(int min, int max) {
        try {
            TimeUnit.SECONDS.sleep(new Random().nextInt((max - min + 1) + min));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
