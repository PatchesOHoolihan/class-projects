import java.util.ArrayList;

public class Chest extends Door{
	
	private ArrayList<Item> contents = new ArrayList<Item>();
	
	public Chest(String n, String d, String inter, Item i, boolean isOpen, boolean isLocked) {
		super(n, "", i, isOpen, isLocked);
		
		setDescr(d);
		setInteraction(inter);
		locked = isLocked;
		open = isOpen;
	}
	
	public void addItem(Item i) {contents.add(i);}
	
	public void loot(Player p) {
		for(Item i: contents) {
			p.addItem(i);
			contents.remove(i);
		}
		System.out.println("You loot the chest of its contents.");
	}
	
	public void interact() {
		if(locked) {
			System.out.println("You unlock and open the " + getName());
			locked = false;
			open = true;
		}
		else System.out.println("It is already unlocked.");
	}
}
