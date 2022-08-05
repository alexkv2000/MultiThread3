package main.java;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class Main {
    private static final int SIZE = 1_000_000;
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        Integer[] array = createArray();
        System.out.printf("Массив случайных чисел размерностью %d\n%s\n", SIZE, Arrays.toString(array));
        long start = System.currentTimeMillis();
        int sum = sumArray(array);
        long finish = System.currentTimeMillis();
        int average = sum / array.length;
        System.out.printf("""
                ОДНОПОТОЧНЫЙ МЕТОД ПОДСЧЕТА
                Сумма чисел массива равна %d
                Среднее арифметическое элементов массива равно %d
                Время выполнение задачи %d мс
                """, sum, average, (finish - start));
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        start = System.currentTimeMillis();
        int sum2 = forkJoinPool.invoke(new ArraySumTask(0, array.length, array));
        finish = System.currentTimeMillis();
        average = sum2 / array.length;
        System.out.printf("""
                МНОГОПОТОЧНЫЙ МЕТОД ПОДСЧЕТА
                Сумма чисел массива равна %d
                Среднее арифметическое элементов массива равно %d
                Время выполнение задачи %d мс
                """, sum2, average, (finish - start));
    }

    private static int sumArray(Integer[] array) {
        int sum = 0;
        for (Integer arr : array) {
            sum += arr;
        }
        return sum;
    }

    private static Integer[] createArray() {
        Integer[] array = new Integer[Main.SIZE];
        for (int i = 0; i < array.length; i++) {
            array[i] = Main.RANDOM.nextInt(Main.SIZE);
        }
        return array;
    }
}
