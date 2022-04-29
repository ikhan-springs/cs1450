/* Ibrahim Khan
 * CS 1450-001
 * Assignment #9
 * 04/23/2020
 * This assignment deals with a singly linked list of Elephant objects.  Each elephant's 
 * information is obtained from a file, which is used to create new Elephants and are placed 
 * into the linked list.  Then the list is printed with each elephant's name and weight.
 * Afterwards, the program finds the total weight of all elephants.  Lastly, each elephant is
 * removed from the list by weight, heaviest to lightest.
 */

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class KhanIbrahimAssignment9 {

	public static void main(String[] args) throws IOException {
		
		//Creating new linked list to hold elephants
		ElephantLinkedList elephants = new ElephantLinkedList();
		
		//Setting up to read from file
		File elephantsFile = new File("elephants.txt");
		Scanner readElephants = new Scanner(elephantsFile);
		
		//Loop creates new Elephant objects and adds them to the linked list
		while (readElephants.hasNext()) {
			Elephant elephant = new Elephant(readElephants.next(), readElephants.nextInt());
			elephants.add(elephant);
		}
		
		//Printing entire linked list
		System.out.println("Step 1: Created and placed elephants in linked list\n");
		System.out.println("Elephant\tWeight");
		System.out.println("-------------------------");
		elephants.printList();
		
		//Printing total weight of all elephants
		System.out.println("\nStep 2: Find total weight for all elephants\n");
		System.out.printf("Total weight for all elephants is: %d lbs\n", elephants.getTotalWeight());
		
		//Printing removal of each elephant from linked list
		System.out.println("\nStep 3: Find and remove elephants, heaviest to lightest\n");
		
		//Loop runs until all elephants are removed
		while (!elephants.isEmpty()) {
			Elephant removingElephant = elephants.findHeaviest();
			System.out.printf("Removing: %s weighing in at %d lbs\n", removingElephant.getName(), removingElephant.getWeight());
			elephants.remove(removingElephant);
		}
		
	} //Main
	
} //Assignment9


class Elephant {
	
	//Private data fields
	private String name;
	private int weight;
	
	//Constructor
	public Elephant(String name, int weight) {
		this.name = name;
		this.weight = weight;
	}
	
	//Getters
	public String getName() {
		return name;
	}
	public int getWeight() {
		return weight;
	}
	
	//Print elephant's name and weight
	public void print() {
		System.out.printf("%s\t\t%d\n", name, weight);
	}
	
} //Elephant

class ElephantLinkedList {
	
	//Private data fields
	private Node head;
	private Node tail;
	
	//Constructor
	public ElephantLinkedList() {
		head = null;
		tail = null;
	}
	
	//Method checks if linked list is empty
	public boolean isEmpty() {
		
		//List is empty
		if (head == null) {
			return true;
		}
		//List is not empty
		else {
			return false;
		}
	}
	
	//Method returns total weight of all elephants in linked list
	public int getTotalWeight() {
		
		Node current = head; //Node to iterate through linked list
		int totalWeight = 0; //Variable that will be returned
		
		while (current != null) {
			totalWeight += current.elephant.getWeight();
			current = current.next;
		}
		
		return totalWeight;
	}
	
	//Method adds an elephant object to linked list
	public void add (Elephant elephant) {
		
		Node newNode = new Node(elephant);
		
		//Checking if adding first elephant to linked list
		if (this.isEmpty()) {
			head = tail = newNode;
		}
		else {
			tail.next = newNode;
			tail = newNode;
		}
	}
	
	//Method returns the heaviest elephant in linked list
	public Elephant findHeaviest() {
		
		Elephant heaviestElephant = head.elephant; //heaviestElephant initialized to first elephant in linked list
		int heaviestWeight = heaviestElephant.getWeight(); //Variable used to compare elephant weights
		Node current = head.next; //Node to iterate through linked list
		
		//Loop runs until all elephants have been compared
		while (current != null) {
			
			int compareWeight = current.elephant.getWeight(); //Other variable used to compare elephant weights
			
			//If heavier elephant is found
			if (compareWeight > heaviestWeight) {
				heaviestWeight = compareWeight;
				heaviestElephant = current.elephant;
			}
			
			current = current.next;
		}
		
		return heaviestElephant;
	}
	
	//Method removes specified elephant from linked list
	public void remove(Elephant elephant) {
		
		//If linked list is empty
		if (this.isEmpty()) {
			System.out.println("List is already empty");
		}
		//If there is only one elephant in linked list
		else if (head == tail) {
			head = null;
			tail = null;
		}
		//If elephant to be removed is the first Node
		else if (head.elephant == elephant) {
			head = head.next;
		}
		else {
			
			//Two nodes to track position in linked list
			Node previous = head;
			Node current = head.next;
			
			//Loop runs until elephant is removed or specified elephant is not found in linked list
			while (current != null) {
				
				//If specified elephant has been found
				if (current.elephant == elephant) {
					
					//Remove all references to elephant
					previous.next = current.next;
					
					//Relocate tail if removing the last elephant in linked list
					if (current == tail) {
						tail = previous;
					}
					
					current = null;
				}
				else {
					previous = current;
					current = current.next;
				}
			}
		}
	}
	
	//Method prints entire linked list using print() from Elephant class
	public void printList() {
		
		//Node to iterate through linked list
		Node current = head;
		
		while (current != null) {
			current.elephant.print();
			current = current.next;
		}
	}
	
	//Private inner class
	private static class Node {
		
		//Data fields
		Elephant elephant;
		Node next;
		
		//Constructor
		public Node(Elephant elephant) {
			this.elephant = elephant;
			next = null;
		}
		
	} //Node
	
} //ElephantLinkedList