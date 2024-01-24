package odas.sterencd.odasprojekt.utils;

import java.util.Random;

public class Delay {
    public static void delay() {
        try {
            Random random = new Random();
            double randomTime = 0.5 + 0.5 * random.nextDouble();
            long millisecondsToSleep = (long) (randomTime * 1000);
            Thread.sleep(millisecondsToSleep);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
