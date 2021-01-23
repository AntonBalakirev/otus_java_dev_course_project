package ru.otus;

import ru.otus.exceptions.TestException;
import ru.otus.test.ExampleTest;
import ru.otus.test.ExampleTestWithBrokenFixture;
import ru.otus.test.ExampleTestWithTestException;

import java.lang.reflect.InvocationTargetException;

import static ru.otus.ExecuterUtils.executeTestClass;

public class Main {

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException, TestException {

        System.out.println("\nExecution with broken fixture");
        System.out.println("======================");
        executeTestClass(ExampleTestWithBrokenFixture.class);
        System.out.println("======================");

        System.out.println("\nExecution with test failure");
        System.out.println("======================");
        executeTestClass(ExampleTestWithTestException.class);
        System.out.println("======================");

        System.out.println("\nSuccessful execution");
        System.out.println("======================");
        executeTestClass(ExampleTest.class);
        System.out.println("======================");
    }
}
