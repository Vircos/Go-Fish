package model;

import java.awt.Color;

public class Card {

	private String suit;
	private int value;
	private Color color;
	
	public Card(String suit, int value) {
		this.suit = suit;
		this.value = value;
		
		if (suit.equals("♥") || suit.equals("♦")) color = Color.RED;
		else color = Color.BLACK;
	}

	public String getSuit() {
		return suit;
	}

	public int getValue() {
		return value;
	}
	
	public String getStringValue() {
		switch (value) {
		case 1 : return "A";
		case 11 : return "J";
		case 12 : return "Q";
		case 13 : return "K";
		default : return Integer.toString(value);
		}
	}

	public Color getColor() {
		return color;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((suit == null) ? 0 : suit.hashCode());
		result = prime * result + value;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Card))
			return false;
		Card other = (Card) obj;
		if (suit == null) {
			if (other.suit != null)
				return false;
		} else if (!suit.equals(other.suit))
			return false;
		if (value != other.value)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getStringValue() + getSuit();
	}
	
}
