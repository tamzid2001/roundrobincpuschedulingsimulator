# Round Robin CPU Scheduling Simulator

![Project Logo](https://example.com/logo.png) *(Optional: Add a relevant logo)*

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [Classes Documentation](#classes-documentation)
  - [Process](#process)
  - [Metrics](#metrics)
  - [Utils](#utils)
  - [Scheduler](#scheduler)
  - [Main](#main)
  - [PriorityScheduler](#priorityscheduler) *(Extra Credit)*
- [Installation](#installation)
- [Usage](#usage)
- [Screenshots](#screenshots)
- [Extra Credit](#extracredit)
- [Analysis of Results](#analysis-of-results)
- [Conclusion](#conclusion)
- [References](#references)
- [License](#license)

---

## Overview

The **Round Robin CPU Scheduling Simulator** is a Java-based application designed to emulate the Round Robin scheduling algorithm used by operating systems to manage process execution. This simulator reads a list of processes from a CSV file, each with a unique Process ID, Arrival Time, and Burst Time, and schedules them based on a user-defined Time Quantum. The program calculates and displays essential performance metrics, including Average Turnaround Time, Average Waiting Time, CPU Utilization, Throughput, and the number of Context Switches.

---

## Features

- **Round Robin Scheduling:** Implements the classic Round Robin CPU scheduling algorithm with configurable Time Quantum.
- **Performance Metrics:** Calculates key metrics such as Turnaround Time, Waiting Time, Response Time, CPU Utilization, Throughput, and Context Switches.
- **Priority Round Robin Scheduling:** *(Extra Credit)* Extends the basic algorithm to incorporate process priorities, allowing higher-priority processes to be scheduled preferentially.
- **CSV Input:** Reads process data from a structured CSV file for ease of use and flexibility.
- **Command-Line Interface:** Simple and intuitive CLI for specifying input files and Time Quantum.
- **Extensible Design:** Modular class structure allows for easy enhancements and additional features.

---

## Technologies Used

- **Java 8 or above**
- **Java Collections Framework**
- **CSV File Handling**
- **Object-Oriented Programming Principles**

---

## Project Structure

```
RoundRobinScheduler/
│
├── src/
│   ├── Process.java
│   ├── Metrics.java
│   ├── Utils.java
│   ├── Scheduler.java
│   ├── PriorityScheduler.java *(Extra Credit)*
│   └── Main.java
│
├── input/
│   └── processes.csv
│
├── screenshots/
│   ├── quantum_1.png
│   ├── quantum_2.png
│   ├── quantum_3.png
│   ├── quantum_4.png
│   └── quantum_5.png
│
├── README.pdf
└── README.md
```

---

## Classes Documentation

### Process

**File:** `src/Process.java`

**Description:**

The `Process` class represents a single process in the scheduling simulation. It encapsulates all necessary attributes and states required for scheduling and performance evaluation.

**Attributes:**

- `int pid`: Unique Process ID.
- `int arrivalTime`: The time at which the process arrives in the ready queue.
- `int burstTime`: Total CPU time required by the process.
- `int remainingTime`: CPU time remaining for the process to complete.
- `int completionTime`: The time at which the process completes execution.
- `int turnaroundTime`: Total time taken from arrival to completion.
- `int waitingTime`: Total time the process spends waiting in the ready queue.
- `int responseTime`: Time from arrival until the first time the process gets the CPU.
- `boolean isStarted`: Flag indicating whether the process has started execution.

**Methods:**

- **Constructor:**
  ```java
  public Process(int pid, int arrivalTime, int burstTime)
  ```
  Initializes a new process with the given PID, Arrival Time, and Burst Time.

- **Getters and Setters:**
  - `getPid()`, `getArrivalTime()`, `getBurstTime()`, `getRemainingTime()`, `getCompletionTime()`, `getTurnaroundTime()`, `getWaitingTime()`, `getResponseTime()`, `isStarted()`
  - `setRemainingTime(int remainingTime)`, `setCompletionTime(int completionTime)`, `setTurnaroundTime(int turnaroundTime)`, `setWaitingTime(int waitingTime)`, `setResponseTime(int responseTime)`, `setStarted(boolean started)`

- **toString():**
  ```java
  @Override
  public String toString()
  ```
  Returns a string representation of the process, including PID, Arrival Time, and Burst Time.

---

### Metrics

**File:** `src/Metrics.java`

**Description:**

The `Metrics` class encapsulates all performance evaluation metrics calculated after the scheduling simulation.

**Attributes:**

- `double averageTurnaroundTime`: The average turnaround time of all processes.
- `double averageWaitingTime`: The average waiting time of all processes.
- `double averageResponseTime`: The average response time of all processes.
- `double cpuUtilization`: The percentage of CPU time utilized during the simulation.
- `double throughput`: The number of processes completed per unit time.
- `int contextSwitches`: The total number of context switches that occurred during the simulation.

**Methods:**

- **Getters and Setters:**
  - `getAverageTurnaroundTime()`, `setAverageTurnaroundTime(double averageTurnaroundTime)`
  - `getAverageWaitingTime()`, `setAverageWaitingTime(double averageWaitingTime)`
  - `getAverageResponseTime()`, `setAverageResponseTime(double averageResponseTime)`
  - `getCpuUtilization()`, `setCpuUtilization(double cpuUtilization)`
  - `getThroughput()`, `setThroughput(double throughput)`
  - `getContextSwitches()`, `setContextSwitches(int contextSwitches)`

---

### Utils

**File:** `src/Utils.java`

**Description:**

The `Utils` class provides utility functions essential for the simulator's operation, such as reading input files and printing metrics.

**Methods:**

- **readProcesses:**
  ```java
  public static ArrayList<Process> readProcesses(String filePath)
  ```
  Reads processes from a CSV file located at the specified `filePath`. Each line in the CSV should follow the format: `ProcessID,ArrivalTime,BurstTime`. Returns a list of `Process` objects.

- **printMetrics:**
  ```java
  public static void printMetrics(Metrics metrics)
  ```
  Prints the performance metrics in a formatted and readable manner to the console.

**Usage Example:**
```java
ArrayList<Process> processes = Utils.readProcesses("input/processes.csv");
Metrics metrics = Scheduler.roundRobin(processes, 2);
Utils.printMetrics(metrics);
```

---

### Scheduler

**File:** `src/Scheduler.java`

**Description:**

The `Scheduler` class implements the Round Robin CPU scheduling algorithm. It manages the ready queue, handles context switches, and calculates performance metrics based on the simulation.

**Methods:**

- **roundRobin:**
  ```java
  public static Metrics roundRobin(ArrayList<Process> processList, int timeQuantum)
  ```
  Simulates the Round Robin scheduling algorithm on the provided `processList` using the specified `timeQuantum`. Returns a `Metrics` object containing all calculated performance metrics.

**Algorithm Steps:**

1. **Initialization:**
   - Sort processes based on Arrival Time.
   - Initialize a ready queue using a FIFO structure.
   - Set `currentTime`, `completed`, `contextSwitches`, and `cpuIdle` to zero.

2. **Simulation Loop:**
   - Continuously add processes to the ready queue as they arrive.
   - If the ready queue is empty, increment `currentTime` and `cpuIdle`.
   - Dequeue the next process from the ready queue.
   - If the process hasn't started, set its `responseTime`.
   - Execute the process for a time slice (`timeQuantum` or remaining burst time).
   - Update `currentTime`, `remainingTime`, and `contextSwitches`.
   - Re-enqueue the process if it still has remaining time.
   - Update process metrics upon completion.

3. **Metrics Calculation:**
   - Calculate averages for Turnaround Time, Waiting Time, and Response Time.
   - Compute CPU Utilization and Throughput.
   - Aggregate Context Switches.

**Usage Example:**
```java
Metrics metrics = Scheduler.roundRobin(processes, 2);
Utils.printMetrics(metrics);
```

---

### Main

**File:** `src/Main.java`

**Description:**

The `Main` class serves as the entry point of the application. It handles user input, initiates the scheduling simulation, and displays the results.

**Methods:**

- **main:**
  ```java
  public static void main(String[] args)
  ```
  - **Parameters:**
    - `args[0]`: Path to the processes CSV file.
    - `args[1]`: Time Quantum (integer).
  - **Functionality:**
    - Validates command-line arguments.
    - Parses the Time Quantum.
    - Reads processes from the specified CSV file.
    - Executes the Round Robin scheduler.
    - Prints the calculated performance metrics.

**Usage Example:**
```bash
java -cp bin Main input/processes.csv 2
```

---

### PriorityScheduler *(Extra Credit)*

**File:** `src/PriorityScheduler.java`

**Description:**

The `PriorityScheduler` class extends the basic Round Robin scheduler by incorporating process priorities. This allows higher-priority processes to be scheduled preferentially while maintaining the cyclic nature of Round Robin within the same priority level.

**Attributes:**

- *(Assuming the `Process` class is extended to include a `priority` attribute.)*

**Methods:**

- **priorityRoundRobin:**
  ```java
  public static Metrics priorityRoundRobin(ArrayList<Process> processList, int timeQuantum)
  ```
  Simulates the Priority Round Robin scheduling algorithm on the provided `processList` using the specified `timeQuantum`. Returns a `Metrics` object containing all calculated performance metrics.

**Implementation Highlights:**

- **Priority Handling:**
  - Processes are grouped based on their priority levels.
  - Higher-priority groups are scheduled before lower-priority ones.
  - Within each priority group, Round Robin scheduling is applied.

- **Ready Queue Management:**
  - Multiple queues may be used to separate processes by priority.
  - Alternatively, a single priority queue with priority-based ordering can be implemented.

**Usage Example:**
```java
Metrics metrics = PriorityScheduler.priorityRoundRobin(processes, 2);
Utils.printMetrics(metrics);
```

**Note:** Ensure that the `Process` class includes a `priority` attribute and that the input CSV includes priority data if implementing this feature.

---

## Installation

1. **Clone the Repository:**

   ```bash
   git clone https://github.com/yourusername/RoundRobinScheduler.git
   cd RoundRobinScheduler
   ```

2. **Compile the Java Source Files:**

   Ensure you have Java installed (Java 8 or above).

   ```bash
   javac -d bin src/*.java
   ```

   This command compiles all `.java` files in the `src/` directory and places the `.class` files in a new `bin/` directory.

---

## Usage

1. **Prepare the Input File:**

   Ensure your `processes.csv` is in the `input/` directory and follows the format:

   ```
   PID,ArrivalTime,BurstTime
   1,0,5
   2,1,3
   3,2,8
   4,3,6
   ```

   *If implementing Priority Scheduling, extend the format to include Priority:*

   ```
   PID,ArrivalTime,BurstTime,Priority
   1,0,5,2
   2,1,3,1
   3,2,8,3
   4,3,6,2
   ```

2. **Run the Program:**

   Navigate to the project directory and execute the program using the `java` command:

   ```bash
   java -cp bin Main input/processes.csv <time_quantum>
   ```

   Replace `<time_quantum>` with the desired time quantum value (integer).

   **Example:**

   ```bash
   java -cp bin Main input/processes.csv 2
   ```

3. **View the Output:**

   The program will display the performance metrics in the terminal.

   **Sample Output:**

   ```
   Average Turnaround Time: 10.50
   Average Waiting Time: 5.20
   Average Response Time: 4.00
   CPU Utilization: 95.00%
   Throughput: 0.95 processes/unit time
   Context Switches: 15
   ```

4. **Running with Priority Scheduling *(Extra Credit)*:**

   If implementing Priority Round Robin, execute the `PriorityScheduler` accordingly.

   ```bash
   java -cp bin PriorityMain input/processes_priority.csv 2
   ```

---

## Screenshots

After running the program with five different time quantum values, capture the terminal outputs as screenshots and save them in the `screenshots/` directory. Ensure each screenshot is named appropriately, such as `quantum_1.png`, `quantum_2.png`, etc.

*Example:*

![Quantum 2](screenshots/quantum_2.png)

---

## Extra Credit

### **Priority Round Robin Scheduling**

Students who wish to earn extra credit have implemented a combination of Priority and Round Robin scheduling algorithms. This extension allows processes to have assigned priorities, enabling the scheduler to prioritize higher-priority processes while maintaining the fairness of Round Robin scheduling within the same priority level.

**Key Enhancements:**

- **Priority Attribute:** The `Process` class includes a `priority` attribute to denote the importance of each process.
  
- **PriorityScheduler Class:** A new class `PriorityScheduler.java` manages the scheduling logic, ensuring that higher-priority processes are allocated CPU time before lower-priority ones.

- **Modified Scheduling Logic:** The scheduler first selects processes based on priority, and within each priority level, it applies the Round Robin algorithm using the specified time quantum.

**Benefits:**

- **Improved Responsiveness:** Critical processes with higher priorities receive CPU time more promptly.
  
- **Balanced Fairness:** While higher-priority processes are favored, lower-priority processes still receive CPU time in a fair and cyclic manner.

**Implementation Notes:**

- Ensure that the input CSV file includes a `Priority` column if implementing this feature.
  
- Modify the `Scheduler` class or create a separate `PriorityScheduler` class to handle priority-based queuing and execution.

**Usage Example:**

```bash
java -cp bin PriorityMain input/processes_priority.csv 2
```

**Sample Output:**

```
Average Turnaround Time: 9.80
Average Waiting Time: 4.50
Average Response Time: 3.80
CPU Utilization: 96.50%
Throughput: 1.05 processes/unit time
Context Switches: 12
```

---

## Analysis of Results

**Impact of Time Quantum Size:**

1. **Small Time Quantum (e.g., 1):**
   - **Advantages:**
     - Lower average waiting time.
     - Faster response time.
   - **Disadvantages:**
     - Higher number of context switches.
     - Increased CPU overhead due to frequent switching.
     - Lower CPU utilization.

2. **Large Time Quantum (e.g., 5):**
   - **Advantages:**
     - Fewer context switches.
     - Higher CPU utilization.
     - Reduced CPU overhead.
   - **Disadvantages:**
     - Higher average waiting time.
     - Longer response time.
     - Potentially less responsive system for interactive processes.

**CPU Utilization:**

As the Time Quantum increases, CPU utilization tends to improve due to fewer context switches. However, excessively large Time Quanta can lead to inefficient CPU usage if processes have short burst times.

**Throughput:**

Throughput generally increases with larger Time Quanta as the system spends less time switching contexts and more time executing processes.

**Priority Scheduling Impact:**

Incorporating priorities can significantly improve the responsiveness of critical processes without excessively penalizing lower-priority ones. This balanced approach enhances overall system efficiency and user satisfaction.

**Trade-offs:**

Selecting an optimal Time Quantum involves balancing responsiveness and CPU utilization. Additionally, integrating priority scheduling introduces another layer of complexity but offers greater control over process management.

---

## Conclusion

The Round Robin CPU Scheduling Simulator effectively demonstrates the principles and performance implications of the Round Robin algorithm. By allowing users to experiment with different Time Quantum values, the simulator provides valuable insights into how scheduling parameters influence system performance metrics. The optional Priority Round Robin extension further enhances the simulator's capabilities, offering a more nuanced approach to process management. This project not only reinforces theoretical concepts but also equips students with practical skills in algorithm implementation and performance analysis.

---

## References

- **YouTube Tutorial:** [Round Robin Scheduling Algorithm](https://www.youtube.com/watch?v=Sa600YsU16U)
- **Java Documentation:**
  - [Java Collections Framework](https://docs.oracle.com/javase/8/docs/technotes/guides/collections/overview.html)
  - [Java File I/O](https://docs.oracle.com/javase/tutorial/essential/io/)
- **CSV File Handling in Java:**
  - [OpenCSV Library](http://opencsv.sourceforge.net/)
  - [Using BufferedReader and String.split](https://docs.oracle.com/javase/tutorial/essential/io/char.html)
- **Operating Systems Textbook:** [Operating System Concepts by Abraham Silberschatz](https://www.wiley.com/en-us/Operating+System+Concepts%2C+10th+Edition-p-9781119456339)

---

## License

This project is licensed under the [MIT License](LICENSE).

```
