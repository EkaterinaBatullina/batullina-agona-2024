package com.technokratos.blockingqueue;

import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueueTask {
    private static final ReentrantLock logLock = new ReentrantLock();

    public static void main(String[] args) {
        /*
         * Ограниченная очередь создает backpressure:
         * если буфер заполнен, producer блокируется в вызове put()
         * и не может производить данные быстрее, чем consumer успевает
         * их извлекать.
         *
         * Внутри ArrayBlockingQueue поток ожидает на Condition notFull,
         * связанной с общим ReentrantLock. После освобождения места
         * consumer выполняет signal(), а ожидающий поток переводится
         * в очередь синхронизатора и впоследствии пробуждается через
         * механизм LockSupport.unpark().
         *
         * В примере используется ArrayBlockingQueue, которая работает
         * через один общий lock для операций put() и take().
         *
         * В отличие от неё LinkedBlockingQueue использует два отдельных
         * lock'а (putLock и takeLock), что позволяет producer и consumer
         * эффективнее выполнять операции параллельно.
         */
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);

        /*
         * ExecutorService создается через try-with-resources.
         *
         * После выхода из try блока будет вызыван close()
         * реализации AutoCloseable, инициирующий корректное завершение запущенных задач.
         *
         * Это позволяет избежать ручного вызова shutdown().
         *
         * Каждая задача выполняется в отдельном виртуальном потоке.
         *
         * Виртуальные потоки позволяют писать код в привычном
         * блокирующем стиле (put(), take(), sleep()), не создавая
         * дорогостоящий платформенный поток на каждую задачу.
         */
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {

            executor.submit(() -> {
                try {
                    for (int i = 1; i <= 10; i++) {

                        /*
                         * Очередь имеет емкость 5 элементов.
                         *
                         * После заполнения буфера вызов put() блокируется до тех пор,
                         * пока consumer не освободит место через take().
                         *
                         * Благодаря этому producer автоматически замедляется и не может
                         * производить данные быстрее их обработки.
                         */
                        queue.put(i);

                        /*
                         * System.out является общим ресурсом для нескольких потоков.
                         *
                         * Без дополнительной синхронизации сообщения producer и consumer
                         * могут перемешиваться, усложняя анализ порядка выполнения.
                         *
                         * ReentrantLock используется для атомарного вывода.
                         */
                        logLock.lock();
                        try {
                            System.out.println("Producer: put %s".formatted(i));
                        } finally {
                            logLock.unlock();
                        }
                    }

                    /*
                     * ExecutorService завершается через try-with-resources.
                     * Выполняющиеся задачи могут получить interrupt().
                     *
                     * В этом случае блокирующая операция выбрасывает
                     * InterruptedException, а флаг прерывания необходимо
                     * восстановить повторным вызовом interrupt(),
                     * чтобы информация о запросе на завершение не потерялась.
                     */
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });

            executor.submit(() -> {
                try {
                    for (int i = 1; i <= 10; i++) {

                        /*
                         * take() симметрична операции put():
                         * если очередь пуста, поток consumer будет ожидать
                         * появления нового элемента.
                         */
                        Integer item = queue.take();

                        logLock.lock();
                        try {
                            System.out.println("Consumer: take %s".formatted(item));
                        } finally {
                            logLock.unlock();
                        }

                        /*
                         * Искусственная задержка имитирует медленную обработку данных.
                         *
                         * Благодаря этому можно наблюдать работу backpressure:
                         * producer периодически вынужден ждать освобождения места
                         * в ограниченном буфере.
                         */
                        Thread.sleep(50);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });

        }

        System.out.println("All producer and consumer tasks completed successfully.");
    }
}