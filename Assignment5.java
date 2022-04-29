/* Ibrahim Khan
 * CS 1450-001
 * Assignment #5
 * 02/27/2020
 * This assignment deals with generic classes and methods.  This program has a generic class 
 * that is meant to create stacks.  This class is used in main to read from two Integer files
 * and two String files to create unique stacks for each file.  There are also multiple generic
 * methods that manipulate the data in these stacks, such as printing stack data and merging
 * stacks.
 */

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class KhanIbrahimAssignment5 {

	public static void main(String[] args) throws IOException {
		
		//Setting up to read from Integer files
		File numbersFile1 = new File("numbers1.txt");
		File numbersFile2 = new File("numbers2.txt");
		Scanner readNumbers1 = new Scanner(numbersFile1);
		Scanner readNumbers2 = new Scanner(numbersFile2);
		
		//New stacks created for each file
		GenericStack <Integer> numStack1 = new GenericStack <>();
		GenericStack <Integer> numStack2 = new GenericStack <>();
		
		//while loops to read from file and add values to stacks
		while (readNumbers1.hasNext()) {
			numStack1.push(readNumbers1.nextInt());
		}
		while (readNumbers2.hasNext()) {
			numStack2.push(readNumbers2.nextInt());
		}
		
		readNumbers1.close();
		readNumbers2.close();
		
		//Printing stack values
		System.out.println("Numbers Stack 1 - filled with values from numbers1.txt");
		System.out.println("---------------------------------------------------------");
		printStack(numStack1);
		System.out.println("\nNumbers Stack 2 - filled with values from numbers2.txt");
		System.out.println("---------------------------------------------------------");
		printStack(numStack2);
		
		//New stack that will merge values from the two stacks
		GenericStack <Integer> mergedNumStack = new GenericStack<>();
		
		//mergeStacks method merges the two Integer stacks
		mergeStacks(numStack1, numStack2, mergedNumStack);
		
		//New stack to make the merged stack have lowest value on top
		GenericStack <Integer> finalMergedNumStack = new GenericStack<>();
		
		//reverseStack method switches order of stack values
		reverseStack(mergedNumStack, finalMergedNumStack);
		
		//Printing the final merged stack
		System.out.println("\nMerged Stack - lowest value on top");
		System.out.println("---------------------------------------------------------");
		printStack(finalMergedNumStack);
		
		//Setting up to read from String files
		File mountainsFile1 = new File("mountains1.txt");
		File mountainsFile2 = new File("mountains2.txt");
		Scanner readMountains1 = new Scanner(mountainsFile1);
		Scanner readMountains2 = new Scanner(mountainsFile2);
		
		//New stacks created for each file
		GenericStack <String> stringStack1 = new GenericStack<>();
		GenericStack <String> stringStack2 = new GenericStack<>();
		
		//while loops to read from file and add values to stacks
		while (readMountains1.hasNext()) {
			stringStack1.push(readMountains1.nextLine());
		}
		while (readMountains2.hasNext()) {
			stringStack2.push(readMountains2.nextLine());
		}
		
		readMountains1.close();
		readMountains2.close();
		
		//Printing stack values
		System.out.println("\nString Stack 1 - filled with values from mountains1.txt");
		System.out.println("---------------------------------------------------------");
		printStack(stringStack1);
		System.out.println("\nString Stack 2 - filled with values from mountains2.txt");
		System.out.println("---------------------------------------------------------");
		printStack(stringStack2);
		
		//New stack that will merge values from the two stacks
		GenericStack <String> mergedStringStack = new GenericStack<>();
		
		//mergeStacks method merges the two String stacks
		mergeStacks(stringStack1, stringStack2, mergedStringStack);
		
		//New stack to make the merged stack have lowest value on top
		GenericStack <String> finalMergedStringStack = new GenericStack<>();
		
		//reverseStack method switches order of stack values
		reverseStack(mergedStringStack, finalMergedStringStack);
		
		//Printing the final merged stack
		System.out.println("\nMerged Stack - lowest value on top");
		System.out.println("---------------------------------------------------------");
		printStack(finalMergedStringStack);
		
		//Printing the two merged stacks side by side
		System.out.println("\nPrinting Merged Stacks side by side - lowest value on top");
		System.out.println("---------------------------------------------------------");
		System.out.println("Integers\tStrings");
		System.out.println("---------------------------------------------------------");
		printTwoStacks(finalMergedNumStack, finalMergedStringStack);
		
		//Print statements that prove stack order was not changed in printTwoStacks method
		System.out.println("\nNumber stack top: "+finalMergedNumStack.peek());
		System.out.println("String stack top: "+finalMergedStringStack.peek());
		
	} //Main
	
	//Method that prints stack values and maintains the order of the stack values
	public static <E> void printStack (GenericStack<E> stack) {
		
		//temporaryStack to store printed and popped values
		GenericStack <E> temporaryStack = new GenericStack<>();
		
		//while loop that runs until all values in stack are printed
		while (!(stack.isEmpty())) {
			
			System.out.println(stack.peek()); //Prints current value
			temporaryStack.push(stack.peek()); //Push same value to temporaryStack
			stack.pop(); //Pop value so that new value can be printed
		}
		
		//reverseStack used to make original stack have the same order of values as before
		reverseStack(temporaryStack, stack);
		
	} //printStack
	
	//Method that takes in three stacks
	//The first and second stacks are merged into the third stack
	public static <E extends Comparable<E>> void mergeStacks (GenericStack<E> stack1, 
															  GenericStack<E> stack2, 
															  GenericStack<E> mergedStack) {
		
		//while loop that runs until one of the stacks runs out of values
		while (!(stack1.isEmpty()) && !(stack2.isEmpty())) {
			
			//if statement runs if stack1 has the lower value
			if ((stack1.peek()).compareTo(stack2.peek()) < 0) {
				mergedStack.push(stack1.peek()); //add value to mergedStack
				stack1.pop();
			}
			
			//else statement runs if stack2 has the lower value
			else {
				mergedStack.push(stack2.peek()); //add value to mergedStack
				stack2.pop();
			}
		}
		
		//while loop runs if stack2 ran out of values and stack1 still has values
		while (!stack1.isEmpty()) {
			mergedStack.push(stack1.peek()); //add value to mergedStack
			stack1.pop();
		}
		
		//while loop runs if stack1 ran out of values and stack2 still has values		
		while (!stack2.isEmpty()) {
			mergedStack.push(stack2.peek()); //add value to mergedStack
			stack2.pop();
		}
		
	} //mergeStacks
	
	//Method that reverses the order of values in a stack by taking in two stacks
	//First stack will pop out all its values and push into second stack
	public static <E> void reverseStack(GenericStack<E> stack, 
										GenericStack<E> finalMergedStack ) {
		
		//while loop that pops values of first stack and pushes into second stack
		while (!stack.isEmpty()) {
			finalMergedStack.push(stack.peek());
			stack.pop();
		}
		
	} //reverseStack
	
	//Method that prints two stacks of different data types side by side
	public static <E, F> void printTwoStacks(GenericStack<E> stack1, GenericStack<F> stack2) {
		
		//Two temporary stacks created for each stack in parameter
		GenericStack <E> temporaryStack1 = new GenericStack <>();
		GenericStack <F> temporaryStack2 = new GenericStack <>();
		
		//while loop that runs as long as both stacks have values to be printed
		while (!(stack1.isEmpty()) && !(stack2.isEmpty())) {
			
			//Printing values side by side
			System.out.println(stack1.peek()+"\t\t"+stack2.peek());
			
			//temporaryStacks push printed values so that the values are not lost
			temporaryStack1.push(stack1.peek());
			temporaryStack2.push(stack2.peek());
			
			//Printed values are popped so that next value can be printed
			stack1.pop();
			stack2.pop();			
		}
		
		//while loop runs when all stack2 values have been printed and stack1 still has values
		//Similar body as while loop above
		while (!stack1.isEmpty()) {
			
			System.out.println(stack1.peek()+"\t\t----");
			
			temporaryStack1.push(stack1.peek());
			
			stack1.pop();
		}
		
		//while loop runs when all stack1 values have been printed and stack2 still has values
		//Similar body as while loop above
		while (!stack2.isEmpty()) {
			
			System.out.println("----\t\t"+stack2.peek());
			
			temporaryStack2.push(stack2.peek());
			
			stack2.pop();
		}
		
		//reverseStack method used to make the order same as it was before invoking method
		reverseStack(temporaryStack1, stack1);
		reverseStack(temporaryStack2, stack2);
		
	} //printTwoStacks
	
} //Assignment 5

class GenericStack <E> {
	
	//ArrayList used to create stack object
	private ArrayList <E> list;
	
	//Constructor
	public GenericStack() {
		list = new ArrayList<>();
	}
	
	//Getter for stack size
	public int getSize() {
		return list.size();
	}
	
	//Method to check if stack is empty
	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	//Method that returns top value in the stack
	public E peek() {
		return list.get(getSize() - 1);
	}
	
	//Method that adds a value to the stack
	public void push(E object) {
		list.add(object);
	}
	
	//Method that removes the top value from the stack
	public void pop() {
		list.remove(getSize() - 1);
	}
	
} //GenericStack