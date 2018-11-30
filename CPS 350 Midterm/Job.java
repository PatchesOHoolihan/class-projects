public class Job implements Comparable<Job> {
	int jobNum, arrivalTime, burstTime, memory;
	int startTime, finishTime, runtime, waitTime;
	int IOstart, IOburst, IOcomp;

	public Job(int j_num, int arr, int mem, int burst) {
		jobNum = j_num;
		arrivalTime = arr;
		memory = mem;
		burstTime = burst;
		runtime = burst;
		IOstart = 0; IOburst = 0; IOcomp = 0;
	}

	public int turnaroundTime() { return finishTime-arrivalTime; }
   
   public int compareTo(Job j) {
      return this.IOcomp - j.IOcomp;
   }
}	
