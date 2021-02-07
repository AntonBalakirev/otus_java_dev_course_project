package ru.otus;

public class LoggingImpl implements LoggingInterface {
    @Override
    public void calculation(int param) {
        System.out.println("Some logic with param: " + param);
    }

    @Override
    public void calculation(int param1, int param2) {
        System.out.println("Some logic with params: " + param1 + " and " + param2 );
    }

    @Override
    public void calculation(int param1, int param2, String param3) {
        System.out.println("Some logic with params: " + param1 + ", " + param2 + " and " + param3 );
    }

    @Override
    public String toString() {
        return "LoggingImpl{}";
    }
}
