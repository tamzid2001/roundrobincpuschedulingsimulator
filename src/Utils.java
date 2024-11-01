// src/Utils.java
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Utils {
    /**
     * Reads processes from a CSV file.
     * Expected format: PID,ArrivalTime,BurstTime
     *
     * @param filePath Path to the CSV file.
     * @return List of Process objects.
     */
    public static ArrayList<Process> readProcesses(String filePath) {
        ArrayList<Process> processes = new ArrayList<>();
        String line;
        String splitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                // Skip empty lines
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(splitBy);
                if (parts.length < 3) {
                    System.out.println("Invalid line in process file: " + line);
                    continue;
                }
                int pid = Integer.parseInt(parts[0].trim());
                int arrivalTime = Integer.parseInt(parts[1].trim());
                int burstTime = Integer.parseInt(parts[2].trim());
                processes.add(new Process(pid, arrivalTime, burstTime));
            }
        } catch (IOException e) {
            System.out.println("Error reading the process file: " + e.getMessage());
            System.exit(1);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format in process file: " + e.getMessage());
            System.exit(1);
        }

        return processes;
    }

    /**
     * Prints the metrics in a formatted way.
     *
     * @param metrics A Metrics object containing all performance metrics.
     */
    public static void printMetrics(Metrics metrics) {
        System.out.println("Average Turnaround Time: " + String.format("%.2f", metrics.getAverageTurnaroundTime()));
        System.out.println("Average Waiting Time: " + String.format("%.2f", metrics.getAverageWaitingTime()));
        System.out.println("Average Response Time: " + String.format("%.2f", metrics.getAverageResponseTime()));
        System.out.println("CPU Utilization: " + String.format("%.2f", metrics.getCpuUtilization()) + "%");
        System.out.println("Throughput: " + String.format("%.2f", metrics.getThroughput()) + " processes/unit time");
        System.out.println("Context Switches: " + metrics.getContextSwitches());
    }
}
