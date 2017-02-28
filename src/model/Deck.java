package model;

import java.util.Collections;
import java.util.Stack;

public class Deck {
	
	private Stack<Card> deck;
	
	public Deck() {
		makeDeck();
	}
	
	public Card drawCard() {
		return deck.pop();
	}
	
	public int cardsLeft() {
		return deck.size();
	}
	
	private void makeDeck() {
		deck = new Stack<>();
		for (int i = 0; i < 4; i++) {
			switch (i) {
			case 0 : makeSuit("♥"); break;
			case 1 : makeSuit("♦"); break;
			case 2 : makeSuit("♣"); break;
			case 3 : makeSuit("♠"); break;
			}
		}
		
		Collections.shuffle(deck);
	}
	
	private void makeSuit(String suit) {
		for (int i = 1; i < 14; i++) {
			deck.add(new Card(suit, i));
		}
	}
}
