package ru.vapima.testTask;

import java.io.*;
import java.util.concurrent.BlockingQueue;

import static ru.vapima.testTask.Main.PATH;
import static ru.vapima.testTask.Main.STOP_SIGNAL;

/**
 * @author Vasily Pima
 * Consumer
 */
public class CsvWriter implements Runnable {
    private final String name;
    private final BlockingQueue<String> lines;

    public CsvWriter(String name, BlockingQueue<String> lines) {
        this.name = name;
        this.lines = lines;
    }


    @Override
    public void run() {
        try (PrintWriter pw = new PrintWriter
                (new OutputStreamWriter
                        (new FileOutputStream
                                (PATH + name + ".csv"), "Cp1251"))) {
            while (true) {
                String take = lines.take();
                if (take.equals(STOP_SIGNAL)) { //Проверям не записал ли стоп сигнал производитель.
                    System.out.println(name + " is STOPPED.");
                    lines.put(STOP_SIGNAL); //Возвращаем стоп сигнал в очередь для других потоков.
                    return; //Данных у производителя больше нет, сворачиваем лавочку.
                }
                pw.write(take + "\r\n");
                System.out.println(name + ": " + take);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found.");
            e.printStackTrace();
        } catch (UnsupportedEncodingException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}
