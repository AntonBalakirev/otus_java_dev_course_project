package ru.otus.test;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

public class ExampleTestWithBrokenFixture extends BaseTest {
    @Before
    public void before() {
        System.out.println("@Before");
        throw new RuntimeException();
    }

    @Test
    public void test1() {
        System.out.println("@Test1");
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
