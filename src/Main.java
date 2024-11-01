// src/Main.java
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // Check for correct number of arguments
        if (args.length != 2) {
            System.out.println("Usage: java Main <process_file> <time_quantum>");
            System.exit(1);
        }

        String processFile = args[0];
        int timeQuantum = 0;

        // Parse time quantum
        try {
            timeQuantum = Integer.parseInt(args[1]);
            if (timeQuantum <= 0) {
                System.out.println("Time quantum must be a positive integer.");
                System.exit(1);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid time quantum. It must be an integer.");
            System.exit(1);
        }

        // Read processes from file
        ArrayList<Process> processes = Utils.readProcesses(processFile);

        // Run Round Robin Scheduler
        Metrics metrics = Scheduler.roundRobin(processes, timeQuantum);

        // Print the metrics
        Utils.printMetrics(metrics);
    }
}
