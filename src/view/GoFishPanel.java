package view;

import java.awt.Dimension;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.UIManager;


public class GoFishPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JPanel topPanel;
	public JPanel bottomPanel;
	public JPanel leftPanel;
	public JPanel rightPanel;
	public JLabel lblGameMessages;
	public JPanel pnlP1CardSpace;
	public JPanel pnlP2CardSpace;
	public JPanel pnlP3CardSpace;
	public JPanel pnlP4CardSpace;
	
	public JLabel lblP1Points;
	public JLabel lblP2Points;
	public JLabel lblP3Points;
	public JLabel lblP4Points;
	public CardView deck;
	
	public PlayerView pView2;
	public PlayerView pView3;
	public PlayerView pView4;
	public JLabel lblNumberOfCardsLeft;
	public JScrollPane scrollPane;
	
	public GoFishPanel() {
		setBackground(new Color(0, 128, 0));
		setPreferredSize(new Dimension(1000, 800));
		
		topPanel = new JPanel();
		topPanel.setBackground(new Color(0, 128, 0));
		topPanel.setBounds(200, 0, 600, 200);
		
		bottomPanel = new JPanel();
		bottomPanel.setBackground(new Color(0, 128, 0));
		bottomPanel.setBounds(200, 600, 600, 200);
		
		leftPanel = new JPanel();
		leftPanel.setBackground(new Color(0, 128, 0));
		leftPanel.setBounds(0, 200, 200, 400);
		
		rightPanel = new JPanel();
		rightPanel.setBackground(new Color(0, 128, 0));
		rightPanel.setBounds(800, 200, 200, 400);
		
		JPanel labelPanel = new JPanel();
		labelPanel.setBackground(UIManager.getColor("Button.background"));
		labelPanel.setBounds(200, 400, 600, 200);
		labelPanel.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 600, 200);
		labelPanel.add(scrollPane);
		
		lblGameMessages = new JLabel("Game Messages");
		lblGameMessages.setHorizontalAlignment(SwingConstants.LEFT);
		scrollPane.setViewportView(lblGameMessages);
		setLayout(null);
		add(leftPanel);
		leftPanel.setLayout(null);
		
		pnlP3CardSpace = new JPanel();
		pnlP3CardSpace.setBackground(new Color(0, 128, 0));
		pnlP3CardSpace.setBounds(0, 0, 180, 400);
		leftPanel.add(pnlP3CardSpace);
		pnlP3CardSpace.setLayout(null);
		add(bottomPanel);
		bottomPanel.setLayout(null);
		
		pnlP1CardSpace = new JPanel();
		pnlP1CardSpace.setBackground(new Color(0, 128, 0));
		pnlP1CardSpace.setBounds(0, 20, 600, 180);
		bottomPanel.add(pnlP1CardSpace);
		pnlP1CardSpace.setLayout(null);
		
		lblP1Points = new JLabel("Player 1 Points");
		lblP1Points.setForeground(Color.BLACK);
		lblP1Points.setHorizontalAlignment(SwingConstants.CENTER);
		lblP1Points.setBounds(0, 0, 600, 20);
		bottomPanel.add(lblP1Points);
		add(topPanel);
		topPanel.setLayout(null);
		
		pnlP2CardSpace = new JPanel();
		pnlP2CardSpace.setBackground(new Color(0, 128, 0));
		pnlP2CardSpace.setBounds(0, 0, 600, 180);
		topPanel.add(pnlP2CardSpace);
		pnlP2CardSpace.setLayout(null);
		add(labelPanel);
		add(rightPanel);
		rightPanel.setLayout(null);
		
		pnlP4CardSpace = new JPanel();
		pnlP4CardSpace.setBackground(new Color(0, 128, 0));
		pnlP4CardSpace.setBounds(20, 0, 180, 400);
		rightPanel.add(pnlP4CardSpace);
		pnlP4CardSpace.setLayout(null);
		
		lblP3Points = new JLabel("Player 3 Points");
		lblP3Points.setForeground(Color.BLACK);
		lblP3Points.setHorizontalAlignment(SwingConstants.CENTER);
		lblP3Points.setBounds(0, 745, 200, 20);
		add(lblP3Points);
		
		lblP4Points = new JLabel("Player 4 Points");
		lblP4Points.setForeground(Color.BLACK);
		lblP4Points.setHorizontalAlignment(SwingConstants.CENTER);
		lblP4Points.setBounds(780, 160, 200, 20);
		add(lblP4Points);
		
		deck = new CardView();
		deck.setBounds(467, 250, 65, 105);
		add(deck);
		
		pView2 = new PlayerView();
		pView2.setBounds(50, 47, 100, 100);
		add(pView2);
		
		pView3 = new PlayerView();
		pView3.setBounds(50, 633, 100, 100);
		add(pView3);
		
		pView4 = new PlayerView();
		pView4.setBounds(836, 47, 100, 100);
		add(pView4);
		
		lblNumberOfCardsLeft = new JLabel("# Cards Left");
		lblNumberOfCardsLeft.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNumberOfCardsLeft.setForeground(Color.BLACK);
		lblNumberOfCardsLeft.setHorizontalAlignment(SwingConstants.CENTER);
		lblNumberOfCardsLeft.setBounds(450, 375, 100, 14);
		add(lblNumberOfCardsLeft);
		
		lblP2Points = new JLabel("Player 2 Points");
		lblP2Points.setBounds(0, 160, 200, 20);
		add(lblP2Points);
		lblP2Points.setForeground(Color.BLACK);
		lblP2Points.setHorizontalAlignment(SwingConstants.CENTER);
	}
}
