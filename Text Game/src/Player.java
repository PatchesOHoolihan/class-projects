import java.util.Scanner;
import java.util.ArrayList;

public class Player {
	private World world = new World();
	private Location current;
	private ArrayList<Item> inventory = new ArrayList<Item>();
	
	private int row, col;
	//private int floor;
	
	private int moveRow, moveCol;
	private String direction;			//Direction that the player moves modified by parse()
	
	//public static boolean openInv = false;	//Opens the inventory if true
	private boolean exit = false;	 	//Exits the program if true
	//public static boolean move = false;		//Told from parse() whether or not to attempt to move
	
	public Player(){
		row = 1;
		col = 1;
		//floor = 1;
		current = world.getLocation(1, 1);
		//This is only a placeholder for now.
	}
	
	public void act() {
		Parser parser = new Parser();
		Scanner input = new Scanner(System.in);
		
		System.out.println("\n" + current.getName());
		current.look();
		
		while(!exit) {
			System.out.print(">");
			parser.parse(input.nextLine(), this, current);
		}
		input.close();
	}
	
	public void openInventory() {
		if(inventory.size() != 0) 
			for(Item i: inventory) System.out.println("-" + i.getName());
		else System.out.println("There is nothing in your inventory.");
	}
	
	private boolean canMove() {
		if(row + moveRow >= 0 && row + moveRow < world.getHeight() && col + moveCol >= 0 && col + moveCol < world.getWidth())
			return (world.getLocation(row + moveRow, col + moveCol) != null);
		return false;
	}
	
	
	public void setDirection(String dir) {
		direction = dir;
		switch(direction) {
		case "north": moveRow = -1; moveCol = 0;
			break;
		case "east": case "right": moveRow = 0; moveCol = 1;
			break;
		case "west": case "left": moveRow = 0; moveCol = -1;
			break;
		case "south": moveRow = 1; moveCol = 0;
			break;
		default: moveRow = 0; moveCol = 0;
		}
	}
	
	public void quit() {exit = true;}
	
	public void move() {
		if(canMove()) {
			row += moveRow; 
			col += moveCol;
			current = world.getLocation(row, col);
			if(current.hasBeen() == false) current.look();
			else System.out.println(current.getName());
		}
		else System.out.println("You can't go that way.");
	}
	
	public void addItem(Item i) {
		inventory.add(i);
		System.out.println("Taken");
	}
	
	public Item getItem(String name) {
		for(Item i: inventory) {
			if(i.getName().contains(name)) return i;
		}
		return null;
	}
}
