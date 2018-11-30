public abstract class CPU {
	public static int TIMER = 0;
	
	public  static Job process = null;
	public static int quantum, workTime;

	public static boolean hasProcess() { return process != null; }
	
	public static void take_job() {
      if(!hasProcess()) {
		   if(PCB.READY1.isEmpty()) {
			   if(!PCB.READY2.isEmpty()) {
				   process = PCB.READY2.poll();
				   quantum = 300;
			   }
		   }	
		   else {
			   process = PCB.READY1.poll();
			   quantum = 100;
		   }
		   workTime = quantum;
		   if(process != null && process.startTime == 0)
			   process.startTime = TIMER;
      }
      else if(quantum == 300 && !PCB.READY1.isEmpty()) 
         preempt_job();
	}
	/*
	 * Works on the current process for the given amount of time.
	 * If there is no process on the CPU, the timer is still updated.
	 */
	public static void work_job(int time) {
		if(hasProcess()) {
			workTime -= time;
			process.burstTime -= time;
		}
		TIMER += time;
	}
	
	public static void expire_job() {
		work_job(workTime);
		PCB.READY2.add(process);
      process = null;
		take_job();
	}
	/*
	 *	Preempts the current process on the CPU for a first-level job
	 *  process is known to be from the second level ready queue
	 *  Work has already been done from the simulator up to the current time
	 */
	public static void preempt_job() {
		PCB.READY2.add(process);
      process = null;
		take_job();
	}
	
	public static void terminate_job() {
		work_job(process.burstTime);
		process.finishTime = TIMER;
		PCB.FINISHED.add(process);
		PCB.OPEN_MEMORY += process.memory;
		process = null;
	}
	/*
	 * Interrupts the current process with a request for IO.
	 * The current process is put onto the IO Wait Queue.
	 */
	public static void interrupt_job(int ioBurstTime) {
		process.IOburst = ioBurstTime;
		process.IOstart = TIMER;
		process.IOcomp = ioBurstTime + TIMER;	//Time IO scheduled to finish
		PCB.IOWAIT.add(process);
		process = null;
		take_job();
	}
	/*
	 *	Returns the next internal event to occur in the simulation and its time
	 *  Will return either termination (T), expire (E), or IO completion (C)
	 */
	public static Event getNextEvent() {
		boolean hasIO = !PCB.IOWAIT.isEmpty();
		int nextIOComp = 0;
		if(hasIO) nextIOComp = PCB.IOWAIT.peek().IOcomp;
		if(process == null) 
			if(!hasIO)return null;
			else return new Event(Event.IO_COMPLETE, nextIOComp);
		if(process.burstTime > workTime) 
			if(hasIO && nextIOComp < TIMER+workTime) 
				return new Event(Event.IO_COMPLETE, nextIOComp);
			else
				return new Event(Event.TIME_EXPIRE, TIMER+workTime);
		else {
			if(hasIO && nextIOComp < TIMER+process.burstTime) 
				return new Event(Event.IO_COMPLETE, nextIOComp);
			else
				return new Event(Event.TERMINATE, TIMER+process.burstTime);
		}
	}
}
		
