package main.java.ru.example.example3_;

import java.util.concurrent.TimeUnit;

/**
 * @author NAgafonov
 */
public class TestRunner {

    public static void runTest() {

        CustomPoolExecutor customPoolExecutor = new CustomPoolExecutor(5);
        // Запуск выполнения задач через CustomPoolExecutor
        for (int i = 0; i < 20; i++) {
           Runnable task = new CustomTask(i);
           customPoolExecutor.execute(task);
        }

        try {
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        customPoolExecutor.shutdown();
        // Попытка выполнения задачи с остановленным CustomPoolExecutor
        customPoolExecutor.execute(new CustomTask(100));
    }

}
