CPS 356 Midterm Project

Clark Annable


Programming Language: Java

Version: (OpenJDK) 1.8.0_151
	
	 (OpenJDK Runtime Environment) IcedTea 3.6.0 (build 1.8.0_151-4)


The following code was written, compiled and run on a UNIX system. The simulator reads from standard input and writes to standard output, so handling I/O for files should be done in the following format:
	> java Simulator < p2stdin_a.txt > myoutput_a.txt
	> java Simulator < p2stdin_b.txt > myoutput_b.txt

This code was written as the midterm project for my Operating Systems class
, and is meant to simulate job & process scheduling on a single-core CPU. The simulator is able to handle 6 types of events:
	1. Job Arrival (A)
	2. Display (D)
	3. Job Termination (T)
	4. Quantum Expire (E)
	5. I/O Request (I)
	6. I/O Completion (C)

Part of the grade for this project was based on how much the output created by the simulator matched the output provided (p2stdout_a, p2stdout_b), according to the diff command.

IMPORTANT NOTE: Due to issues with floating point numbers (float/double) and round-off in Java, average times given in program outputs may differ from the desired output by ~.001.  
