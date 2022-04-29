/* Ibrahim Khan
 * CS 1450-001
 * Assignment #3
 * 02/13/2020
 * This assignment introduces abstract classes and methods as well as interfaces.  The program
 * reads from a file to place Insect objects into a polymorphic array.  Then, ArrayLists are
 * used to see which of these objects decompose and which object has the most abilities.  The
 * information found is then displayed on the console.
 */

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class KhanIbrahimAssignment3 {

	public static void main(String[] args) throws IOException {
		
		//Getting file ready to be read from
		File insectFile = new File("insects.txt");
		Scanner readFile = new Scanner(insectFile);
		
		int arrayLength = readFile.nextInt(); //Obtain array length from file
		Insect[] insectArray = new Insect[arrayLength]; //Create array using array length
		
		//for loop that places Insect objects into array
		for (int i = 0; i < insectArray.length; i++) {
			
			//Variables initialized to information in file
			String type = readFile.next();
			String name = readFile.next();
			int pollinateAbility = readFile.nextInt();
			int buildAbility = readFile.nextInt();
			int predatorAbility = readFile.nextInt();
			int decomposeAbility = readFile.nextInt();
			
			//switch statement that creates new Insect objects based on each subclass
			//The objects are then placed into the array
			switch (type) {
				case "h": Honeybee honeybee = new Honeybee(name, pollinateAbility, buildAbility);
						  insectArray[i] = honeybee; break;
				case "l": Ladybug ladybug = new Ladybug(name, pollinateAbility, predatorAbility);
						  insectArray[i] = ladybug; break;
				case "a": Ant ant = new Ant(name, buildAbility, predatorAbility, decomposeAbility);
						  insectArray[i] = ant; break;
				case "p": PrayingMantis prayingMantis = new PrayingMantis(name, predatorAbility);
						  insectArray[i] = prayingMantis; break;
			}
			
		}
		
		//ArrayList created to store all insects that do not decompose
		ArrayList<Insect> nonDecomposers = findDoNotDecompose(insectArray);
		
		System.out.println("INSECTS THAT DON'T HELP WITH DECOMPOSITION!");
		System.out.println("--------------------------------------------");
		
		//for loop that prints information of each object in ArrayList
		for (int i = 0; i < nonDecomposers.size(); i++) {
			
			System.out.printf("%s is a %s and does not help with decomposition\n", 
					nonDecomposers.get(i).getName(), nonDecomposers.get(i).getType());
			
			displayAbilities(nonDecomposers.get(i));
		}
		
		//Integer created to store the index of insect with most abilities
		int mostAbleIndex = findMostAble(insectArray);
		
		System.out.println("INSECT WITH MOST ABILITIES!");
		System.out.println("----------------------------");
		
		System.out.printf("The winner is %s the %s\n", insectArray[mostAbleIndex].getName(), 
				insectArray[mostAbleIndex].getType());
		
		displayAbilities(insectArray[mostAbleIndex]);
		
	} //Main
	
	//Method that stores all insects that do not decompose into an ArrayList
	public static ArrayList<Insect> findDoNotDecompose(Insect[] insects) {
		
		//New ArrayList created
		ArrayList<Insect> nonDecomposers = new ArrayList<>();
		
		//for loop that checks if insect is a decomposer or not
		for (int i = 0; i < insects.length; i++) {
			
			//Ants are the only decomposers
			if (insects[i] instanceof Ant) {
				
			}
			//All other insects do not decompose and are placed into ArrayList
			else {
				nonDecomposers.add(insects[i]);
			}
		}
		
		return nonDecomposers;
		
	} //findDoNotDecompose
	
	//Method that returns the index of the insect with most abilities
	public static int findMostAble(Insect[] insects) {
		
		int mostAbleIndex = 0; //Variable that will be returned
		int mostAbleAbilities = 0; //Variable to keep track of insect with most abilities
		
		//for loop that runs through array to find insect with most abilities
		for (int i = 0; i < insects.length; i++) {
			
			int compareAbilities = 0; //Variable created to compare with mostAbleAbilities
			
			//if statements for each subclass of Insect
			//compareAbilties is set to the abilities of current iterated Insect
			if (insects[i] instanceof Honeybee) {
				compareAbilities = ((Honeybee) insects[i]).pollinate() + 
						((Honeybee) insects[i]).build();
			}
			else if (insects[i] instanceof Ladybug) {
				compareAbilities = ((Ladybug) insects[i]).pollinate() + 
						((Ladybug) insects[i]).predator();
			}
			else if (insects[i] instanceof Ant) {
				compareAbilities = ((Ant) insects[i]).build() + 
						((Ant) insects[i]).predator() + ((Ant) insects[i]).decompose();
			}
			else if (insects[i] instanceof PrayingMantis) {
				compareAbilities = ((PrayingMantis) insects[i]).predator();
			}
			
			//if statement that runs if current iterated insect is most able
			if (compareAbilities > mostAbleAbilities) {
				mostAbleAbilities = compareAbilities;
				mostAbleIndex = i;
			}
		}
		
		return mostAbleIndex;
		
	} //findMostAble
	
	public static void displayAbilities(Insect insect) {
		
		//if statements that display abilities depending on the subclass of Insect
		if (insect instanceof Honeybee) {
			System.out.println(((Honeybee) insect).purpose());
			System.out.println("Pollinating Ability "+((Honeybee) insect).pollinate());
			System.out.println("Building Ability "+((Honeybee) insect).build()+"\n");
		}
		else if (insect instanceof Ladybug) {
			System.out.println(((Ladybug) insect).purpose());
			System.out.println("Pollinating Ability "+((Ladybug) insect).pollinate());
			System.out.println("Predator Ability "+((Ladybug) insect).predator()+"\n");
		}
		else if (insect instanceof Ant) {
			System.out.println(((Ant) insect).purpose());
			System.out.println("Building Ability "+((Ant) insect).build());
			System.out.println("Predator Ability "+((Ant) insect).predator());
			System.out.println("Decomposing Ability "+((Ant) insect).decompose()+"\n");
		}
		else if (insect instanceof PrayingMantis) {
			System.out.println(((PrayingMantis) insect).purpose());
			System.out.println("Predator Ability "+((PrayingMantis) insect).predator()+"\n");
		}
		
	} //displayAbilities
	
} //Assignment3


interface Pollinator {
	
	public abstract int pollinate();
	
} //Pollinator

interface Builder {
	
	public abstract int build();
	
} //Builder

interface Predator {
	
	public abstract int predator();
	
} //Predator

interface Decomposer {
	
	public abstract int decompose();
	
} //Decomposer


abstract class Insect {
	
	//Private data fields
	private String type;
	private String name;
	
	//Getters
	public String getType() {
		return type;
	}
	public String getName() {
		return name;
	}
	
	//Setters
	public void setType(String type) {
		this.type = type;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public abstract String purpose();
	
} //Insect


class Honeybee extends Insect implements Pollinator, Builder {
	
	//Private data fields
	private int pollinateAbility;
	private int buildAbility;
	
	//Constructor
	public Honeybee(String name, int pollinateAbility, int buildAbility) {
		
		setType("Honeybee");
		setName(name);
		this.pollinateAbility = pollinateAbility;
		this.buildAbility = buildAbility;
	}
	
	@Override
	public int pollinate() {
		return pollinateAbility;
	}
	
	@Override
	public int build() {
		return buildAbility;
	}
	
	@Override
	public String purpose() {
		return "I'm popular for producing honey but I also pollinate 35% of the crops! Without "
				+ "me, 1/3 of the food you eat would not be available!";
	}
	
} //Honeybee

class Ladybug extends Insect implements Pollinator, Predator {
	
	//Private data fields
	private int pollinateAbility;
	private int predatorAbility;
	
	//Constructor
	public Ladybug(String name, int pollinateAbility, int predatorAbility) {
		
		setType("Ladybug");
		setName(name);
		this.pollinateAbility = pollinateAbility;
		this.predatorAbility = predatorAbility;
	}

	@Override
	public int pollinate() {
		return pollinateAbility;
	}
	
	@Override
	public int predator() {
		return predatorAbility;
	}
	
	@Override
	public String purpose() {
		return "Named after the Virgin Mary, I'm considered good luck if I land on you! I'm a pest"
				+ " control expert eating up to 5,000 plant pests during my life span.";
	}
	
} //Ladybug

class Ant extends Insect implements Builder, Predator, Decomposer {
	
	//Private data fields
	private int buildAbility;
	private int predatorAbility;
	private int decomposeAbility;
	
	//Constructor
	public Ant(String name, int buildAbility, int predatorAbility, int decomposeAbility) {
		
		setType("Ant");
		setName(name);
		this.buildAbility = buildAbility;
		this.predatorAbility = predatorAbility;
		this.decomposeAbility = decomposeAbility;
	}
	
	@Override
	public int build() {
		return buildAbility;
	}
	
	@Override
	public int predator() {
		return predatorAbility;
	}
	
	@Override
	public int decompose() {
		return decomposeAbility;
	}
	
	@Override
	public String purpose() {
		return "Don't squash me, I'm an ecosystem engineer! Me and my 20 million friends accelerate"
				+ " decomposition of dead wood, aerate soil, improve drainage, and eat insects like"
				+ " ticks and termites!";
	}
	
} //Ant

class PrayingMantis extends Insect implements Predator {
	
	//Private data field
	private int predatorAbility;
	
	//Constructor
	public PrayingMantis(String name, int predatorAbility) {
		
		setType("Praying Mantis");
		setName(name);
		this.predatorAbility = predatorAbility;
	}
	
	@Override
	public int predator() {
		return predatorAbility;
	}
	
	@Override
	public String purpose() {
		return "I'm an extreme predator quick enough to catch a fly. Release me in a garden and I'll "
				+ "eat beetles, grasshoppers, crickets and even pesky moths.";
	}
	
} //PrayingMantis