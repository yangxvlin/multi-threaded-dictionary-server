package com.unimelb.comp90015.Server.ThreadPool;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * Xulin Yang, 904904
 *
 * @create 2020-03-23 22:44
 * description: the customized thread pool with PriorityBlockingQueue to pop
 *              threads to be executed by workers
 **/

public class ThreadPool {
    /**
     * Thread pool size
     */
    private final int poolSize;

    /**
     * an array of workers
     */
    private final WorkerThread[] workers;

    /**
     * VIP level and time based priority queue
     */
    private final PriorityBlockingQueue<PriorityTaskThread> queue;

    public ThreadPool(int poolSize) {
        this.poolSize = poolSize;
        queue = new PriorityBlockingQueue<>();
        workers = new WorkerThread[poolSize];

        for (int i = 0; i < poolSize; i++) {
            workers[i] = new WorkerThread();
            workers[i].start();
        }
    }

    /**
     * @param task: task added to pool's queue to be executed
     */
    public void execute(PriorityTaskThread task) {
        synchronized (queue) {
            queue.add(task);
            queue.notify();
        }
    }

    /**
     * terminate all workers
     */
    public void shutdown() {
        System.out.println("Shutting down thread pool");
        for (int i = 0; i < poolSize; i++) {
            workers[i] = null;
        }
    }

    /**
     * @return current queued tasks' size
     */
    public synchronized int getQueueSize() {
        return queue.size();
    }

    /**
     * Worker thread in thread pool
     */
    private class WorkerThread extends Thread {
        public void run() {
            PriorityTaskThread task;

            while (true) {
                synchronized (queue) {
                    while (queue.isEmpty()) {
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            System.out.println("An error occurred while queue is waiting: " + e.getMessage());
                        }
                    }

                    // pop queued task to be executed
                    task = queue.poll();
                }

                try {
                    task.start();
                } catch (RuntimeException e) {
                    System.out.println("Thread pool is interrupted due to an issue: " + e.getMessage());
                }
            }
        }
    }
}
