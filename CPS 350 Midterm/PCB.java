import java.util.*;
import java.io.PrintWriter;

public abstract class PCB {
	public static final int MAX_MEMORY = 512;
	public static int OPEN_MEMORY = 512;
	
	private static final String QUEUE_HEADER = 
		"Job #  Arr. Time  Mem. Req.  Run Time";
   private static final String IO_HEADER = 
      "IO Start Time  IO Burst  Comp. Time";
	private static final String FINISHED_HEADER = 
		"Start Time  Com. Time";
	public static Queue<Job> SCHEDULER, READY1, READY2, FINISHED;
	public static PriorityQueue<Job> IOWAIT;

	public static Event nextEvent;
	private static Scanner fileIn = null;
	private static PrintWriter fileOut = null;

	public static void init(Scanner fin, PrintWriter fout) {
		SCHEDULER = new LinkedList<Job>();
		READY1 = new LinkedList<Job>();
		READY2 = new LinkedList<Job>();
		FINISHED = new LinkedList<Job>();
		IOWAIT = new PriorityQueue<Job>(1, new Comparator<Job>() {
			public int compare(Job j1, Job j2) {
				return j1.IOcomp - j2.IOcomp;
			}
		});
		nextEvent = null;
		fileIn = fin;
		fileOut = fout;
	}

	public static Event getNextEvent() {
		if(nextEvent == null) {
			if(fileIn == null) 
				System.err.println("PCB ERROR: PCB not initialized.");
			else {
				if(fileIn.hasNext() && fileIn.hasNextLine()) 
					nextEvent = new Event(fileIn.next().charAt(0), fileIn.nextInt()); 
				else return null;
			}	
		}
		return nextEvent;
	}

	/*
	 *	Called when the next event to occur is a job arrival on the PCB
     *  (nextEvent is known to not be null and contain info on a process)
	 *  If a job exceeds max memory capacity, prints an error to the file.
	 */
	public static void schedule_job() {
		int jobNum = fileIn.nextInt();
		int arrival = nextEvent.occurTime;
		int memory = fileIn.nextInt();
		int burstTime = fileIn.nextInt();
		Job j = new Job(jobNum, arrival, memory, burstTime);
		
		nextEvent = null;

		if(memory > MAX_MEMORY) 
			fileOut.println("This job exceeds the system's main memory capacity.");
		else 			
			SCHEDULER.add(j);
	}
	/*
	 *	Pushes jobs onto the ready queue as long as there is memory available. 
	 */	
	public static void push_to_ready() {
		while(!SCHEDULER.isEmpty() && SCHEDULER.peek().memory <= OPEN_MEMORY) {
			Job j = SCHEDULER.poll();
			OPEN_MEMORY -= j.memory;
			j.waitTime = CPU.TIMER - j.arrivalTime;
			READY1.add(j);
		}
		CPU.take_job();
		
		//If a second-level job is on the CPU, preempt it for a first-level job
	}
	/*
	 *	Moves the top job from the IO Wait Queue to the ready queue
	 */
	public static void move_io_to_ready() {
		READY1.add(IOWAIT.poll());
      CPU.take_job();
	}

	public static void displayQueue(String queueTitle, Queue<Job> list) {
		String header = "The contents of the " + queueTitle.toUpperCase();
		fileOut.printf("\n%s\n%s\n\n", header, Simulator.generateLine(header));

		if(list.isEmpty()) 
			fileOut.printf("The %s is empty.\n\n", queueTitle);
		else {
			//Generate the header for displaying job info
			header = QUEUE_HEADER;
			if(list == FINISHED) header += "  " + FINISHED_HEADER;
         else if(list == IOWAIT) header += "  " + IO_HEADER;
			String lines = Simulator.generateLines(header.split("  "));
			fileOut.printf("%s\n%s\n\n", header, lines);		
	      if(list == IOWAIT) {
            Queue<Job> temp = new LinkedList<Job>();
            while(!IOWAIT.isEmpty())
               temp.add(IOWAIT.poll());
            for(Job j: temp) {   
				   fileOut.printf("% 5d  % 9d  % 9d  % 8d", 
					   j.jobNum, j.arrivalTime, j.memory, j.runtime);
               fileOut.printf("  % 13d  % 8d  % 10d\n", 
                  j.IOstart, j.IOburst, j.IOcomp);
            }
            while(!temp.isEmpty())
               IOWAIT.add(temp.poll());
         }
         else
			   for(Job j : list) {
				   fileOut.printf("% 5d  % 9d  % 9d  % 8d", 
					   j.jobNum, j.arrivalTime, j.memory, j.runtime);
				   if(list == FINISHED)
					   fileOut.printf("  % 10d  % 9d", j.startTime, j.finishTime);
			   fileOut.println();
			}
         fileOut.println();
		}	
	}
   
   public static double getAvgTurnaroundTime() {
      double sum = 0;
      for(Job j: FINISHED) 
         sum += (j.finishTime - j.arrivalTime);
      
      return sum/FINISHED.size();
   }
   
   public static double getAvgWaitTime() {
      double sum = 0;
      for(Job j: FINISHED)
         sum += j.waitTime;

      return sum/FINISHED.size();
   } 
}		
	
