package ru.vapima.testTask;

import ru.vapima.testTask.variant1.LauncherVariant1;
import ru.vapima.testTask.variant2.LauncherVariant2;

public class Main {

    public static final String STOP_SIGNAL = "STOP MACHINE";
    public static final String PATH = "resources/";
    public static final String CSV_ORIGINAL = "exchange_rates_2001-2021.csv";
    public static final Integer THREADS = 3;

    public static void main(String[] args) throws InterruptedException {
        LauncherVariant1.main();
        System.out.println("---------------------------------");
        LauncherVariant2.main();

    }

}
