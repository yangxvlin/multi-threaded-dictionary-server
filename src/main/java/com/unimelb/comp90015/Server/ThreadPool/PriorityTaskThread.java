package com.unimelb.comp90015.Server.ThreadPool;

import java.util.Date;

/**
 * Xulin Yang, 904904
 *
 * @create 2020-03-23 23:19
 * description: Thread to be used in PriorityBlockingQueue to wrap various task thread
 **/

public class PriorityTaskThread implements Runnable, Comparable<PriorityTaskThread> {
    /**
     * task thread to be executed
     */
    private Runnable task;

    /**
     * priority number
     */
    private int priority;

    /**
     * task's creation time
     */
    private Date allocatedTime;

    public PriorityTaskThread(Runnable task, int priority, Date allocatedTime) {
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
        this.task.run();
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
        int result = 0;

        if (this.priority < other.priority) {
            result =  -1;
        } else if (this.priority > other.priority) {
            result =  1;
        } else if (this.allocatedTime.before(other.allocatedTime)) {
            result =  1;
        } else if (this.allocatedTime.after(other.allocatedTime)) {
            result =  -1;
        }

        // reverse the result as the PriorityBlockingQueue poll the element with
        // min value. i.e. reverse the result makes the system poll the higher
        // priority element or an earlier one in the corner case
        return -1 * result;
    }
}
