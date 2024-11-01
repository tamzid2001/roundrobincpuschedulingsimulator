// src/Metrics.java
public class Metrics {
    private double averageTurnaroundTime;
    private double averageWaitingTime;
    private double averageResponseTime;
    private double cpuUtilization; // in percentage
    private double throughput;
    private int contextSwitches;

    // Getters and Setters
    public double getAverageTurnaroundTime() { return averageTurnaroundTime; }
    public void setAverageTurnaroundTime(double averageTurnaroundTime) { this.averageTurnaroundTime = averageTurnaroundTime; }
    public double getAverageWaitingTime() { return averageWaitingTime; }
    public void setAverageWaitingTime(double averageWaitingTime) { this.averageWaitingTime = averageWaitingTime; }
    public double getAverageResponseTime() { return averageResponseTime; }
    public void setAverageResponseTime(double averageResponseTime) { this.averageResponseTime = averageResponseTime; }
    public double getCpuUtilization() { return cpuUtilization; }
    public void setCpuUtilization(double cpuUtilization) { this.cpuUtilization = cpuUtilization; }
    public double getThroughput() { return throughput; }
    public void setThroughput(double throughput) { this.throughput = throughput; }
    public int getContextSwitches() { return contextSwitches; }
    public void setContextSwitches(int contextSwitches) { this.contextSwitches = contextSwitches; }
}
