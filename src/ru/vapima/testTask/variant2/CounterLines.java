package ru.vapima.testTask.variant2;

public class CounterLines {
    private Integer count = 1; //Пропускаем первыую строчку

    public void inc() {
        count++;
    }

    public Integer getCount() {
        return count;
    }

}
