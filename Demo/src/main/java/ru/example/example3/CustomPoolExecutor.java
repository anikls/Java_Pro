package main.java.ru.example.example3;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * @author NAgafonov
 */

public class CustomPoolExecutor {

    private final LinkedList<Thread> taskList = new LinkedList<>();
    private boolean isRun = false;
    private int capacity = 0;

    private Runnable poolExecutorTask = () -> {

        while (true) {
            Iterator<Thread> threadIterator = taskList.iterator();
            int currentNumber = 0;
            while (currentNumber < capacity && threadIterator.hasNext()) {
                Thread executedTask = threadIterator.next();
                currentNumber++;
                Thread.State state = executedTask.getState();
                try {
                    if (Thread.State.NEW.equals(state)) {
                        executedTask.start();
                    } else if (Thread.State.TERMINATED.equals(state)) {
                        threadIterator.remove();
                    }
                } catch (IllegalThreadStateException ex) {
                }
            }

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                break;
            }
        }
    };

    private final Thread poolExecutorThread = new Thread(poolExecutorTask);

    public CustomPoolExecutor(int capacity) {
        this.capacity = capacity;
        this.isRun = true;
        poolExecutorThread.setDaemon(false);
        poolExecutorThread.start();
    }

    public void execute(Thread command) {
        if (isRun) {
            this.taskList.add(command);
        } else {
            throw new IllegalStateException();
        }
    }

    public void shutdown() {
        this.isRun = false;
        poolExecutorThread.interrupt();
    }

}
