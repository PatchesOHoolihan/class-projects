import java.util.Scanner;
import java.io.PrintWriter;

public class Simulator {
	private static Scanner readIn;
	private static PrintWriter writeOut;
	
	private static final String CPU_HEADER = 
		"The CPU  Start Time  CPU burst time left";
	public static void main(String[] args) {
		readIn = new Scanner(System.in);
      writeOut = new PrintWriter(System.out);
        
		PCB.init(readIn, writeOut);
		
		while(getNextEvent());
      
      displayFinal();

		writeOut.close();
		readIn.close();
	}
	/*
	 *	Finds the next event to occur - internal or external - and executes it
	 *	Returns true if there are still events to run, false otherwise
	 */ 		
	private static boolean getNextEvent() {
		Event pcbEvent = PCB.getNextEvent();
		Event cpuEvent = CPU.getNextEvent();
		if(cpuEvent == null && pcbEvent == null) return false;
	   
      //If both events occur at the same time, cpuEvent is resolved first
		if(cpuEvent != null && 
			(pcbEvent == null || cpuEvent.occurTime <= pcbEvent.occurTime)) {
			switch(cpuEvent.eventType) {
				case Event.TIME_EXPIRE: 
					CPU.expire_job();
					break;
				case Event.TERMINATE:
					CPU.terminate_job();
					PCB.push_to_ready(); //Run job scheduling after a job terminates
					break;
				case Event.IO_COMPLETE:
					int delta = cpuEvent.occurTime - CPU.TIMER;
					CPU.work_job(delta);
					PCB.move_io_to_ready();
					break;
			}
			writeOut.printf("Event: %c   Time: %d\n", 
				cpuEvent.eventType, cpuEvent.occurTime);
		}
      //Whenever an external event occurs, the CPU's timer must be updated 
      //(and whatever job the CPU is working on)
		if(pcbEvent != null && (cpuEvent == null || pcbEvent.occurTime <= cpuEvent.occurTime)) {
			writeOut.printf("Event: %c   Time: %d\n", pcbEvent.eventType, pcbEvent.occurTime);
         //Update the CPU's timer and current process
			int delta = pcbEvent.occurTime - CPU.TIMER;
			CPU.work_job(delta);

			switch(pcbEvent.eventType) {
				case Event.JOB_ARRIVAL: 
					PCB.schedule_job();
					PCB.push_to_ready(); 
               //Run job scheduling algorithm after a job arrives 
					break;
				case Event.DISPLAY:
					display();
					break;
				case Event.IO_REQUEST:
					CPU.interrupt_job(readIn.nextInt()); 
               //Move the current process onto the blocked queue
					break;
			}
			PCB.nextEvent = null;   
         //This is set to null so that the PCB will know to read in the 
         //next external event from standard input
		}
		return true;
	}
   /*
    * Displays all information about the simulation (queues, CPU, memory) 
    * at the current time (CPU.TIMER)
    */
	public static void display() {
		writeOut.println();
		for(int i = 0; i < 60; i++) writeOut.print("*");
		writeOut.println();
		writeOut.printf("\nThe status of the simulator at time %d.\n", CPU.TIMER);
		
		PCB.displayQueue("Job Scheduling Queue", PCB.SCHEDULER);
		PCB.displayQueue("First Level Ready Queue", PCB.READY1);
		PCB.displayQueue("Second Level Ready Queue", PCB.READY2);
		PCB.displayQueue("I/O Wait Queue", PCB.IOWAIT);
		
	   writeOut.println("\n"+CPU_HEADER);
		writeOut.println(generateLines(CPU_HEADER.split("  ")) + "\n");
		if(CPU.hasProcess()) {
			writeOut.printf("% 7d  % 10d  % 19d\n\n", 
			CPU.process.jobNum, CPU.process.startTime, CPU.process.burstTime);
		}
		else
			writeOut.println("The CPU is idle.\n");	
		PCB.displayQueue("Finished List", PCB.FINISHED);
			
		writeOut.printf("\nThere are %d blocks of main memory available in the system.\n\n", PCB.OPEN_MEMORY);
	}
   
   /*
    * Displays the final finished list and average turnaround and wait times. 
    * Called once no more events will be run through the simulation.
    */
   private static void displayFinal() {
      PCB.displayQueue("Final Finished List", PCB.FINISHED);
      writeOut.printf("\nThe Average Turnaround Time for the simulation was %.3f units.\n", PCB.getAvgTurnaroundTime());
      writeOut.printf("\nThe Average Job Scheduling Wait Time for the simulation was %.3f units.\n", PCB.getAvgWaitTime());
      writeOut.printf("\nThere are %d blocks of main memory available in the system.\n\n", PCB.OPEN_MEMORY);
   }
   /*
    * Given a string, returns a string of '-' characters of equal length
	 */
	public static String generateLine(String str) {
		String line = "";
		for(int i = 0; i < str.length(); i++) line+="-";
		return line;
	}	
   /*
    * Given an array of strings, returns a sequence of generateLine calls
    * Lines are separated by two spaces, except for the last one.
    */
	public static String generateLines(String[] items) {
		String result = "";
		for(int i = 0; i < items.length; i++) {
			result += generateLine(items[i]);
			if(i != items.length-1) 
				result += "  ";
		}
		return result;
	}
}			
