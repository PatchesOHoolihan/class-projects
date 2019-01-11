
public class Item {
	protected String name;
	private String placement; //This is printed when it is visible in the world

	public Item(String n, String p) {
		name = n;
		placement = p;
	}
	
	public String getName() {return name;}
	public String getPlacement() {return placement;}
	
	public boolean equals(Item other) {
		return(name.equals(other.getName()) && placement.equals(other.getPlacement()));
	}
	
	
}


