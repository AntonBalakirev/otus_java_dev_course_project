package ru.otus.test;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

public class ExampleTestWithTestException extends BaseTest{
    @Before
    public void before() {
        System.out.println("@Before");
    }

    @Test
    public void test1() {
        System.out.println("@Test1");
        throw new RuntimeException();
    }

    @Test
    public void test2() {
        System.out.println("@Test2");
    }

    @After
    public void after() {
        System.out.println("@After");
    }
}
