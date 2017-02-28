package model;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

public class CpuPlayer extends Player {

	
	
	public CpuPlayer(int playerNumber) {
		super(playerNumber);
	}
	

	public Card mostFrequentOrRandomCard() {
	    ArrayList<Card> hand = super.getHand();
	    Hashtable<String, Integer> frequecies = new Hashtable<>();
	    Card mostFrequentCard = null;
	    int mostFrequent = -1;
	    
	    for (Card card : hand) {
	    	String cardString = card.getStringValue();
	    	
	    	if (!frequecies.containsKey(cardString)) {
	    		frequecies.put(cardString, 1);
	    	} else {
	    		int f = frequecies.get(cardString);
	    		frequecies.put(cardString, ++f);
	    		
	    		if (f > mostFrequent) {
	    			mostFrequentCard = card;
	    			mostFrequent = frequecies.get(cardString);
	    		}
	    	}
	    }
	    
	    if (mostFrequent != 1 && mostFrequent != -1) {
	    	return mostFrequentCard;
	    }
	    
	    Random random = new Random();
	    int i = random.nextInt(hand.size());
	    return hand.get(i);
	}
}
