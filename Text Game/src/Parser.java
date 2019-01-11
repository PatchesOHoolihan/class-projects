import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Parser {
	
	public Parser() {}
	
	public static final ArrayList<String> ACTIONS = new ArrayList<String>(
			Arrays.asList("look", "open", "inventory", "use", "climb",
						  "take", "close", "examine", "turn", "quit", "help", "go"));
	
	public static final ArrayList<String> DIRECTIONS = new ArrayList<String>(
			Arrays.asList("north", "south", "east", "west",
						  "up", "down", "right", "left"));
	
	public static final ArrayList<String> PREP = new ArrayList<String>(
			Arrays.asList("on", "with"));
	
	public void parse(String str, Player p, Location loc) {
		Scanner find = new Scanner(str);
		int numWords = 0;
		
		for(int i = 0; i < str.length(); i++) 
			if(str.charAt(i) == ' ') numWords++;
		
		if(str.charAt(str.length()-1) != ' ') numWords++;
		
		String[] com = new String[numWords];
		
		for(int i = 0; i < com.length; i++) {
			com[i] = find.next().toLowerCase();
		}
		find.close();
		
		if(com.length != 0) {
		
		if(ACTIONS.contains(com[0])) {
			switch(com[0]) {
			
			case "look": if(com.length == 1) loc.look();
			else System.out.println("I don't know how to do that.");
			break;
			
			case "inventory": case "i": if(com.length == 1) p.openInventory();
			else System.out.println("I don't know how to do that.");
			break;
			
			case "help": case "h": if(com.length == 1) loc.printHelp(); 
			else System.out.println("I don't know how to do that.");
			break;
			
			case "quit": case "q": if(com.length == 1) p.quit();
			else System.out.println("I don't know how to do that.");
			break;
			
			
			}
		}
		}
	}
}
