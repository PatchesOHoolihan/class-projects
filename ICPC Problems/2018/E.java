import java.util.ArrayList;
import java.util.Scanner;

public class E {
	/*	
	 *	The Fraction class is used to store the ratings computed for each crossword clue.
	 *  This way, they can be compared to each other for the purposes of determining clue priority.
	 */
	private static class Fraction implements Comparable<Fraction> {
		int numerator, denominator;
		
		public Fraction(int n, int d) {
			numerator = n;
			denominator = d;
		}
		
		public int compareTo(Fraction f) {
			return (this.numerator * f.denominator - f.numerator * this.denominator);
		}
	}
	
	/*
	 *	The Clue class is used to store information about each clue in the puzzle, including its number,
	 *  direction (across or down), its starting position in the puzzle, and the length of the answer.
	 *  It is used to compare clues for the purpose of sorting them in the array list.
	 */
	public static class Clue implements Comparable<Clue> {
		int number, row, col, length;
		boolean across;
		Fraction rating;
		
		public Clue(boolean across, int num, int r, int c, int length, Fraction f) {
			number = num;
			this.across = across;
			row = r;
			col = c;
			rating = f;
			this.length = length;
		}
		
		public int compareTo(Clue c) {
			//First compare the clues based on their ratings
			int rateComp = this.rating.compareTo(c.rating);
			if(rateComp == 0) {		//If the two clues have equal ratings...
				if(this.rating.numerator == 0) return 0;
				if(this.across = r.across) {
					//If both clues go in the same direction, compare them by clue number
					return this.number - r.number;
				} else {
					//Prioritize across clues over down clues
					if(this.across) return -1;
					else return 1;
				}
			} 
			//Higher rating indicates higher priority (sorts to the left in the list)
			else return -rateComp;
		}
		
		public String toString() {
			if(across) return this.clueNumber + "A";
			else return this.clueNumber + "D";
		}
	}
	
	public static void main(String[] args) {
		Scanner cin = new Scanner(System.in);
		
		//Process first line to get puzzle size
		String[] input = cin.nextLine().split(" ");
		int r = Integer.parseInt(input[0]);
		int c = Integer.parseInt(input[1]);
		
		//Holds empty, filled and black squares in puzzle 
		//Initialized two larger than input size to create outer border of black squares
		char[][] puzzle = new char[r+2][c+2];
		//Holds the starting positions of each clue (non-zero value indicates starting point)
		int[][] acrossClues = new int[r+2][c+2];
		int[][] downClues = new int[r+2][c+2];
		
		//Used for holding unsolved clues, to be sorted based on solving priority
		ArrayList<Clue> unsolvedClues = new ArrayList<Clue>();
		
		//List of solved clues stored as [direction][clue number]
		//Direction mapped as across = 0, down = 1;
		boolean[][] solvedClues = new boolean[2][1000];
		
		int clueNum = 1; 	//The clue number to place next in the grid of clues
		
		//Fill in outside border of puzzle with black squares
		for(int i = 0; i < c+2; i++) {
			puzzle[0][i] = '#';
			puzzle[r+1][i] = '#';
		}
		for(int i = 0; i < r+2; i++) {
			puzzle[i][0] = '#';
			puzzle[i][c+1] = '#';
		}
		
		for(int i = 1; i <= r; i++) {
			String line = cin.nextLine();
			
			for(int j = 1; j <= c; j++) {
				puzzle[i][j] = line.charAt(j-1);
				//If the space just added to the puzzle is not a black space...
				if(puzzle[i][j] != '#') {
					boolean newClue = false;
					//If there is a black square to the left of the current spot in the grid
					//Create a new across clue
					if(puzzle[i][j-1] == '#') {
						acrossClues[i][j] = clueNum;
						newClue = true;
					}
					//If there is a black square above the current spot in the grid
					//Create a new down clue
					if(puzzle[i-1][j] == '#') {
						downClues[i][j] = clueNum;
						newClue = true;
					}
					//If a new clue was created at this spot, increment to the next clue number
					if(newClue) {
						clueNum++;
					}
				}
			}
		}
		//Repeatedly find all unsolved clues and sort them by solving priority
		//Must be re-computed each time because solving a clue will modify the ratings of other clues
		//Repeats until all clues in the puzzle are solved (no unsolved clues remain)
		do {
			//Clear the list of unsolved clues
			unsolvedClues = new ArrayList<Clue>();
			
			//Find all unsolved across clues
			for(int i = 1; i <= r; i++) {
				for(int j = 1; j <= c; j++) {
					//If a clue starts at space [i][j] and that clue is not solved...
					if(acrossClues[i][j] != 0 && !solvedClues[0][acrossClues[i][j]]) {
						boolean solved = true;
						int x = j;
						for(x = j; x <= c; x++) {
							//If there are empty spaces in the clue, that clue is not yet solved
							if(puzzle[i][x] == '.') solved = false;
							//If there is a black space, the end of the clue has been reached
							if(puzzle[i][x] == '#') break;
						}
						//Compute the length of the clue by the difference between the starting (x)
						//ending (j) positions
						int n = x - j;
						
						//If the clue is not solved, compute its rating and add it to the list of unsolved clues
						if(!solved) {
							int denom = 0;
							int rate = 0;
							int temp = n;
							//The denominator for each clue's rating is the sum of all values [1, 2...n]
							while(temp != 0) {
								denom += temp;
								temp--;
							}
							temp = n;
							//Clue spans n squares, with values assigned to each space [n, n-1...1] left to right
							//j point to the first space in the clue, 
							//y points to the kth space in the clue, where 1 <= k <= n
							//The value for the kth space is given as (n-k)+1
							for(int y = j; y < j + n; y++) {
								if(puzzle[i][y] != '.') {
									//If the kth space is not empty, add (n-k)+1 to the numerator
									rate += (n - y) + j;
								}
							}
							//Add the clue to the list of unsolved clues
							unsolvedClues.add(new Clue(true, acrossClues[i][j], i, j, n, new Fraction(rate, denom)));
						} else {
							//Mark the clue as solved
							solvedClues[0][acrossClues[i][j]] = true;
						}
					}
				}
			}
			
			//Find all unsolved down clues
			for(int i = 1; i <= r; i++) {
				for(int j = 1; j <= c; j++) {
					//If a clue starts at space [i][j] and that clue is not solved...
					if(downClues[i][j] != 0 && !solvedClues[1][downClues[i][j]]) {
						boolean solved = true;
						int x = i;
						for(x = i; x <= r; x++) {
							//If there are empty spaces in the clue, that clue is not yet solved
							if(puzzle[x][j] == '.') solved = false;
							//If there is a black space, the end of the clue has been reached
							if(puzzle[x][j] == '#') break;
						}
						//Compute the length of the clue by the difference between the starting (x)
						//ending (j) positions
						int n = x - i;
						
						//If the clue is not solved, compute its rating and add it to the list of unsolved clues
						if(!solved) {
							int denom = 0;
							int rate = 0;
							int temp = n;
							//The denominator for each clue's rating is the sum of all values [1, 2...n]
							while(temp != 0) {
								denom += temp;
								temp--;
							}
							temp = n;
							//Clue spans n squares, with values assigned to each space [n, n-1...1] top to bottom
							//i point to the first space in the clue, 
							//y points to the kth space in the clue, where 1 <= k <= n
							//The value for the kth space is given as (n-k)+1
							for(int y = i; y < i + n; y++) {
								if(puzzle[y][j] != '.') {
									//If the kth space is not empty, add (n-k)+1 to the numerator
									rate += (n - y) + i;
								}
							}
							//Add the clue to the list of unsolved clues
							unsolvedClues.add(new Clue(true, downClues[i][j], i, j, n, new Fraction(rate, denom)));
						} else {
							//Mark the clue as solved
							solvedClues[0][downClues[i][j]] = true;
						}
					}
				}
			}
			
			//If there are clues left to solved...
			if(!clueRatings.isEmpty()) {
				//Sort the list of clues in descending order by priority (handled by Clue compareTo)
				clueRatings.sort(null);
				
				//Get the first clue in the list, which will have the highest priority
				Clue clue = clueRatings.get(0);
				System.out.println(clue);
				int n = clue.length;
				//Fill in all of the clue's spaces with letters, indicating it has been solved
				//Spaces can be filled with arbitrary letters
				if(clue.across) {
					for(int i = clue.col; i < clue.col + n; i++) {
						puzzle[clue.row][i] = 'x';
					}
				} else {
					for(int i = clue.row; i < clue.row + n; i++) {
						puzzle[i][clue.col] = 'x';
					}
				}
			}
		
		//Repeat this process while there are still unsolved clues (a clue was solved in this iteration)
		} while (!clueRatings.isEmpty());
	}