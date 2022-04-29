/* Ibrahim Khan
 * CS 1450-001
 * Assignment #10
 * 04/30/2020
 * This assignment implements a binary tree and conducts a level order traversal of it.
 * This tree contains parrot objects that each have a unique ID, name, and song word.
 * Parrots are organized and placed into the tree based on their ID numbers.
 * The traversal prints out a song when all parrot objects are invoked to sing.
 */

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;

public class KhanIbrahimAssignment10 {

	public static void main(String[] args) throws IOException {
		
		//Setting up to read from file
		File parrotsFile = new File("parrots.txt");
		Scanner readParrots = new Scanner(parrotsFile);
		
		//Creating a new binary tree
		BinaryTree parrotTree = new BinaryTree();
		
		//Loop runs until all parrot objects from file have been inserted to tree
		while (readParrots.hasNext()) {
			Parrot newParrot = new Parrot(readParrots.nextInt(), readParrots.next(), readParrots.next());
			parrotTree.insert(newParrot);
		}
		
		//Print song through a level order traversal
		System.out.println("Parrot's Song");
		System.out.println("--------------------------------");
		parrotTree.levelOrder();
		
		//Print the names of all leaf node parrots
		System.out.println("\n\nLeaf Node Parrots");
		System.out.println("--------------------------------");
		parrotTree.visitLeaves();
		
	} //Main
	
} //Assignment10

class Parrot implements Comparable<Parrot> {
	
	//Private data fields
	private int id;
	private String name;
	private String songWord;
	
	//Constructor
	public Parrot(int id, String name, String songWord) {
		this.id = id;
		this.name = name;
		this.songWord = songWord;
	}
	
	//Name getter
	public String getName() {
		return name;
	}
	
	//Method to return parrot's song word
	public String sing() {
		return songWord;
	}
	
	@Override
	public int compareTo(Parrot otherParrot) {
		if (this.id > otherParrot.id) {
			return 1;
		}
		else if (this.id < otherParrot.id) {
			return -1;
		}
		else {
			return 0;
		}
	}
	
} //Parrot

class BinaryTree {
	
	//Private data field
	private TreeNode root;
	
	//Constructor
	public BinaryTree() {
		root = null;
	}
	
	//Method that inserts a parrot object into tree
	public boolean insert(Parrot parrotToAdd) {
		
		//If there is nothing in tree yet
		if (root == null) {
			root = new TreeNode(parrotToAdd);
		}
		else {
			TreeNode parent = null; //Parent node used to insert parrot
			TreeNode current = root; //Current node used to find correct spot in tree
			
			//Loop runs until bottom of tree is reached
			while (current != null) {
				
				//If parrotToAdd should be in left subtree
				if (parrotToAdd.compareTo(current.parrot) < 0) {
					parent = current;
					current = current.left;
				}
				//If parrotToAdd should be in right subtree
				else if (parrotToAdd.compareTo(current.parrot) > 0) {
					parent = current;
					current = current.right;
				}
				//If there is a duplicate - parrot will not be added
				else {
					return false;
				}
			}
			
			//Adding parrot to its correct location in tree
			if (parrotToAdd.compareTo(parent.parrot) < 0) {
				parent.left = new TreeNode(parrotToAdd);
			}
			else {
				parent.right = new TreeNode(parrotToAdd);
			}
		}
		
		return true;
	}
	
	//Method that traverses tree and prints each parrot's song word
	public void levelOrder() {
		
		//If statement checks if tree is empty
		if (root != null) {
			
			//Queue created to start traversal
			Queue <TreeNode> parrotQueue = new LinkedList<>();
			parrotQueue.offer(root);
			
			//Loop runs until queue is empty (all parrots have gone through queue)
			while (!parrotQueue.isEmpty()) {
				
				//tempNode used to print song word
				TreeNode tempNode = parrotQueue.remove();
				System.out.printf("%s ", tempNode.parrot.sing());
				
				//Adding left and right parrots to queue
				if (tempNode.left != null) {
					parrotQueue.offer(tempNode.left);
				}
				if (tempNode.right != null) {
					parrotQueue.offer(tempNode.right);
				}
			}
		}
	}
	
	//Method used to return leaf nodes
	public void visitLeaves() {
		
		//If statement checks if tree is empty
		if (root != null) {
			//Call private visitLeaves method
			visitLeaves(root);
		}
	}
	
	//Recursive method that runs until all leaf nodes have been found
	private void visitLeaves(TreeNode node) {
		
		//If there is a parrot at node's left - call method again
		if (node.left != null) {
			visitLeaves(node.left);
		}
		//If there is a parrot at node's right - call method again
		if (node.right != null) {
			visitLeaves(node.right);
		}
		//If a leaf node is found - name is printed
		if (node.left == null && node.right == null) {
			System.out.println(node.parrot.getName());
		}
	}
	
	//Private inner class
	private static class TreeNode {
		
		//Data fields
		Parrot parrot;
		TreeNode left;
		TreeNode right;
		
		//Constructor
		public TreeNode(Parrot parrot) {
			this.parrot = parrot;
			left = null;
			right = null;
		}
		
	} //TreeNode
	
} //BinaryTree