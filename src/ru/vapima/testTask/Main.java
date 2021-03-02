package ru.vapima.testTask;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static ru.vapima.testTask.Validation.valid;

public class Main {

    public static final String STOP_SIGNAL = "STOP MACHINE";
    public static final String PATH = "resources/";
    public static final String CSV_ORIGINAL = "exchange_rates_2001-2021.csv";
    public static final Integer THREADS = 3; //Minimum 1 for least one Consumer.

    public static void main(String[] args) throws InterruptedException {
        ArrayBlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<>(10);
        ExecutorService executorService = Executors.newFixedThreadPool(THREADS + 1);
        executorService.execute(new CsvReader(
                "Producer", arrayBlockingQueue));
        int countThreads = THREADS;
        System.out.println(arrayBlockingQueue.size());
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
