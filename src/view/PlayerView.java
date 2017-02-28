package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;

public class PlayerView extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Dimension SIZE = new Dimension(100, 100);
	
	public PlayerView() {
		
	}
	
	public Dimension getSIZE() {
		return SIZE;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.yellow);
		g.fillOval(0, 0, 100, 100);
		g.setColor(Color.black);;
		g.fillOval(20, 20, 20, 20);
		g.fillOval(60, 20, 20, 20);

		g.drawArc(20, 40, 60, 40, 0, -180);
		
	}
}
