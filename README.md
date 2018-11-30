# class-projects

---

# CPS 356 Midterm Project
**Programming Language**: Java  
**Version**: (OpenJDK) 1.8.0_151  

	 (OpenJDK Runtime Environment) IcedTea 3.6.0 (build 1.8.0_151-4)

### Program Execution
The following code was written, compiled and run on a Raspberry Pi, accessed using a PuTTY UNIX shell. The simulator reads from standard input and writes to standard output. For running the simulation with the provided inputs, the following commands should be run:  

    java Simulator < p2stdin_a.txt > myoutput_a.txt
    java Simulator < p2stdin_b.txt > myoutput_b.txt

### Summary
This code was written for the midterm project for CPS 350 - Operating Systems, and is designed to simulate job & process scheduling on a single-core CPU. Jobs are given an amount of memory in arbitrary units, with the maximum memory for the CPU (ready queue) set to 512. The simulator is able to handle 6 types of events:  

1. Job Arrival (A)
2. Display (D)
3. Job Termination (T)
4. Quantum Expire (E)
5. I/O Request (I)
6. I/O Completion (C)

Part of the grade for this project was based on how much the output created by the simulator matched the output provided (p2stdout_a, p2stdout_b), according to the diff command.

Input is given according to the following format:  

    A [Event time] [Job number] [Memory needed for job] [Time needed to process job]
    D [Event time] 
    I [Event time] [Time spent processing I/O request]

If a job requires more memory than the CPU's maximum, an error message is written to standard output, and the job is ignored.  
I/O requests always interrupt whatever job is currently on the CPU, pushing the job to the I/O Wait queue. 

### Results
The simulation's results are written to files *myoutput_a.txt* and *myoutput_b.txt* for given inputs *p2stdin_a.txt* and *p2stdin_b.txt* respectively. Output consists of event occurences and their times, and the contents produced by display events.

**IMPORTANT NOTE**: Due to issues with floating point numbers (float/double) and round-off in Java, average times given in program outputs may differ from the desired output by ~.001.  
