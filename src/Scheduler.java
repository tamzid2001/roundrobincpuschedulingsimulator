// src/Scheduler.java
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Scheduler {

    /**
     * Implements the Round Robin scheduling algorithm.
     *
     * @param processList List of processes to schedule.
     * @param timeQuantum Time quantum for Round Robin.
     * @return Metrics object containing performance metrics.
     */
    public static Metrics roundRobin(ArrayList<Process> processList, int timeQuantum) {
        // Sort processes based on arrival time
        processList.sort((p1, p2) -> Integer.compare(p1.getArrivalTime(), p2.getArrivalTime()));

        Queue<Process> readyQueue = new LinkedList<>();
        int currentTime = 0;
        int completed = 0;
        int n = processList.size();
        int contextSwitches = 0;
        int cpuIdle = 0;

        ArrayList<Process> processes = new ArrayList<>(processList); // Clone to avoid modifying original list

        // To keep track of processes that have been added to the ready queue
        boolean[] inReadyQueue = new boolean[n];

        while (completed < n) {
            // Add all processes that have arrived by current time to the ready queue
            for (int i = 0; i < n; i++) {
                Process p = processes.get(i);
                if (p.getArrivalTime() <= currentTime && p.getRemainingTime() > 0 && !inReadyQueue[i]) {
                    readyQueue.add(p);
                    inReadyQueue[i] = true;
                }
            }

            if (readyQueue.isEmpty()) {
                currentTime++;
                cpuIdle++;
                continue;
            }

            Process currentProcess = readyQueue.poll();
            contextSwitches++;

            if (!currentProcess.isStarted()) {
                currentProcess.setResponseTime(currentTime - currentProcess.getArrivalTime());
                currentProcess.setStarted(true);
            }

            // Execute the process for a time slice
            int execTime = Math.min(timeQuantum, currentProcess.getRemainingTime());
            currentProcess.setRemainingTime(currentProcess.getRemainingTime() - execTime);
            currentTime += execTime;

            // During execution, check for newly arrived processes
            for (int i = 0; i < n; i++) {
                Process p = processes.get(i);
                if (p.getArrivalTime() > (currentTime - execTime) && p.getArrivalTime() <= currentTime
                        && p.getRemainingTime() > 0 && !inReadyQueue[i]) {
                    readyQueue.add(p);
                    inReadyQueue[i] = true;
                }
            }

            if (currentProcess.getRemainingTime() > 0) {
                readyQueue.add(currentProcess);
            } else {
                completed++;
                currentProcess.setCompletionTime(currentTime);
                currentProcess.setTurnaroundTime(currentProcess.getCompletionTime() - currentProcess.getArrivalTime());
                currentProcess.setWaitingTime(currentProcess.getTurnaroundTime() - currentProcess.getBurstTime());
            }
        }

        // Calculate metrics
        double totalTurnaround = 0;
        double totalWaiting = 0;
        double totalResponse = 0;

        for (Process p : processes) {
            totalTurnaround += p.getTurnaroundTime();
            totalWaiting += p.getWaitingTime();
            totalResponse += p.getResponseTime();
        }

        double avgTurnaround = totalTurnaround / n;
        double avgWaiting = totalWaiting / n;
        double avgResponse = totalResponse / n;
        double cpuUtilization = 1 - ((double) cpuIdle / currentTime);
        double throughput = (double) n / currentTime;

        Metrics metrics = new Metrics();
        metrics.setAverageTurnaroundTime(avgTurnaround);
        metrics.setAverageWaitingTime(avgWaiting);
        metrics.setAverageResponseTime(avgResponse);
        metrics.setCpuUtilization(cpuUtilization * 100); // Convert to percentage
        metrics.setThroughput(throughput);
        metrics.setContextSwitches(contextSwitches);

        return metrics;
    }
}
