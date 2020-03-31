package com.unimelb.comp90015.Server.ThreadPool;

import java.util.Date;

/**
 * Xulin Yang, 904904
 *
 * @create 2020-03-23 23:19
 * description:
 **/

public class PriorityTaskThread extends Thread implements Comparable<PriorityTaskThread> {
    private Thread task;

    private int priority;

    private Date allocatedTime;

    public PriorityTaskThread(Thread task, int priority, Date allocatedTime) {
        this.task = task;
        this.priority = priority;
        this.allocatedTime = allocatedTime;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * run method to be called in that separately executing
     * thread.
     */
    @Override
    public void run() {
        this.task.start();
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * @param other the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     */
    public int compareTo(PriorityTaskThread other) {
        if (this.priority < other.priority) {
            return -1;
        } else if (this.priority > other.priority) {
            return 1;
        } else if (this.allocatedTime.before(other.allocatedTime)) {
            return -1;
        } else if (this.allocatedTime.after(other.allocatedTime)) {
            return 1;
        }

        return 0;
    }
}
