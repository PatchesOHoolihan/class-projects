public class Event {
	public static final char JOB_ARRIVAL = 'A', DISPLAY = 'D', IO_REQUEST = 'I',						  TIME_EXPIRE = 'E', TERMINATE = 'T', IO_COMPLETE = 'C';

	public char eventType;
	public int occurTime;
		
	public Event(char type, int time) {
		eventType = type;
		occurTime = time;
	}
}
