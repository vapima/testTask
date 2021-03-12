package ru.vapima.testTask.variant2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.vapima.testTask.Main.THREADS;
import static ru.vapima.testTask.Validation.valid;

public class Launcher {
    private static volatile Integer integer = 1;

    public static void main() throws InterruptedException {
        CounterLines counterLines = new CounterLines();
        AtomicInteger atomicInteger = new AtomicInteger(100);
        ExecutorService executorService = Executors.newFixedThreadPool(THREADS);
        int countThreads = THREADS;
        while (countThreads > 0) {
            executorService.execute(new LinesGrabber("n" + countThreads, counterLines));
            countThreads--;
        }
        executorService.shutdown();
        if (executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS)) {
            valid();
        }

    }

}
