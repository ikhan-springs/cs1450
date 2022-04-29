/* Ibrahim Khan
 * CS 1450-001
 * Assignment #8
 * 04/16/2020
 * This assignment creates two crossword objects using information from four files.  Two of these files 
 * contain the letters that go into the crossword and the other two files contain index locations of
 * where each letter is placed in the crossword.  One crossword uses ArrayLists and the other uses Queues.
 * The Iterator is also used when inserting letters into the crossword.
 */

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Iterator;

public class KhanIbrahimAssignment8 {

	public static void main(String[] args) throws IOException {
		
		//Setting up files and scanners for reading
		File listLettersFile = new File("listLetters.txt");
		File listSlotsFile = new File("listSlots.txt");
		File queueLettersFile = new File("queueLetters.txt");
		File queueSlotsFile = new File("queueSlots.txt");
		
		Scanner readListLetters = new Scanner(listLettersFile);
		Scanner readListSlots = new Scanner(listSlotsFile);
		Scanner readQueueLetters = new Scanner(queueLettersFile);
		Scanner readQueueSlots = new Scanner(queueSlotsFile);
		
		//New ArrayLists that will store data from first two files
		ArrayList <Character> letters = new ArrayList<>();
		ArrayList <Slot> slots = new ArrayList<>();
		
		//Adding letters into ArrayList
		while (readListLetters.hasNext()) {
			
			//String is needed because there is no Scanner method to obtain characters from files
			String newChar = readListLetters.next();
			letters.add(newChar.charAt(0));
		}
		
		//Variables that determine crossword size
		int listRows = readListSlots.nextInt();
		int listColumns = readListSlots.nextInt();
		
		//Adding slot locations into ArrayList
		while (readListSlots.hasNext()) {
			Slot newSlot = new Slot(readListSlots.nextInt(), readListSlots.nextInt());
			slots.add(newSlot);
		}
		
		readListLetters.close();
		readListSlots.close();
		
		//Crossword for ArrayList values
		Crossword listCrossword = new Crossword(listRows, listColumns);
		
		//Iterators for both ArrayLists
		Iterator <Character> lettersIterator = letters.iterator();
		Iterator <Slot> slotsIterator = slots.iterator();
		
		//Enter letters into crossword
		listCrossword.enterLetters(lettersIterator, slotsIterator);
		
		//Print crossword
		System.out.println("****************");
		System.out.println("Crossword #1");
		System.out.println("****************");
		listCrossword.printCrossword();
		
		//New queues that will store data from last two files
		Queue <Character> queueLetters = new LinkedList<>();
		Queue <Slot> queueSlots = new LinkedList<>();
		
		//Adding letters into queue
		while (readQueueLetters.hasNext()) {
			
			//String is needed because there is no Scanner method to obtain characters from files
			String newChar = readQueueLetters.next();
			queueLetters.offer(newChar.charAt(0));
		}
		
		//Variables that determine crossword size
		int queueRows = readQueueSlots.nextInt();
		int queueColumns = readQueueSlots.nextInt();
		
		//Adding slot locations into queue
		while (readQueueSlots.hasNext()) {
			Slot newSlot = new Slot(readQueueSlots.nextInt(), readQueueSlots.nextInt());
			queueSlots.offer(newSlot);
		}
		
		readQueueLetters.close();
		readQueueSlots.close();
		
		//Crossword for queue values
		Crossword queueCrossword = new Crossword(queueRows, queueColumns);
		
		//Iterators for both queues
		Iterator <Character> queueLettersIterator = queueLetters.iterator();
		Iterator <Slot> queueSlotsIterator = queueSlots.iterator();
		
		//Enter letters into crossword
		queueCrossword.enterLetters(queueLettersIterator, queueSlotsIterator);
		
		//Print crossword
		System.out.println("\n\n****************");
		System.out.println("Crossword #2");
		System.out.println("****************");
		queueCrossword.printCrossword();
		
	} //Main
	
} //Assignment8

class Slot {
	
	//Private data fields
	private int row;
	private int column;
	
	//Constructor
	public Slot(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	//Getters
	public int getRow() {
		return row;
	}
	public int getColumn() {
		return column;
	}
	
} //Slot

class Crossword {
	
	//Private data fields
	private char[][] array;
	private int numRows;
	private int numColumns;
	final private char EMPTY_CHARACTER = '_';
	
	//Constructor
	public Crossword(int numRows, int numColumns) {
		this.numRows = numRows;
		this.numColumns = numColumns;
		
		array = new char[numRows][numColumns];
		
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				array[i][j] = EMPTY_CHARACTER;
			}
		}
	}
	
	//Enters letters into its corresponding location in the crossword
	public void enterLetters(Iterator<Character> letterIterator, Iterator<Slot> slotIterator) {
		
		while (letterIterator.hasNext()) {
			
			char letter = letterIterator.next();
			Slot slot = slotIterator.next();
			
			int row = slot.getRow();
			int column = slot.getColumn();
			
			array[row][column] = letter;
		}
		
	}
	
	//Prints entire crossword
	public void printCrossword() {
		
		for (int i = 0; i < numRows; i++) {
			System.out.println();
			for (int j = 0; j < numColumns; j++) {
				System.out.print(array[i][j]+" ");
			}
		}
	}
	
} //Crossword