package ru.otus;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;
import ru.otus.exceptions.FixtureException;
import ru.otus.exceptions.TestException;
import ru.otus.test.BaseTest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ExecuterUtils {

    public static void executeTestClass(Class<? extends BaseTest> testClass) throws TestException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        BaseTest exampleTest = testClass.getDeclaredConstructor().newInstance();
        Method[] methodArray = testClass.getDeclaredMethods();

        int allTestCount = 0;
        int failedTestCount = 0;

        for (Method method : methodArray) {
            if (method.isAnnotationPresent(Test.class)) {
                try {
                    executeBeforeMethod(methodArray, exampleTest);
                    failedTestCount +=
                            executeTestMethod(method, exampleTest);
                    executeAfterMethod(methodArray, exampleTest);
                    allTestCount++;
                } catch (FixtureException e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                    return;
                }
            }
        }

        System.out.println(
                "Test Results: run " + allTestCount +
                        ", passed " + (allTestCount - failedTestCount) +
                        ", failed " + failedTestCount
        );
    }

    public static void executeBeforeMethod(Method[] methodArray, BaseTest exampleTest) throws FixtureException {
        for (Method m : methodArray) {
            if (m.isAnnotationPresent(Before.class)) {
                try {
                    m.invoke(exampleTest);
                } catch (IllegalAccessException e) {
                    throw new FixtureException("IllegalAccessException in before method execution", e);
                } catch (InvocationTargetException e) {
                    throw new FixtureException("Test fixture is broken. Exception was caught in 'before' method", e);
                }
            }
        }
    }

    public static int executeTestMethod(Method method, BaseTest exampleTest) throws TestException {
        try {
            method.invoke(exampleTest);
        } catch (IllegalAccessException e) {
            throw new TestException("IllegalAccessException in test method execution", e);
        } catch (InvocationTargetException e) {
            System.out.println("Test is failed. Exception was caught in 'test' method");
            e.printStackTrace();
            return 1;
        }
        return 0;
    }

    public static void executeAfterMethod(Method[] methodArray, BaseTest exampleTest) throws FixtureException {
        for (Method m : methodArray) {
            if (m.isAnnotationPresent(After.class)) {
                try {
                    m.invoke(exampleTest);
                } catch (IllegalAccessException e) {
                    throw new FixtureException("IllegalAccessException in after method execution", e);
                } catch (InvocationTargetException e) {
                    throw new FixtureException("Test fixture is broken. Exception was caught in 'after' method", e);
                }
            }
        }
    }
}
