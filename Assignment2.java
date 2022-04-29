/* Ibrahim Khan
 * CS 1450-001
 * Assignment #2
 * 02/06/2020
 * This assignment shows understanding of polymorphism and inheritance.  A superclass (Train) is
 * created which is inherited by four subclasses of different train types.  This program reads
 * from a file containing data about different trains, creates objects based on the data read,
 * and places the objects into a polymorphic Train array.  Afterwards, the program displays the
 * data in the array, along with the benefit of each train.
 */

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class KhanIbrahimAssignment2{

	public static void main(String[] args) throws IOException {
		
		File trainFile = new File("trains.txt");
		
		Scanner readFile = new Scanner(trainFile);
		
		int arrayLength = readFile.nextInt(); //First value in file is the length of the array
		Train[] trainArray = new Train[arrayLength]; //Value used to create array
		
		//for loop that reads from file, creates objects, and places objects into array
		for (int i = 0; i < arrayLength; i++) {
			
			//Local variables set with file contents
			String type = readFile.next();
			double topSpeed = readFile.nextDouble();
			String name = readFile.nextLine();
			
			//if statements look at train type to determine what object type is created
			//Then, the newly created object is placed into trainArray
			if (type.equalsIgnoreCase("Highspeed")) {
				
				Highspeed train = new Highspeed(name, topSpeed);
				trainArray[i] = train;
			}
			
			else if (type.equalsIgnoreCase("Monorail")) {
				
				Monorail train = new Monorail(name, topSpeed);
				trainArray[i] = train;
			}
			
			else if (type.equalsIgnoreCase("Lightrail")) {
				
				Lightrail train = new Lightrail(name, topSpeed);
				trainArray[i] = train;
			}
			
			else if (type.equalsIgnoreCase("Cog")) {
				
				Cog train = new Cog(name, topSpeed);
				trainArray[i] = train;
			}
		}
		
		readFile.close();
		
		//Display statements
		System.out.println("----------------------------------------------------------------"
				+ "-------------------------------------");
		System.out.println("Type\t\t Name\t\t\tTop Speed\tBenefit");
		System.out.println("----------------------------------------------------------------"
				+ "-------------------------------------");
		
		//for loop that iterates through trainArray and displays each object's information
		for (int i = 0; i < arrayLength; i++) {
			
			System.out.printf("%-8s\t%-16s\t%.2f\t\t%s\n", trainArray[i].getType(),
					trainArray[i].getName(), trainArray[i].getTopSpeed(),
					trainArray[i].benefit());
		}
		
	} // Main
	
} //Assignment2


class Train {
	
	//Private data fields
	private String type;
	private String name;
	private double topSpeed;
	
	//Default constructor - required since there are subclasses
	public Train() {
		
	}
	
	//Explicitly defined constructor
	public Train(String type, String name, double topSpeed) {
		
		this.type = type;
		this.name = name;
		this.topSpeed = topSpeed;
	}
	
	//Getters
	public String getType() {
		return type;
	}
	
	public String getName() {
		return name;
	}
	
	public double getTopSpeed() {
		return topSpeed;
	}
	
	//Benefit method
	public String benefit() {
		return "";
	}
	
} //Train

class Highspeed extends Train {
	
	//Explicitly defined constructor
	Highspeed(String name, double topSpeed) {
		super("Highspeed", name, topSpeed);
	}
	
	//Benefit method overridden
	public String benefit() {
		return "Travels at speeds between 125 and 267 mph";
	}
	
} //Highspeed

class Monorail extends Train {
	
	//Explicitly defined constructor
	Monorail(String name, double topSpeed) {
		super("Monorail", name, topSpeed);
	}
	
	//Benefit method overridden
	public String benefit() {
		return "Minimal footprint and quieter";
	}
	
} //Monorail

class Lightrail extends Train {
	
	//Explicitly defined constructor
	Lightrail(String name, double topSpeed) {
		super("Lightrail", name, topSpeed);
	}
	
	//Benefit method overridden
	public String benefit() {
		return "Tighter turning radius";
	}
	
} //Lightrail

class Cog extends Train {
	
	//Explicitly defined constructor
	Cog(String name, double topSpeed) {
		super("Cog", name, topSpeed);
	}
	
	//Benefit method overridden
	public String benefit() {
		return "Can climb grades up to 48%";
	}
	
} //Cog