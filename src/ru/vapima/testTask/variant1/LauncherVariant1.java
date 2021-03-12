package ru.vapima.testTask.variant1;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static ru.vapima.testTask.Main.THREADS;
import static ru.vapima.testTask.Validation.valid;

public class LauncherVariant1 {


    public static void main() throws InterruptedException {
        ArrayBlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<>(10);
        ExecutorService executorService = Executors.newFixedThreadPool(THREADS + 1);
        executorService.execute(new CsvReader(
                "Producer", arrayBlockingQueue));
        int countThreads = THREADS;
        while (countThreads > 0) {
            executorService.execute(new CsvWriter("n" + countThreads, arrayBlockingQueue));
            countThreads--;
        }
        executorService.shutdown();
        if (executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS)) {
            valid();
        }

    }

}
