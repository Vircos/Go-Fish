package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.Card;
import model.CpuPlayer;
import model.Deck;
import model.Player;
import view.CardView;
import view.GoFishPanel;
import view.PlayerView;

public class ViewController {

	JPanel topPanel;
	JPanel bottomPanel;
	JPanel leftPanel;
	JPanel rightPanel;
	JLabel lblGameMessages;
	JPanel pnlP1CardSpace;
	JPanel pnlP2CardSpace;
	JPanel pnlP3CardSpace;
	JPanel pnlP4CardSpace;

	JLabel lblP1Points;
	JLabel lblP2Points;
	JLabel lblP3Points;
	JLabel lblP4Points;
	JLabel lblCardsLeft;
	CardView deckView;

	PlayerView pView2;
	PlayerView pView3;
	PlayerView pView4;

	JScrollPane scrollPane;

	GoFishPanel goFishPanel;
	String numberOfPLayersString;
	int numberOfPlayers;

	final int USER_TURN = 0;
	Player[] players;
	Deck deck;
	int turnCounter = -1;
	Random random;
	Card selectedCard;
	LinkedList<String> messages;
	JFrame frame;

	public ViewController() {
		frame = new JFrame("Go Fish");
		goFishPanel = new GoFishPanel();
		random = new Random();
		messages = new LinkedList<>();
		setupProperties();
		showNumberOfPlayersDialog();
		setUpNumberOfPlayers();

		createDeck();
		dealCards();
		checkIfAnyPlayersHaveBooks();

		paintCPUCards();
		paintUserCards();

		lblCardsLeft.setText(deck.cardsLeft() + " cards left.");

		nextTurn();
		writeToGameMessages("");
		writeToGameMessages("Your Turn! Select a card:");

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(goFishPanel);
		frame.pack();
		frame.setVisible(true);
	}

	private void refresh() {
		paintCPUCards();
		paintUserCards();
		goFishPanel.repaint();
	}

	private boolean continueGame() {
		boolean goOn = false;
		for (Player p : players) {
			if (p.getHand().size() > 0)
				goOn = true;
		}
		return goOn;
	}

	private void createDeck() {
		writeToGameMessages("Shuffling Deck...");
		deck = new Deck();
	}

	private void dealCards() {
		writeToGameMessages("Dealing Cards...");
		for (int i = 0; i < 5; i++) {
			for (Player p : players)
				p.addCard(deck.drawCard());
		}
	}

	private void drawUpTo5Cards(Player player) {
		int i = 0;
		if (deck.cardsLeft() > 0) {
			do {
				if (deck.cardsLeft() > 0)
					player.addCard(deck.drawCard());
				i++;
			} while (i < 5 && deck.cardsLeft() > 0);
			writeToGameMessages("Player" + player.getPlayerNumber() + " draws " + (i + 1) + " cards.");
		} else
			deckView.setVisible(false);
		lblCardsLeft.setText(deck.cardsLeft() + " cards left.");
	}

	private void checkIfAnyPlayersHaveBooks() {
		for (Player player : players) {
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

	private void cardSelected(Card card) {
		if (turnCounter == USER_TURN) {
			selectedCard = card;
			writeToGameMessages(selectedCard + " selected. Click on a Smiley");
		}
	}

	private int nextTurn() {
		turnCounter++;
		if (turnCounter == players.length)
			turnCounter = 0;
		return turnCounter;
	}

	private void playerSelected(int n) {
		if (turnCounter == USER_TURN && selectedCard != null) {
			n = n - 1;
			userTurn(players[n]);
		}
	}

	private void userTurn(Player chosenPlayer) {
		refresh();

		// check to see if the game has ended
		if (deck.cardsLeft() < 1 && !continueGame()) {
			endGame();
		}

		Player userPlayer = players[USER_TURN];
		// draw up to 5 cards if empty
		if (userPlayer.getHand().size() < 1)
			drawUpTo5Cards(userPlayer);
		refresh();

		if (userPlayer.getHand().size() > 0) {
			// ask player for card
			ArrayList<Card> cardsFromPlayer = chosenPlayer.hasCard(selectedCard);

			// if player doesnt have it go fish
			if (cardsFromPlayer == null) {
				goFish(userPlayer);
				nextTurn();
				for (int i = 1; i < players.length; i++) {
					while (cpuTurn(players[i]))
						;
				}
				writeToGameMessages("");
				writeToGameMessages("Your turn! Select a card:");
				// end turn
				selectedCard = null;
			} else {
				for (Card c : cardsFromPlayer) {
					userPlayer.addCard(c);
					writeToGameMessages("CPU" + chosenPlayer.getPlayerNumber() + " gives card: " + c + " to Player"
							+ userPlayer.getPlayerNumber());
				}

				// check to see if he has a book
				Card[] book = userPlayer.hasBookOf(cardsFromPlayer.get(0));
				if (book != null) {
					printBook(userPlayer, book);
					printPoints();
				}

				writeToGameMessages("");
				writeToGameMessages("Go Again!");
				refresh();
				// return true if other player did have the card, ie go again
				selectedCard = null;
			}
		} else { // VERY SPECIAL CASE ONLY IF THERE ARE NO CARDS IN DECK AND NON
					// IN YOUR HAND!!!!
			nextTurn();
			do {
				for (int i = 1; i < players.length; i++) {
					while (cpuTurn(players[i]));
				}
			} while (deck.cardsLeft() > 0 || continueGame());
		}
	}

	private boolean cpuTurn(Player player) {
		refresh();

		// check to see if the game has ended
		if (deck.cardsLeft() < 1 && !continueGame()) {
			endGame();
		}

		writeToGameMessages("");
		writeToGameMessages("Player" + player.getPlayerNumber() + "'s Turn");

		CpuPlayer cpuPlayer = (CpuPlayer) player;
		// draw up to 5 cards if empty
		if (cpuPlayer.getHand().size() < 1)
			drawUpTo5Cards(cpuPlayer);
		refresh();
		// ask player for card
		if (cpuPlayer.getHand().size() > 0) {
			Card card = cpuPlayer.mostFrequentOrRandomCard();

			// select other player at random
			int index;
			do {
				index = random.nextInt(players.length);
			} while (index == turnCounter);

			Player otherPlayer = players[index];
			ArrayList<Card> cardsFromPlayer = otherPlayer.hasCard(card);

			if (otherPlayer.getPlayerNumber() != 1) {
				writeToGameMessages("CPU" + cpuPlayer.getPlayerNumber() + " asks CPU" + otherPlayer.getPlayerNumber()
						+ " for card " + card);
			} else {
				writeToGameMessages("CPU" + cpuPlayer.getPlayerNumber() + " asks Player" + otherPlayer.getPlayerNumber()
						+ " for card " + card);
			}
			// go fish if other player didnt have any cards
			if (cardsFromPlayer == null) {
				goFish(cpuPlayer);
				nextTurn();
				return false;
			} else {
				for (Card c : cardsFromPlayer) {
					cpuPlayer.addCard(c);
					writeToGameMessages("Player" + otherPlayer.getPlayerNumber() + " gives card: " + c + " to Player"
							+ cpuPlayer.getPlayerNumber());
				}
				// check to see if he has a book
				Card[] book = cpuPlayer.hasBookOf(cardsFromPlayer.get(0));
				if (book != null) {
					printBook(cpuPlayer, book);
					printPoints();
				}
				return true;
			}
		} else
			return false;
	}

	private void goFish(Player player) {
		if (deck.cardsLeft() > 0)
			player.addCard(deck.drawCard());
		else {
			writeToGameMessages(("No more cards in deck!"));
			deckView.setVisible(false);
		}
		lblCardsLeft.setText(deck.cardsLeft() + " cards left.");
		if (player.getPlayerNumber() != 1)
			writeToGameMessages("Go Fish: CPU" + player.getPlayerNumber());
		else
			writeToGameMessages("Go Fish: Player" + player.getPlayerNumber());
	}

	private void showNumberOfPlayersDialog() {
		do {
			numberOfPLayersString = JOptionPane.showInputDialog("Enter number of CPUs 2-4 (more fun with 4!)");
		} while (!numberOfPLayersString.equals("2") && !numberOfPLayersString.equals("3")
				&& !numberOfPLayersString.equals("4"));
	}

	private void endGame() {
		goFishPanel.removeAll();
		goFishPanel.repaint();

		String message = "Player";
		int highScore = 0;
		int playerNumber = -1;
		for (Player p : players) {
			if (p.getPoints() > highScore) {
				playerNumber = p.getPlayerNumber();
				highScore = p.getPoints();
			}
		}
		message += playerNumber + " has Won with " + highScore + " points!";

		JOptionPane.showMessageDialog(frame, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
	}

	private void setUpNumberOfPlayers() {
		numberOfPlayers = Integer.parseInt(numberOfPLayersString);
		createPlayers(numberOfPlayers);
		switch (numberOfPlayers) {
		case 2: twoPlayerSetup(); break;
		case 3: threePlayerSetup(); break;
		case 4: fourPlayerSetup(); break;
		}
	}

	private void createPlayers(int number) {
		players = new Player[number];
		for (int i = 0; i < number; i++)
			if (i == 0)
				players[i] = new Player(i + 1);
			else
				players[i] = new CpuPlayer(i + 1);
		writeToGameMessages("Number of Players: " + players.length);
	}

	private void twoPlayerSetup() {
		pView3.setVisible(false);
		pView3.setEnabled(false);
		leftPanel.setVisible(false);
		lblP3Points.setVisible(false);

		pView4.setVisible(false);
		pView4.setEnabled(false);
		rightPanel.setVisible(false);
		lblP4Points.setVisible(false);

		pView2.addMouseListener(new PlayerViewMouseListener(2));
	}

	private void threePlayerSetup() {
		pView4.setVisible(false);
		pView4.setEnabled(false);
		rightPanel.setVisible(false);
		lblP4Points.setVisible(false);

		pView2.addMouseListener(new PlayerViewMouseListener(2));
		pView3.addMouseListener(new PlayerViewMouseListener(3));
	}

	private void fourPlayerSetup() {
		pView2.addMouseListener(new PlayerViewMouseListener(2));
		pView3.addMouseListener(new PlayerViewMouseListener(3));
		pView4.addMouseListener(new PlayerViewMouseListener(4));
	}

	private void writeToGameMessages(String s) {
		messages.add("<br>" + s);
		String html = "<html>";

		ListIterator<String> iterator = messages.listIterator();
		while (iterator.hasNext())
			iterator.next();

		while (iterator.hasPrevious()) {
			html += iterator.previous();
		}
		html += "</html>";
		lblGameMessages.setText(html);
	}

	private void printBook(Player player, Card[] book) {
		String string = "Player" + player.getPlayerNumber() + " has book of: ";
		String m = "";
		for (Card c : book) m += (c + " ");
		writeToGameMessages(string + m);
	}

	private void printPoints() {
		switch (players.length) {
		case 4: lblP4Points.setText("CPU 4 Points: " + players[3].getPoints());
		case 3: lblP3Points.setText("CPU 3 Points: " + players[2].getPoints());
		case 2: lblP2Points.setText("CPU 2 Points: " + players[1].getPoints());
		case 1: lblP1Points.setText("Player 1 Points: " + players[0].getPoints());
		}
		refresh();
	}

	private void setupProperties() {
		topPanel = goFishPanel.topPanel;
		bottomPanel = goFishPanel.bottomPanel;
		leftPanel = goFishPanel.leftPanel;
		rightPanel = goFishPanel.rightPanel;
		lblGameMessages = goFishPanel.lblGameMessages;
		pnlP1CardSpace = goFishPanel.pnlP1CardSpace;
		pnlP2CardSpace = goFishPanel.pnlP2CardSpace;
		pnlP3CardSpace = goFishPanel.pnlP3CardSpace;
		pnlP4CardSpace = goFishPanel.pnlP4CardSpace;

		lblP1Points = goFishPanel.lblP1Points;
		lblP2Points = goFishPanel.lblP2Points;
		lblP3Points = goFishPanel.lblP3Points;
		lblP4Points = goFishPanel.lblP4Points;

		lblP1Points.setText("Player 1 Points: 0");
		lblP2Points.setText("CPU 2 Points: 0");
		lblP3Points.setText("CPU 3 Points: 0");
		lblP4Points.setText("CPU 4 Points: 0");

		lblCardsLeft = goFishPanel.lblNumberOfCardsLeft;
		deckView = goFishPanel.deck;

		pView2 = goFishPanel.pView2;
		pView3 = goFishPanel.pView3;
		pView4 = goFishPanel.pView4;

		scrollPane = goFishPanel.scrollPane;
		lblGameMessages.setText("");
	}

	private void paintUserCards() {
		pnlP1CardSpace.removeAll();
		int xOffset = 0;
		ArrayList<Card> hand = players[0].getHand();
		for (Card card : hand) {
			CardView cardView = new CardView(card, true);
			cardView.setBounds(xOffset, 0, 65, 105);
			cardView.addMouseListener(new CardViewMouseListener());
			xOffset += 30;
			pnlP1CardSpace.add(cardView);
		}
	}
	
	@SuppressWarnings("unused")
	private void paintCPUCards() {
		int xOffset = 0, yOffset = 0;
		switch (numberOfPlayers) {
		case 4:
			pnlP4CardSpace.removeAll();
			ArrayList<Card> hand4 = players[3].getHand();
			for ( Card card : hand4) {
				CardView cv = new CardView(false, true);
				cv.setBounds(0, yOffset, 105, 65);
				yOffset += 15;
				pnlP4CardSpace.add(cv);
			}

		case 3:
			pnlP3CardSpace.removeAll();
			ArrayList<Card> hand3 = players[2].getHand();
			yOffset = 0;
			for ( Card card : hand3) {
				CardView cv = new CardView(false, true);
				cv.setBounds(0, yOffset, 105, 65);
				yOffset += 15;
				pnlP3CardSpace.add(cv);
			}
		case 2:
			pnlP2CardSpace.removeAll();
			ArrayList<Card> hand2 = players[1].getHand();
			xOffset = 0;
			for ( Card card : hand2) {
				CardView cv = new CardView(false);
				cv.setBounds(xOffset, 0, 65, 105);
				xOffset += 20;
				pnlP2CardSpace.add(cv);
			}
			break;
		default:
			break;
		}
	}

	private class CardViewMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 1) {
				CardView cv = (CardView) e.getSource();
				Card card = cv.getCard();
				cardSelected(card);
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

	private class PlayerViewMouseListener implements MouseListener {

		int playerNumber;

		public PlayerViewMouseListener(int playerNumber) {
			super();
			this.playerNumber = playerNumber;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 1) {
				playerSelected(playerNumber);
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

}
