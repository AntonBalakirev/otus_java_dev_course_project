package ru.otus;

public class Main {

    public static void main(String[] args) {
        LoggingInterface loggedClass = Ioc.createLoggedClass();
        loggedClass.calculation(1);
        loggedClass.calculation(1, 2);
        loggedClass.calculation(1, 2, "3");
    }
}
