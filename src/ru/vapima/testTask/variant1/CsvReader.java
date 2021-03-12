package ru.vapima.testTask.variant1;

import java.io.*;
import java.util.concurrent.BlockingQueue;

import static ru.vapima.testTask.Main.*;

/**
 * @author Vasily Pima
 * Producer
 */
public class CsvReader implements Runnable {
    public CsvReader(String name, BlockingQueue<String> lines) {
        this.name = name;
        this.lines = lines;
    }

    private final String name;
    private final BlockingQueue<String> lines;

    @Override
    public void run() {
        System.out.println(name + ": START");
        try (BufferedReader fp = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(PATH + CSV_ORIGINAL), "Cp1251"))) {
            String line;
            boolean isFirst = true;  //For skip first line
            while ((line = fp.readLine()) != null) {
                if (isFirst) {
                    isFirst = false;
                } else {
                    lines.put(line);
                }
            }
            System.out.println(name + " is STOPPED.");
        } catch (FileNotFoundException e) {
            System.err.println("Fail not found.");
            e.printStackTrace();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                lines.put(STOP_SIGNAL); //Кладем стоп сигнал в очередь, информирую потребителей об оканчании поставок.
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
