package ru.otus;

public interface LoggingInterface {
    @Log
    void calculation(int param);

    @Log
    void calculation(int param1, int param2);

    @Log
    void calculation(int param1, int param2, String param3);
}