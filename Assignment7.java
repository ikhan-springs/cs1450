/* Ibrahim Khan
 * CS 1450-001
 * Assignment #7
 * 04/09/2020
 * This assignment deals with queues and priority queues through an escape room simulation.  Player objects
 * start in a regular waiting queue before entering the game.  Afterwards, the players are stored in a priority
 * results queue that sorts each player based on their score.  The sort method used is the selection sort, which
 * uses the compareTo method. 
 */

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;

public class KhanIbrahimAssignment7 {

	public static void main(String[] args) throws IOException {
		
		Player[] seats = new Player[25]; //array that holds players before being put into waitingQueue
		EscapeGame escapeGame = new EscapeGame(); //new escapeGame created
		EscapeGameController escapeGameController = new EscapeGameController(); //new controller created
		
		//Setting up to read player info from file
		File players = new File("players.txt");
		Scanner readPlayers = new Scanner(players);
		
		//while loop that transfers file data to seats array
		while(readPlayers.hasNext()) {
			
			Player player = new Player(readPlayers.next(), readPlayers.nextInt(), readPlayers.nextInt());
			seats[player.getSeat()] = player;
		}
		
		//Moving players from seats array to waitingQueue
		System.out.println("Controller: Moving players from outside seats into escape game:");
		System.out.println("----------------------------------------------------------------");
		escapeGameController.movePlayerIntoEscapeGame(seats, escapeGame);
		
		//Moving players from waitingQueue to game, then to resultsQueue
		System.out.println("\nController: Starting Escape Game - moving players waiting in line into escape room:");
		System.out.println("--------------------------------------------------------------------------------------");
		System.out.println("Player\tScore\tCurrent Leader");
		System.out.println("--------------------------------------------------------------------------------------");
		escapeGameController.simulateGame(escapeGame);
		
		//Displaying game results by removing players from resultsQueue
		System.out.println("\nController: Escape Room Results");
		System.out.println("--------------------------------");
		System.out.println("Player\tScore");
		System.out.println("--------------------------------");
		escapeGameController.displayResults(escapeGame);
		
	} //Main
	
} //Assignment7


class Player implements Comparable<Player> {
	
	//Private data fields
	private String name;
	private int ranking;
	private int seat;
	private int score;
	
	//Constructor
	public Player(String name, int ranking, int seat) {
		this.name = name;
		this.ranking = ranking;
		this.seat = seat;
		score = 0;
	}
	
	//Getters
	public String getName() {
		return name;
	}
	public int getRanking() {
		return ranking;
	}
	public int getSeat() {
		return seat;
	}
	public int getScore() {
		return score;
	}
	
	//Setter
	public void setScore(int score) {
		this.score = score;
	}
	
	@Override
	public int compareTo(Player otherPlayer) {
		
		if (this.score < otherPlayer.score) {
			return -1;
		}
		else if (this.score > otherPlayer.score) {
			return 1;
		}
		else {
			return 0;
		}	
	}
	
} //Player

class EscapeRoom {
	
	//Return a hash of the key. Key can be any length
	//Returns an integer >= 0
	private int hash(String key) {
		
		int hash = 0;
		
		for (int i = 0; i < key.length(); i++) {
			hash += key.charAt(i);
			hash += (hash << 10);
			hash ^= (hash >> 6);
		}
		hash += (hash << 3);
		hash ^= (hash >> 11);
		hash += (hash << 15);
		
		return Math.abs(hash);
		
	}
	
	//Method that returns score by invoking hash
	public int tryToEscape(String playerName, int playerRanking) {
		
		String key = playerName + playerRanking;
		int hashResult = hash(key);
		int score = hashResult % 101;
		
		return score;		
	}
	
} //EscapeRoom

class EscapeGame {
	
	//Private data fields
	private Queue <Player> waitingQueue;
	private PriorityQueue resultsQueue;
	private EscapeRoom escapeRoom;
	
	//Constructor
	public EscapeGame() {
		waitingQueue = new LinkedList<Player>();
		resultsQueue = new PriorityQueue();
		escapeRoom = new EscapeRoom();
	}
	
	//Checks if waitingQueue is empty
	public boolean isWaitingQueueEmpty() {
		return waitingQueue.isEmpty();
	}
	
	//Adds player to waitingQueue
	public void addPlayerToWaitingQueue(Player player) {
		waitingQueue.offer(player);
	}
	
	//Removes player from waitingQueue
	public Player removePlayerFromWaitingQueue() {
		return waitingQueue.remove();
	}
	
	//Checks if resultsQueue is empty
	public boolean isResultsQueueEmpty() {
		return resultsQueue.isEmpty();
	}
	
	//Adds player to resultsQueue
	public void addPlayerToResultsQueue(Player player) {
		resultsQueue.offer(player);
	}
	
	//Removes player from resultsQueue
	public Player removePlayerFromResultsQueue() {
		return resultsQueue.remove();
	}
	
	//Returns player from resultsQueue without removing
	public Player peekResultsQueue() {
		return resultsQueue.peek();
	}
	
	//Simulates game and returns a score
	public int tryToEscape(String playerName, int playerRanking) {
		return escapeRoom.tryToEscape(playerName, playerRanking);
	}
	
} //EscapeGame

class EscapeGameController {
	
	//Moves players from seats array into waitingQueue
	public void movePlayerIntoEscapeGame(Player[] seats, EscapeGame escapeGame) {
		
		for (int i = 0; i < seats.length; i++) {
			
			//Checking if seat is occupied by a player
			if (seats[i] != null) {
				escapeGame.addPlayerToWaitingQueue(seats[i]);
				System.out.println("Moved to escape game: "+seats[i].getName()+" from outside seat "+seats[i].getSeat());
			}
		}
		
	} //movePlayerIntoEscapeGame
	
	public void simulateGame(EscapeGame escapeGame) {
		
		//Method runs until waitingQueue is empty
		while (!escapeGame.isWaitingQueueEmpty()) {
			
			//Remove player from waitingQueue and obtain score
			Player playingPlayer = escapeGame.removePlayerFromWaitingQueue();
			playingPlayer.setScore(escapeGame.tryToEscape(playingPlayer.getName(), playingPlayer.getRanking()));
			
			//Add player to resultsQueue
			escapeGame.addPlayerToResultsQueue(playingPlayer);
			System.out.printf("%s\t%d\t%s\n", playingPlayer.getName(),
											  playingPlayer.getScore(), 
											  escapeGame.peekResultsQueue().getName());
		}
		
	} //simulateGame
	
	public void displayResults(EscapeGame escapeGame) {
		
		//Method runs until resultsQueue is empty
		while (!escapeGame.isResultsQueueEmpty()) {
			
			//Displays each player's name and score
			Player displayPlayer = escapeGame.removePlayerFromResultsQueue();
			System.out.printf("%s\t%d\n", displayPlayer.getName(), displayPlayer.getScore());
		}
		
	} //displayResults
	
} //EscapeGameController

class PriorityQueue {
	
	//Private data fields
	private Player[] list;
	private int numPlayers;
	
	//Constructor
	public PriorityQueue() {
		list = new Player[30];
		numPlayers = 0;
	}
	
	//Checks if priority queue is empty
	public boolean isEmpty() {
		
		if (numPlayers == 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	//Returns top scoring player
	public Player peek() {
		return list[numPlayers - 1];
	}
	
	//Add player to priority queue
	public boolean offer(Player player) {
		
		//If statement checks if queue is full
		if (numPlayers == 30) {
			return false;
		}
		else {
			list[numPlayers] = player;
			numPlayers++;
			selectionSort(list, numPlayers);
			return true;
		}
	}
	
	//Remove player from priority queue
	public Player remove() {
		
		Player removingPlayer = list[numPlayers - 1];
		list[numPlayers - 1] = null;
		numPlayers--;
		
		return removingPlayer;
	}
	
	//Sort players based on score
	private void selectionSort(Player[] list, int numPlayers) {
		
		for (int i = 0; i < numPlayers; i++) {
			
			Player lowestScore = list[i];
			int lowestIndex = i;
			
			for (int j = i + 1; j < numPlayers; j++) {
				
				if (lowestScore.compareTo(list[j]) > 0) {
					lowestScore = list[j];
					lowestIndex = j;
				}
			}
			
			if (lowestIndex != i) {
				list[lowestIndex] = list[i];
				list[i] = lowestScore;
			}
		}
	}
	
} //PriorityQueue