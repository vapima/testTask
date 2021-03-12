package ru.vapima.testTask.variant2;

import java.io.*;
import java.util.concurrent.BlockingQueue;

import static ru.vapima.testTask.variant1.Launcher.*;

/**
 * @author Vasily Pima
 * Producer
 */
public class CsvReader implements Runnable {
    public CsvReader(String name) {
        this.name = name;
    }

    private final String name;


    @Override
    public void run() {



        try ( BufferedReader fp = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(PATH + CSV_ORIGINAL), "Cp1251"));
              PrintWriter pw = new PrintWriter
                      (new OutputStreamWriter
                              (new FileOutputStream
                                      (PATH + name + ".csv"), "Cp1251"));) {
            while ((line = fp.readLine()) != null) {
                pw.write(line + "\r\n");
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found.");
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
