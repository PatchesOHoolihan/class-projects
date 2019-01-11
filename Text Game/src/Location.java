import java.util.Scanner;
import java.util.ArrayList;

public class Location {
	private String name;
	private String description;
	private boolean been = false;
	
	private ArrayList<Item> itemsInWorld = new ArrayList<Item>();
	private ArrayList<Door> doors = new ArrayList<Door>();
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	
	private Chest chest;
	
	public Location(String n, String d){
		name = n;
		description = d;
		chest = null;
	}
	
	public boolean hasBeen() {return been;}
	public String getName() {return name;}
	
	public void look() {
		System.out.println(description);
		for(Item i: itemsInWorld)
			System.out.println(i.getPlacement());
		
		for(Entity e: entities)
			System.out.println(e.getDescr());
		
		for(Door d: doors)
			System.out.println(d.getDescr());
		
		if(chest != null) System.out.println(chest.getDescr());
	}
	
	public void removeItem(Item i) {
		itemsInWorld.remove(i);
	}
	
	public Entity findEnt(String name) {
		for(Entity e: entities) {
			if(e.getName().contains(name) && !Parser.PREP.contains(name)) return e;
		}
		return null;
	}
	
	public Item findItem(String name) {
		for(Item i: itemsInWorld) {
			if(i.getName().toLowerCase().contains(name) && !Parser.PREP.contains(name)) return i;
		}
		return null;
	}
	
	public Door findDoorAt(String direction) {
		for(Door door: doors) {
			if(door.getPosition().equals(direction)) return door;
		}
		return null;
	}
	
	public Chest getChest() {return chest;}
	
	public void printHelp() {
		System.out.println("\nList of commands\n"
						  +"\n-----------------------"
						  +"\nLook: View surroundings"
						  +"\nInventory: Shows items on player"
						  +"\nLoot chest: Loots the contents of a chest"
						  +"\nTake <Item>: Takes an item from where you are"
						  +"\nHelp: Displays help menu"
						  +"\nGo <direction> OR <direction>: moves player a direction"
						  +"\nOpen/Close <Direction> door: Opens the door found at a specific point."
						  +"\nOpen <Direction> door with <Item>: Attempts to open a door with a certain item"
						  +"\nQuit: Exits the game" 
						  +"\n--------------------"
						  +"\nDirections: North, South, East, West, Up, Down, Left, Right");
	}
	
	//Methods only used in the World constructor----------------------------
	
	public void addItem(Item i) {itemsInWorld.add(i);}
	public void addDoor(Door d) {doors.add(d);}
	public void setChest(Chest c) {chest = c;}
}
