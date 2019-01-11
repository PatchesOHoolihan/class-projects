
public class TestDriver {

	public static void main(String[] args) {
		Player player = new Player();
		
		System.out.println("Welcome to the test game!"
						  +"\nThis is just to test the bare basics of the game engine"
						  +"\nas it goes throughout its development."
						  + "\nType 'help' to see all the commands that function in this version.");
		System.out.println("Current Version: Alpha 1.2");
		player.act();

	}

}
