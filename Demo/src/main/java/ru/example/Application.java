package main.java.ru.example;

import main.java.ru.example.examle2.StreamExamples;
import main.java.ru.example.example1.CustomTest;
import main.java.ru.example.example1.TestRunner;

/**
 * @author NAgafonov
 */
public class Application {

    public static void main(String[] args) throws Exception {
        //TestRunner.runTest(CustomTest.class);
        StreamExamples.testRun();
    }
}
