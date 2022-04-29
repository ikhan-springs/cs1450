/* Ibrahim Khan
 * CS 1450-001
 * Assignment #4
 * 02/21/2020
 * This program creates a pinball machine using 2-dimensional arrays.  The 2D array is set up
 * by reading from a file, which contains details about the targets in the pinball machine.
 * Afterwards, the machine is played using another file that details where the ball travels.
 * The program also displays the layout of the pinball machine (i.e. array values), a simple
 * simulation of the game, and a report showing how many times each target was hit.
 */

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class KhanIbrahimAssignment4 {

	public static void main(String[] args) throws IOException {
		
		//Setting up to read from file
		File targetsFile = new File("PinballMachineTargets.txt");
		Scanner readFile = new Scanner(targetsFile);
		
		//Obtaining values for creation of pinball array
		final int ROWS = readFile.nextInt();
		final int COLUMNS = readFile.nextInt();
		
		//Creating a new pinball machine
		PinballMachine pinballMachine = new PinballMachine(ROWS, COLUMNS);
		
		System.out.println("Set up targets in pinball machine...\n");
		
		//while loop that runs until all values in file are read
		while (readFile.hasNext()) {
			
			//Initializing values from file
			int rowIndex = readFile.nextInt();
			int columnIndex = readFile.nextInt();
			String type = readFile.next();
			int id = readFile.nextInt();
			int points = readFile.nextInt();
			
			//Using file values to create and initialize new target
			Target newTarget = new Target(type, id, points);
			
			//Method invoked to add target to pinball machine
			pinballMachine.addTargetToPlayingField(rowIndex, columnIndex, newTarget);
		}
		
		pinballMachine.displayPlayingField(); //Displays targets in the pinball machine
		play(pinballMachine); //Simulates a game and displays the simulation
		printReport(pinballMachine); //Prints report of number of hits on targets
		
	} //Main
	
	//Method simulates one game by reading from a file that shows where ball travels
	public static void play(PinballMachine pinballMachine) throws IOException {
		
		System.out.println("\n\n------------------------------------------------------");
		System.out.println("\t\tSimulate Pinball Game");
		System.out.println("------------------------------------------------------");
		System.out.println("Target Hit\tID\tPoints\t\tScore");
		System.out.println("------------------------------------------------------");
		
		//Setting up to read from file
		File playFile = new File("Play.txt");
		Scanner readFile = new Scanner(playFile);
		
		int score = 0; //Initializing score variable that will keep track of total score
		
		////while loop that runs until all values in file are read
		while (readFile.hasNext()) {
			
			//Initializing values from file
			int rowIndex = readFile.nextInt();
			int columnIndex = readFile.nextInt();
			Target currentTarget = pinballMachine.getTarget(rowIndex, columnIndex); 
			
			//if statement runs when there a target is hit
			if (currentTarget != null) {
				
				currentTarget.incrementHits(); //number of hits incremented
				score += currentTarget.getPoints(); //score updated
				System.out.printf("%-8s\t%d\t%d\t\t%d\n", currentTarget.getType(), 
						currentTarget.getId(), currentTarget.getPoints(), score);
			}
		}
		
	} //play
	
	//Method prints a report for pinball machine 
	public static void printReport(PinballMachine pinballMachine) {
		
		System.out.println("\n\n************************************************************");
		System.out.println("\t\tPINBALL MACHINE TARGET HIT REPORT");
		System.out.println("\t\t (From Most Hits to Least Hits)");
		System.out.println("************************************************************");
		System.out.println("Row\tColumn\tType\t\tID\tPoints\tNumber Hits");
		System.out.println("------------------------------------------------------------");
		
		//Creating new ArrayList to store reports of each target
		ArrayList<TargetReport> targetReports = new ArrayList<>();
		
		//for loops to iterate through the 2D array
		for (int i = 0; i < pinballMachine.getNumberRows(); i++) {
			for (int j = 0; j < pinballMachine.getNumberColumns(); j++) {
				
				//if statement runs when a target is at the specified location
				if (pinballMachine.getTarget(i, j) != null) {
					
					//New report created for the target
					TargetReport newTargetReport = new TargetReport(i, j, 
							pinballMachine.getTarget(i, j).getType(), 
							pinballMachine.getTarget(i, j).getId(), 
							pinballMachine.getTarget(i, j).getPoints(), 
							pinballMachine.getTarget(i, j).getHits());
					targetReports.add(newTargetReport); //Report added to ArrayList
				}
			}
		}
		
		Collections.sort(targetReports); //All reports sorted by number of hits
		
		//for loop to print each report
		for (int i = 0; i < targetReports.size(); i++) {
			System.out.println(targetReports.get(i).print());
		}
		
	} //printReport
	
} //Assignment4


class PinballMachine {
	
	//Private data fields
	private int numberRows;
	private int numberColumns;
	private Target[][] playingField;
	
	//Constructor
	public PinballMachine(int numberRows, int numberColumns) {
		this.numberRows = numberRows;
		this.numberColumns = numberColumns;
		playingField = new Target[numberRows][numberColumns]; //2D array created
	}
	
	//Getters
	public int getNumberRows() {
		return numberRows;
	}	
	public int getNumberColumns() {
		return numberColumns;
	}
	
	//Method that adds a target to the pinball machine at a specific location
	public void addTargetToPlayingField(int row, int column, Target target) {
		playingField[row][column] = target;
	}
	
	//Getter for a target at a location in the array
	public Target getTarget(int row, int column) {
		return playingField[row][column];
	}
	
	//Method that displays all targets in pinball machine in a table
	public void displayPlayingField() {
		
		System.out.print("\t");
		
		//For loop to print top labels of table
		for (int i = 0; i < numberColumns; i++) {
			System.out.printf("Column %d\t", i);
		}
		
		for (int i = 0; i < numberRows; i++) {
			System.out.printf("\nRow %d\t", i); //Side labels of table
			
			for (int j = 0; j < numberColumns; j++) {
				
				//if statement checks if there is a target at the specified location
				if (playingField[i][j] == null) {
					System.out.print("-------\t\t");
				}
				else {
					System.out.printf("%-8s\t", playingField[i][j].getType());
				}
			}
		}
	}
	
} //PinballMachine

class Target {
	
	//Private data fields
	private String type;
	private int id;
	private int points;
	private int hits;
	
	//Constructor
	public Target(String type, int id, int points) {
		this.type = type;
		this.id = id;
		this.points = points;
	}
	
	//Getters
	public String getType() {
		return type;
	}
	public int getId() {
		return id;
	}
	public int getPoints() {
		return points;
	}
	public int getHits() {
		return hits;
	}
	
	//Method that increments number of hits by 1
	public void incrementHits() {
		hits++;
	}
	
} //Target

class TargetReport implements Comparable<TargetReport> {
	
	//Private data fields
	private int rowNumber;
	private int columnNumber;
	private String type;
	private int id;
	private int points;
	private int hits;
	
	//Constructor
	public TargetReport(int rowNumber, int columnNumber, String type, int id, int points, 
			int hits) {
		this.rowNumber = rowNumber;
		this.columnNumber = columnNumber;
		this.type = type;
		this.id = id;
		this.points = points;
		this.hits = hits;
	}
	
	//Method that returns formatted report
	public String print() {
		return String.format("%d\t%d\t%-10s\t%-1d\t%-4d\t%-4d", rowNumber, columnNumber, 
				type, id, points, hits);
	}
	
	@Override
	//Method used to sort reports from most hits to least hits
	public int compareTo(TargetReport otherReport) {
		
		if (hits < otherReport.hits) {
			return 1;
		}
		else if (hits > otherReport.hits) {
			return -1;
		}
		else {
			return 0;
		}
	}
	
} //TargetReport