
public class Entity {
	private Item interactsWith;
	
	private String interaction;
	private String description;
	private String name;

	public Entity(String n, String d, String i, Item inter) {
		description = d;
		name = n;
		interaction = i;
		interactsWith = inter;
	}
	
	public String getName() {return name;}
	public String getDescr() {return description;}
	
	public boolean canInteractWith(Item i) {
		return i.equals(interactsWith);
	}
	
	public void setInteractsWith(Item i) {
		interactsWith = i;
	}
	
	public void setInteraction(String s) {interaction = s;}
	
	public void interact(Item i) {
		System.out.println(interaction);
	}
	
	public void setDescr(String d) {description = d;}
}
