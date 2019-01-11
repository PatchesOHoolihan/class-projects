
public class World {
	private Location[][] world = new Location[3][3];
	
	public World() {
		//The locations will be hard-coded or imported using text documents in the future
		for(int i = 0; i < world.length; i++)
			for(int j = 0; j < world[i].length; j++)
				world[i][j] = null;
		
		Item oldKey = new Item("old key", "There is an old key hanging on the wall.");
		Item silverKey = new Item("silver key", "There is a silver key lying on the table.");
		world[1][1] = new Location ("Test Location", "This is a test location, so there is nothing here.");
		world[0][1] = new Location ("Test Location 2", "Congratulations! You know how to move around!");
		world[1][1].addDoor(new Door("door", "north", oldKey, true, false));
		world[0][1].addDoor(new Door("door", "south", null, false, true));
		world[0][1].addItem(new Item("rope", "There is a coil of rope in the center of the room."));
		world[1][1].addItem(oldKey);
		world[0][1].addItem(silverKey);
		world[0][1].setChest(new Chest("wooden chest", "There is a wooden chest next to the table.", "", silverKey, false, true));
	}
	
	public Location getLocation(int row, int col) {
		return world[row][col];
	}
	public int getHeight() {return world.length;}
	public int getWidth() {return world[0].length;}
}
