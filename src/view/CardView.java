package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import model.Card;

public class CardView extends JComponent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean facedown = true, sideways = false;
	private Card card;
	private final Dimension SIZE = new Dimension(60, 100);
	
	public CardView() {
		if (facedown) {
			JLabel label = new JLabel("Deck");
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setForeground(Color.black);
			
			label.setBounds(2, 48, 58, 20);
			add(label);
		} else {
			JLabel label = new JLabel(card.toString());
			label.setForeground(card.getColor());
		}
	}
	
	public Card getCard() {
		return card;
	}

	public Dimension getSIZE() {
		return SIZE;
	}

	public CardView(Card card, boolean faceup) {
		this.card = card;
		
		if (faceup)
			facedown = false;
		
		if (facedown) {
			if (!sideways) {
				JLabel label = new JLabel("Deck");
				label.setHorizontalAlignment(SwingConstants.CENTER);
				label.setForeground(Color.black);
				label.setBounds(2, 48, 58, 20);
				add(label);
			}
		} else {
			JLabel label = new JLabel(card.toString());
			label.setForeground(card.getColor());
			label.setBounds(4, 2, 58, 20);
			
			JLabel label2 = new JLabel(card.toString());
			label2.setForeground(card.getColor());
			label2.setHorizontalAlignment(SwingConstants.TRAILING);
			label2.setBounds(2, 86, 58, 20);
			
			JLabel label3 = new JLabel(card.getSuit());
			label3.setForeground(card.getColor());
			label3.setHorizontalAlignment(SwingConstants.CENTER);
			label3.setBounds(2, 48, 58, 20);
			
			add(label);
			add(label2);
			add(label3);
		}
	}
	
	public CardView( boolean faceup, boolean sideWays) {
		this.sideways = sideWays;
		
		if (faceup)
			facedown = false;
		
		if (facedown) {
			if (!sideways) {
				JLabel label = new JLabel("Deck");
				label.setHorizontalAlignment(SwingConstants.CENTER);
				label.setForeground(Color.black);
				label.setBounds(2, 48, 58, 20);
				add(label);
			}
		} else {
			JLabel label = new JLabel(card.toString());
			label.setForeground(card.getColor());
			label.setBounds(4, 2, 58, 20);
			
			JLabel label2 = new JLabel(card.toString());
			label2.setForeground(card.getColor());
			label2.setHorizontalAlignment(SwingConstants.TRAILING);
			label2.setBounds(2, 86, 58, 20);
			
			JLabel label3 = new JLabel(card.getSuit());
			label3.setForeground(card.getColor());
			label3.setHorizontalAlignment(SwingConstants.CENTER);
			label3.setBounds(2, 48, 58, 20);
			
			add(label);
			add(label2);
			add(label3);
		}
	}
	
	public CardView(boolean faceup) {
		if (faceup)
		facedown = false;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int x = SIZE.width;
		int y = SIZE.height;
		
		if (sideways) {
			int t = x;
			x = y;
			y = t;
		}
		
		g.setColor(Color.white);
		g.fillRoundRect(2, 2, x, y, 5, 5);
		g.setColor(Color.black);
		g.drawRoundRect(2, 2, x, y, 5, 5);
		
		if (facedown) {
			g.setColor(Color.red);
			g.fillRoundRect( 7, 7, x - 10, y - 10, 5, 5);
		}
	}
	
	@Override
	public Dimension getPreferredSize() {
		return SIZE;
	}

}
