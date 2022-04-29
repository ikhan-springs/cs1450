/* Ibrahim Khan
 * CS 1450-001
 * Assignment #1
 * 01/30/2020
 * This assignment deals with arrays of random sizes and values, and files. First, this program creates two
 * arrays of random sizes and fills in random values. Then, the arrays are sorted and the values are written
 * to a file in order, from smallest to greatest. Lastly, the file is re-opened and the program obtains the 
 * values and displays them without any duplicates.
 */

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Arrays;

public class KhanIbrahimAssignment1 {

	public static void main(String[] args) throws IOException {
		
		//Equations to find the size of the arrays
		int size1 = (int) (2 + Math.random() * 8);
		int size2 = (int) (2 + Math.random() * 8);
		
		System.out.println("size1 = "+size1);
		System.out.println("size2 = "+size2);
		
		//Creating the two arrays
		int[] array1 = new int[size1];
		int[] array2 = new int[size2];
		
		//Two for loops that fills in the arrays with random values
		for (int i = 0; i < size1; i++) {
			array1[i] = (int) (2 + Math.random() * 23);
		}
		for (int i = 0; i < size2; i++) {
			array2[i] = (int) (2 + Math.random() * 23);
		}
		
		//Arrays are sorted
		Arrays.sort(array1);
		Arrays.sort(array2);
		
		//Displaying values in first array
		System.out.println("\nFirst array with "+size1+" sorted random values");
		System.out.println("-----------------------------------------------");
		
		for (int i = 0; i < size1; i++) {
			System.out.println(array1[i]);
		}
		
		//Displaying values in second array
		System.out.println("\nSecond array with "+size2+" sorted random values");
		System.out.println("------------------------------------------------");
		
		for (int i = 0; i < size2; i++) {
			System.out.println(array2[i]);
		}
		
		//New file created
		File arrayFile = new File("Assignment1.txt");
		
		//New PrintWriter created to print values from array 
		PrintWriter valuesFile = new PrintWriter(arrayFile);
		
		//Counter variables to keep track of how many values have been written from each array
		int counter1 = 0;
		int counter2 = 0;
		
		System.out.println("\nWriting values to the file");
		System.out.println("--------------------------");
		
		//while loop that runs until all values from one array have been written to file
		while (counter1 != size1 && counter2 != size2) {
			
			//if statement that checks and writes the lower value
			if (array1[counter1] <= array2[counter2]) {
				System.out.println("Writing: "+array1[counter1]);
				valuesFile.println(array1[counter1]);
				counter1++;
			}
			else {
				System.out.println("Writing: "+array2[counter2]);
				valuesFile.println(array2[counter2]);
				counter2++;
			}
		}
		
		//Two while loops to write remaining values of array with bigger size 
		while (counter1 != size1) {
			System.out.println("Writing: "+array1[counter1]);
			valuesFile.println(array1[counter1]);
			counter1++;
		}
		while (counter2 != size2) {
			System.out.println("Writing: "+array2[counter2]);
			valuesFile.println(array2[counter2]);
			counter2++;
		}
		
		valuesFile.close();
		
		//New Scanner to read values from file
		Scanner readFile = new Scanner(arrayFile);
		
		//New array for all values in file
		int[] arrayWithDuplicates = new int[size1 + size2];
		
		//for loop that copies all values from file to array
		for (int i = 0; i < arrayWithDuplicates.length; i++) {
			arrayWithDuplicates[i] = readFile.nextInt();
		}
		
		//Temporary array and variable created to help remove duplicates
		int[] temporaryArray = new int[arrayWithDuplicates.length];
		int temporaryVariable = 0;
		
		//for loop that removes duplicates and copies values to temporary array
		for (int i = 0; i < temporaryArray.length; i++) {
			
			boolean compare = false;
			
			//inner for loop that checks for duplicates
			for (int j = 0; j < temporaryArray.length; j++) {
				
				if (temporaryArray[j] != arrayWithDuplicates[i]) {
					compare = true;
				}
				else {
					compare = false;
					j = temporaryArray.length;
				}
			}
			
			//if statement runs when no duplicates are found
			if (compare == true) {
				temporaryArray[temporaryVariable] = arrayWithDuplicates[i];
				temporaryVariable++;
			}
		}
		
		//New variable to get length of final array
		//temporaryArray cannot be used as final because it will contain zeros for removed duplicates
		int arrayLength = 0;
		
		//for loop that runs until a zero is found in temporaryArray
		//This determines the length of the final array
		for (int i = 0; i < temporaryArray.length; i++) {
			
			if (temporaryArray[i] == 0) {
				i = temporaryArray.length;
			}
			else {
				arrayLength++;
			}
		}
		
		//Final array created to have no duplicates
		int[] noDuplicatesArray = new int[arrayLength];
		
		//for loop that copies values into noDuplicatesArray
		for (int i = 0; i < arrayLength; i++) {
			noDuplicatesArray[i] = temporaryArray[i];
		}
		
		//Displaying array with no duplicate values
		System.out.println("\nArray with no duplicate values");
		System.out.println("------------------------------");
		
		for (int i = 0; i < noDuplicatesArray.length; i++) {
			System.out.println(noDuplicatesArray[i]);
		}
		
	} //Main

} //Assignment 1