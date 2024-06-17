package main.java.ru.example;

import main.java.ru.example.annotations.AfterSuite;
import main.java.ru.example.annotations.BeforeSuite;
import main.java.ru.example.annotations.CsvSource;
import main.java.ru.example.annotations.Test;

/**
 * @author NAgafonov
 */
public class CustomTest {

    @BeforeSuite
    public static void beforeTest() {
        System.out.println("Before test");
    }

    @Test(priority = 10)
    @CsvSource("10,Java,true,false")
    public void testMethod1(int a, String b, boolean c, boolean d) {
        System.out.println("Method1. Priority 10");

    }

    @Test
    @CsvSource("10,Java,20,true")
    public void testMethod2(int a, String b, int c, boolean d) {
        System.out.println("Method2. Priority 5");
    }

    @Test(priority = 1)
    @CsvSource("10,Java,20,true")
    public void testMethod3(int a, String b, int c, boolean d) {
        System.out.println("Method3. Priority 1");
    }

    @AfterSuite
    public static void afterTest() {
        System.out.println("After test");
    }

}
