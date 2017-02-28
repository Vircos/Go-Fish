package controller;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import model.Card;
import model.CpuPlayer;
import model.Deck;
import model.Player;

public class Main {
	
	static final int USER_TURN = 0;
	static Player[] players;
	static Deck deck;
	static int turnCounter = -1;
	static Random random;
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		
		if (args != null) {
			ViewController vc = new ViewController();
		} else {
			
			random = new Random();
			
			createDeck();
			createPlayers(4);
			dealCards();
			checkIfAnyPLayersHaveBooks();
			// play game until deck runs out or all players run out of cards.
			do {
				int playerToGo = nextTurn();
				if (playerToGo == 0) userTurn();
				else cpuTurn(players[playerToGo]);
				
			} while(deck.cardsLeft() > 0 || continueGame());
			
			System.out.println("Player");
			int highScore = 0;
			int playerNumber = -1;
			for (Player p: players) {
				if (p.getPoints() > highScore) {
					playerNumber = p.getPlayerNumber();
					highScore = p.getPoints();
				}
			}
			System.out.print(playerNumber + " has Won with " + highScore + " points!");
		}
	}
	
	static boolean continueGame() {
		
		boolean goOn = false;
		
		for (Player p: players) {
			if (p.getHand().size() > 0) 
				goOn = true;
		}
		
		return goOn;
	}
	
	static void drawUpTo5cards(Player player) {
		int i = 0;
		do {
			if (deck.cardsLeft() > 0) 
				player.addCard(deck.drawCard());
			i++;
		} while (i < 5 && deck.cardsLeft() > 0);
		System.out.println("Player" + player.getPlayerNumber() + " draws " + (i + 1) + " cards.");
		System.out.println("Deck: " + deck.cardsLeft());
	}
	
	static void createPlayers(int number) {
		players = new Player[number];
		for (int i = 0; i < number; i++)
			if (i == 0) players[i] = new Player(i + 1);
			else players[i] = new CpuPlayer(i + 1);
		System.out.println("Number of Players: " + players.length);
	}
	
	static void createDeck() {
		deck = new Deck();
		System.out.println("Shuffling Deck...");
	}
	
	static void dealCards() {
		System.out.println("Dealing Cards...");
		for (int i = 0; i < 5; i++) {
			for (Player p : players) p.addCard(deck.drawCard());
		}
	}
	
	static int nextTurn() {
		turnCounter++;
		if (turnCounter == players.length) turnCounter = 0;
		return turnCounter;
	}
	
	// goes again if true
	@SuppressWarnings("resource")
	static boolean userTurn() {
		Player userPlayer = players[0];
		
		// draw up to 5 cards
		if (userPlayer.getHand().size() < 1) drawUpTo5cards(userPlayer);
		
		System.out.println("\nYour Turn:");
		System.out.println("You hand: ");
		ArrayList<Card> hand = userPlayer.getHand();
		for (Card c : hand) {
			System.out.print(c + " ");
		}
		// select the card
		System.out.println("Select Card");
		int index = (new Scanner(System.in)).nextInt() - 1;
		Card selectedCard = hand.get(index);
		System.out.print(selectedCard);
		
		
		// select Player
		System.out.print("\nSelect Player: ");
		for (int i = 1; i < players.length; i++)
			System.out.print((i + 1) + " ");
		
		index = (new Scanner(System.in)).nextInt() - 1;
		
		// see if other player has card
		Player otherPlayer = players[index];
		ArrayList<Card> cardsFromPlayer = otherPlayer.hasCard(selectedCard);
		
		if (cardsFromPlayer == null) goFish(userPlayer);
		else {
			for (Card c : cardsFromPlayer) {
				userPlayer.addCard(c);
				System.out.println("Player" + otherPlayer.getPlayerNumber() + " gives card: " + c
									+ " to Player" + userPlayer.getPlayerNumber());
			}
			
			// check to see if he has a book
			Card[] book = userPlayer.hasBookOf(cardsFromPlayer.get(0));
			if (book != null) {
				printBook(userPlayer, book);
				printPoints();
				return true;
			}
		}
		return false;
	}
	
	static boolean cpuTurn(Player player) {
		CpuPlayer cpuPlayer = (CpuPlayer) player;
		
		// check to see if hand is empty
		if (player.getHand().size() < 1) drawUpTo5cards(cpuPlayer);
		
		System.out.println("\nPlayer" + cpuPlayer.getPlayerNumber() + "'s turn:");
		
		// select the card
		Card card = cpuPlayer.mostFrequentOrRandomCard();
		
		// select the player
		int otherPlayerIndex;
		Player otherPlayer;
		do {
			otherPlayerIndex = random.nextInt(players.length);
			otherPlayer = players[otherPlayerIndex];
		} while (otherPlayerIndex == turnCounter);
		
		// see if other player has cards of equal value
		ArrayList<Card> cardsFromPlayer = otherPlayer.hasCard(card);
		
		System.out.println("Player" + cpuPlayer.getPlayerNumber() + " asks Player" + otherPlayer.getPlayerNumber() +
							" for card " + card);
		// if other player doesnt have cards : go fish
		if (cardsFromPlayer == null) goFish(cpuPlayer);
		else {
			for (Card c : cardsFromPlayer) {
				cpuPlayer.addCard(c);
				System.out.println("Player" + otherPlayer.getPlayerNumber() + " gives card: " + c
									+ " to Player" + cpuPlayer.getPlayerNumber());
			}
			
			// check to see if he has a book
			Card[] book = cpuPlayer.hasBookOf(cardsFromPlayer.get(0));
			if (book != null) {
				printBook(cpuPlayer, book);
				printPoints();
				return true;
			}
		}
		return false;
	}
	
	static void printBook(Player player, Card[] book) {
		System.out.println("Player" + player.getPlayerNumber() + " has book of:");
		for (Card c : book) {
			System.out.print(c + " ");
		}
	}
	
	static void printPoints() {
		System.out.println("Points:");
		for (Player p : players) {
			System.out.println("Player" + p.getPlayerNumber() + ": " + p.getPoints());
		}
	}
	
	static void goFish(Player player) {
		if (deck.cardsLeft() > 0)
			player.addCard(deck.drawCard());
		else System.out.println("No more cards in deck!");
		System.out.println("Go Fish: Player" + player.getPlayerNumber());
		System.out.println("Deck: " + deck.cardsLeft());
	}
	
	static void checkIfAnyPLayersHaveBooks() {
		for (Player player: players) {
			ArrayList<Card> hand = player.getHand();
			for (Card card : hand) {
				Card[] book = player.hasBookOf(card);
				if (book != null) {
					printBook(player, book);
					printPoints();
				}
			}
		}
	}
}
