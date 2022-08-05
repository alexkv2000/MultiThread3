package main.java;

import java.util.concurrent.RecursiveTask;

public class ArraySumTask extends RecursiveTask<Integer> {
    private final int start;
    private final int end;
    private final Integer[] array;

    public ArraySumTask(int start, int end, Integer[] array) {
        this.start = start;
        this.end = end;
        this.array = array;
    }

    @Override
    protected Integer compute() {
        final int diff = end - start;
        switch (diff) {
            case 0: return 0;
            case 1: return array[start];
            case 2: return array[start] + array[start+1];
            default: return forkTasksAndGetResult();
        }
    }

    private int forkTasksAndGetResult() {
        final int middle = (end - start) / 2 + start;
        ArraySumTask task1 = new ArraySumTask(start, middle, array);
        ArraySumTask task2 = new ArraySumTask(middle, end, array);
        invokeAll(task1, task2);
//        task1.invoke();
//        task2.invoke();

        return task1.join() + task2.join();
    }
}
