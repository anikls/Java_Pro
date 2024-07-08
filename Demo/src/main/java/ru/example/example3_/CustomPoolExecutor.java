package main.java.ru.example.example3_;

import java.util.LinkedList;
import java.util.concurrent.Executor;

/**
 * @author NAgafonov
 */

public class CustomPoolExecutor implements Executor {

    private final LinkedList<Runnable> taskList = new LinkedList<>();

    private volatile boolean isRunning = true;

    public CustomPoolExecutor(int capacity) {
        for (int i = 0; i < capacity; i++) {
            new Thread(new TaskWorker()).start();
        }
    }

    @Override
    public void execute(Runnable task) {
        if (!isRunning) {
            throw new IllegalStateException();
        }
        taskList.add(task);
    }

    public void shutdown() {
        isRunning = false;
    }

    private final class TaskWorker implements Runnable {

        @Override
        public void run() {
            while (isRunning) {
                Runnable nextTask = taskList.poll();
                if (nextTask != null) {
                    nextTask.run();
                }
            }
        }
    }
}

