
public class Door extends Entity {
	protected boolean open;
	protected boolean locked;
	private String position;
	
	public Door(String n, String p, Item inter, boolean isLocked, boolean isOpen) {
		super(n, "", "", inter);
		open = isOpen;
		locked = isLocked;
		position = p;
		
		if(locked) setDescr("There is a locked door to the " + p);
		else if(!open) setDescr("There is a closed door to the " + p);
		else setDescr("There is an open door to the " + p);
	}
	
	public void open() {
		if(open) System.out.println("The door is already open");
		else 
			if(!locked) {
				open = true;
				System.out.println("You open the door.");
			}
			else System.out.println("The door is locked, so you cannot open it.");
	}
	
	public void close() {
		if(!open) System.out.println("The door is already closed.");
		else {
			open = false;
			setDescr("There is a closed door to the " + position);
			System.out.println("You close the door.");
		}
	}
	
	public void interact() {
		if(locked) {
			System.out.println("You unlock the door and open it.");
			setDescr("There is an open door to the " + position);
			locked = false;
			open = true;
		}
		else System.out.println("The door is already unlocked.");
	}
		
	public boolean isLocked() {return locked;}
	public boolean isOpen() {return open;}
	public String getPosition() {return position;}
	
}
