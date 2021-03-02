package ru.vapima.testTask;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static ru.vapima.testTask.Main.*;


public class Validation {
    public static void valid() {
        System.out.println("---===VALIDATING===---");
        //Грузим в мапу исходный файл.
        Map<String, String> originalsLines = new HashMap<>();
        try (BufferedReader fp = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(PATH + CSV_ORIGINAL), "Cp1251"))) {
            String line;
            boolean isFirst = true;  //Пропускаем первую строчку (шапку).
            while ((line = fp.readLine()) != null) {
                if (isFirst) {
                    isFirst = false;
                } else {
                    originalsLines.put(line, "ok");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        int countThreads = THREADS;
        int countAllLinesInAllOutputFile = 0;
        int countLinesInOriginalFile = originalsLines.size();
        //Грузим в мапу строки из только созданых файлов. Заодно проверям на дубликаты и подсчитываем количество строк.
        while (countThreads > 0) {
            Integer countLineInFile = putLinesFromOutputFileInMap("n" + countThreads, originalsLines);
            System.out.println("File n" + countThreads + ".csv have " + countLineInFile + " lines.");
            countAllLinesInAllOutputFile += countLineInFile;
            countThreads--;
        }
        System.out.println("Total: " + countAllLinesInAllOutputFile);
        System.out.println("Original file have " + countLinesInOriginalFile + " lines.");
        if (countAllLinesInAllOutputFile == countLinesInOriginalFile) {
            System.out.println("Correct counts of lines.");
        } else {
            throw new RuntimeException("Incorrect counts of lines.");
        }
        System.out.println("Duplicated not found.");
    }

    private static Integer putLinesFromOutputFileInMap(String name, Map<String, String> originalsLines) {
        int countOfLinesInOutputFile = 0;
        try (BufferedReader fp = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(PATH + name + ".csv"), "Cp1251"))) {
            String line;
            while ((line = fp.readLine()) != null) {
                String putAnswer = originalsLines.put(line, name);
                //Если такой строки не было в оригинале.
                if (putAnswer == null) {
                    throw new RuntimeException("Validation false. Not found in original file: " + line);
                }
                //Если значение изменено, значит такая строчка уже где то была и добавлена в мапу. Дубликат.
                else if (!putAnswer.equals("ok")) {
                    throw new RuntimeException("Validation false. Duplicate found. "+"Duplicated: " + line);
                }
                countOfLinesInOutputFile++;
            }

        } catch (FileNotFoundException e) {
            System.err.println("Fail not found.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return countOfLinesInOutputFile; //Возвращаем количство строк в просматриваемом файле.
    }

    public static void main(String[] args) {
        valid();
    }
}
