package ru.otus;

import ru.otus.exceptions.TestMethodException;
import ru.otus.test.ExampleTest;
import ru.otus.test.ExampleTestWithBrokenFixture;
import ru.otus.test.ExampleTestWithTestException;

import static ru.otus.ExecuterUtils.executeTestClass;

public class Main {

    public static void main(String[] args) throws TestMethodException {

        System.out.println("\nExecution with broken fixture");
        System.out.println("======================");
        ExampleTestWithBrokenFixture
                exampleTestWithBrokenFixture = new ExampleTestWithBrokenFixture();
        executeTestClass(exampleTestWithBrokenFixture);
        System.out.println("======================");

        System.out.println("\nExecution with test failure");
        System.out.println("======================");
        ExampleTestWithTestException
                exampleTestWithTestException = new ExampleTestWithTestException();
        executeTestClass(exampleTestWithTestException);
        System.out.println("======================");

        System.out.println("\nSuccessful execution");
        System.out.println("======================");
        ExampleTest
                exampleTest = new ExampleTest();
        executeTestClass(exampleTest);
        System.out.println("======================");
    }
}
