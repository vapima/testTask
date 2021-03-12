package ru.vapima.testTask.variant2;

import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.vapima.testTask.Main.CSV_ORIGINAL;
import static ru.vapima.testTask.Main.PATH;

/**
 * @author Vasily Pima
 * Поток который заюирает строчку из общего файла, и сохраняет в совй файл.
 */
public class LinesGrabber implements Runnable {
    private final String name;
    private final AtomicInteger linesCounterInOriginalFile;

    public LinesGrabber(String name, AtomicInteger linesCounterInOriginalFile) {
        this.name = name;
        this.linesCounterInOriginalFile = linesCounterInOriginalFile;
    }


    @Override
    public void run() {
        System.out.println(name + ": START");
        try (BufferedReader fp = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(PATH + CSV_ORIGINAL), "Cp1251"));
             PrintWriter pw = new PrintWriter
                     (new OutputStreamWriter
                             (new FileOutputStream
                                     (PATH + name + ".csv"), "Cp1251"))) {
            String line;
            Integer insideCounter = 0; //Счетчик строк пройденых отдельным(текущим) потоком
            Integer availableCount = linesCounterInOriginalFile.getAndIncrement(); //получаем доступный номер строки для изменения
            while ((line = fp.readLine()) != null) { //Бежим по строчкам в файле
                if (insideCounter.equals(availableCount)) { //если номер строки равен номеру строки доступной для изменения
                    pw.write(line + "\r\n"); //записываем в свой файл эту строчку
                    availableCount = linesCounterInOriginalFile.getAndIncrement(); //получаем новый доступный номер
                }
                insideCounter++;
            }
            System.out.println(name + ": STOPPED");
        } catch (FileNotFoundException e) {
            System.err.println("File not found.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
