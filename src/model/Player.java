package model;

import java.util.ArrayList;

public class Player {
	
	private ArrayList<Card> hand;
	private int points;
	private int playerNumber;
	
	public Player(int playerNumber) {
		hand = new ArrayList<Card>();
		points = 0;
		this.playerNumber = playerNumber;
	}
	
	
	public void addCard(Card card) {
		hand.add(card);
	}
	
	public ArrayList<Card> hasCard(Card card) {
		
		ArrayList<Card> cards = new ArrayList<>();
		
		for (Card cardInHand : hand) {
			if (cardInHand.getValue() == card.getValue()) {
				cards.add(cardInHand);
				//hand.remove(cardInHand);
			}
		}
		
		hand.removeAll(cards);
		
		if (cards.size() != 0) return cards;
		else return null;
	}
	
	public Card selectCardAtPosition(int index) {
		return hand.get(index);
	}

	public int getPoints() {
		return points;
	}
	
	public int addPoint() {
		return ++points;
	}

	public ArrayList<Card> getHand() {
		return hand;
	}
	

	public int getPlayerNumber() {
		return playerNumber;
	}

	public Card[] hasBookOf(Card card) {
		Card[] book = new Card[4];
		int value = card.getValue();
		int cardFrequency = 0;
		for (int i = 0; i < hand.size(); i++) {
			if (hand.get(i).getValue() == value) {
				book[cardFrequency] = hand.get(i);
				cardFrequency++;
			}
		}
		if (cardFrequency == 4) {
			for (Card c : book) {
				hand.remove(c);
			}
			addPoint();
			return book;
		}
		else return null;
	}
}
