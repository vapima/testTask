package ru.vapima.testTask.variant2;

import java.io.*;

import static ru.vapima.testTask.Main.CSV_ORIGINAL;
import static ru.vapima.testTask.Main.PATH;

/**
 * @author Vasily Pima
 * Producer
 */
public class LinesGrabber implements Runnable {
    private final String name;
    private final CounterLines counterLines; //Счетчик строк пройденых всеми потоками

    public LinesGrabber(String name, CounterLines counterLines) {
        this.name = name;
        this.counterLines = counterLines;
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
            Integer insideCounter = 0; //Счетчик строк пройденых отдельным потоком
            while ((line = fp.readLine()) != null) { //Берем строчку
                synchronized (counterLines) {  //не даем изменять счетчики и записывать другим потокам
                    if (counterLines.getCount().equals(insideCounter)) { //проверяем добежали ли мы до еще не стыреной строчки
                        counterLines.inc(); //инкрементим общий счетчик, давац понять остальным потокам что мы забрали эту строку
                        pw.write(line + "\r\n"); //записываем в свой файл строчку
                    }
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
