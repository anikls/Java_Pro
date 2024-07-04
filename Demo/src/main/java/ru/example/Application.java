package main.java.ru.example;

import main.java.ru.example.examle2.StreamExamples;
import main.java.ru.example.example1.CustomTest;
import main.java.ru.example.example3.TestRunner;

import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author NAgafonov
 */
public class Application {

    public static void main(String[] args) throws Exception {
        //TestRunner.runTest(CustomTest.class);
        //StreamExamples.testRun();

        TestRunner.runTest();
    }
}
