package com.unimelb.comp90015.Server.Timer;

import sun.rmi.runtime.Log;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Xulin Yang, 904904
 *
 * @create 2020-03-31 14:55
 * description:
 **/

public class CountDownTimer {

    private long millisecondsPeriod;

    private boolean isFinished;

    /**
     * @param period seconds to count down
     */
    public CountDownTimer(long period) {
        millisecondsPeriod = TimeUnit.SECONDS.toMillis(period);
        isFinished = false;
    }

    public void startCountDown() {
        isFinished = false;
        long start = System.currentTimeMillis();
        final long end = start + millisecondsPeriod;

        final Timer timer = new Timer();
//        //延迟0毫秒（即立即执行）开始，每隔1000毫秒执行一次
//        timer.schedule(new TimerTask() {
//            public void run() {
//                Log.e("MainActivity", "此处实现倒计时，指定时长内，每隔1秒执行一次该任务");
//            }
//        }, 0, 1000);
        //计时结束时候，停止全部timer计时计划任务
        timer.schedule(new TimerTask() {
            public void run() {
                timer.cancel();
                isFinished = true;
            }
        }, new Date(end));
    }

    public void restart() {
        startCountDown();
    }

    public boolean isFinished() {
        return isFinished;
    }
}
