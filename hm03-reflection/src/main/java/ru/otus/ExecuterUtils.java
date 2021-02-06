package ru.otus;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;
import ru.otus.exceptions.FixtureException;
import ru.otus.exceptions.TestMethodException;
import ru.otus.test.BaseTest;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ExecuterUtils {

    public static void executeTestClass(Class<? extends BaseTest> testClass) throws TestMethodException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        Method[] methodArray = testClass.getDeclaredMethods();
        List<Method> testMethodsList = getMethods(methodArray, Test.class);
        List<Method> beforeMethodsList = getMethods(methodArray, Before.class);
        List<Method> afterMethodsList = getMethods(methodArray, After.class);

        int allTestCount = 0;
        int failedTestCount = 0;

        for (Method method : testMethodsList) {
            if (method.isAnnotationPresent(Test.class)) {
                BaseTest exampleTest = testClass.getDeclaredConstructor().newInstance();

                try {
                    executeBeforeMethod(beforeMethodsList, exampleTest);
                    failedTestCount +=
                            executeTestMethod(method, exampleTest);
                } catch (FixtureException e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                    return;
                }

                try {
                    executeAfterMethod(afterMethodsList, exampleTest);
                } catch (FixtureException e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                    return;
                }

                allTestCount++;
            }
        }

        System.out.println(
                "Test Results: run " + allTestCount +
                        ", passed " + (allTestCount - failedTestCount) +
                        ", failed " + failedTestCount
        );
    }

    private static List<Method> getMethods(Method[] methodArray, Class<? extends Annotation> clazz) {
        return Arrays.stream(methodArray)
                .filter(method -> method.isAnnotationPresent(clazz))
                .collect(Collectors.toList());
    }

    public static void executeBeforeMethod(List<Method> beforeMethodsList, BaseTest exampleTest) throws FixtureException {
        for (Method m : beforeMethodsList) {
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

    public static int executeTestMethod(Method method, BaseTest exampleTest) throws TestMethodException {
        try {
            method.invoke(exampleTest);
        } catch (IllegalAccessException e) {
            throw new TestMethodException("IllegalAccessException in test method execution", e);
        } catch (InvocationTargetException e) {
            System.out.println("Test is failed. Exception was caught in 'test' method");
            e.printStackTrace();
            return 1;
        }
        return 0;
    }

    public static void executeAfterMethod(List<Method> afterMethodsList, BaseTest exampleTest) throws FixtureException {
        for (Method m : afterMethodsList) {
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
