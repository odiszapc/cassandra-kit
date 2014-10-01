/*
 * Copyright 2014 Alexey Plotnik
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.odiszapc.casskit.repair;


import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

class Tracker {
    final static int MAX_MEASUREMENTS = 100;
    final int total;
    final int parallelism;
    final LinkedList<Long> measurements = new LinkedList<Long>();
    AtomicInteger completed = new AtomicInteger(0);
    AtomicInteger failed = new AtomicInteger(0);
    private long remainingTimeSec;
    private long startTime = System.currentTimeMillis();
    private long tasksPerHour;

    public Tracker(int size, int parallelism) {
        this.total = size;
        this.parallelism = parallelism;
    }


    public synchronized void onComplete(long duration) {
        completed.incrementAndGet();
        if (measurements.size() > MAX_MEASUREMENTS)
            measurements.pollFirst();

        measurements.addLast(duration);

        long totalDuration = 0;
        for (Long dur : measurements) {
            totalDuration += dur;
        }

        double abstractTaskDuration = (double) totalDuration / measurements.size() / parallelism;
        this.tasksPerHour = Math.round(1 / abstractTaskDuration * 3600);
        this.remainingTimeSec = Math.round(abstractTaskDuration * (total - completed.get() - failed.get()));
    }

    public void onError() {
        failed.incrementAndGet();
    }

    public String toString() {
        double progress = (double) Math.round((double) (completed.get() + failed.get()) / total * 10000) / 100;

        return "=======================\n" +
                "SUMMARY\n" +
                BigDecimal.valueOf(progress).toPlainString() + "% completed" +
                "\nTotal: " + total +
                "\nCompleted: " + completed.get() +
                "\nFailed: " + failed.get() +
                "\nRepair running time: " + human((System.currentTimeMillis() - startTime) / 1000) +
                "\nTasks remaining: " + (total - completed.get() - failed.get()) +
                "\nTasks per hour: " + tasksPerHour +
                "\nRemaining time: " + human(remainingTimeSec) + "\n" +
                "=======================\n";
    }

    private String human(long timeSec) {
        long x = timeSec;
        long seconds = (x % 60);
        x /= 60;
        long minutes = x % 60;
        x /= 60;
        long hours = x % 24;
        x /= 24;
        long days = x;

        return String.format("%s days, %s hours, %s min, %s sec", days, hours, minutes, seconds);
    }

}
