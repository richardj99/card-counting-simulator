package blackjack;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.io.*;
import java.util.ArrayList;
/**
 *
 * @author Rory
 */
public class Blackjack extends JFrame{
	private static Blackjack blackjackInstance;

    private final int TABLE_INITIAL_WIDTH = 1000;
    private final int TABLE_INITIAL_HEIGHT = 700;
    private final int CONTROL_PANEL_WIDTH = 200;
    private final int MESSAGE_AREA_HEIGHT = 100;
    private Game gameInstance;
    private boolean hitButtonFlag, betButtonFlag, stickButtonFlag;
    private Thread gameThread;
    
    class tableArea extends JPanel
    {

        private void setbackground(Color black) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        ;
    }
    
    private tableArea table;
    private JPanel countPanel, playerOnePanel, playerTwoPanel, playerThreePanel, playerFourPanel, playerFivePanel, dealerPanel;
    private ArrayList<JPanel> dealerCards, playerOneCards, playerTwoCards, playerThreeCards, playerFourCards, playerFiveCards;
    private JPanel controlPanel, tablePlayArea;
    private JLabel playerMoneyLabel, countLabel;
    //private JRadioButton ;
    private JSlider numberOfPlayers, numberOfDecks;
    private JCheckBox cardCounting, deckPeaking;
    private JButton clearButton, hitButton, stickButton, makeBetButton, newGameButton;
    private JTextArea messageArea;
    private JSpinner startingMoneySpinner, betSizeSpinner;
    
    public Blackjack()
    {
        setTitle("Current_Drawing_Application");
        setLayout(new BorderLayout());  // Layout manager for the frame.
        
        // table
        table = new tableArea();
          table.setBorder(new TitledBorder(new EtchedBorder(), "table"));
          table.setPreferredSize(new Dimension(TABLE_INITIAL_WIDTH, TABLE_INITIAL_HEIGHT));
        add(table, BorderLayout.CENTER);
        tablePlayArea = new JPanel();
        tablePlayArea.setBackground(new Color(0, 128, 0));
        tablePlayArea.setPreferredSize(new Dimension(TABLE_INITIAL_WIDTH - 20, TABLE_INITIAL_HEIGHT - 30));
        
        table.add(tablePlayArea);
        
        // Control Panel
        controlPanel = new JPanel();
          controlPanel.setBorder(new TitledBorder(new EtchedBorder(), "Control Panel"));
          controlPanel.setPreferredSize(new Dimension(CONTROL_PANEL_WIDTH, TABLE_INITIAL_HEIGHT));
          // the following two lines put the control panel in a scroll pane (nicer?).      
          JScrollPane controlPanelScrollPane = new JScrollPane(controlPanel);
          controlPanelScrollPane.setPreferredSize(new Dimension(CONTROL_PANEL_WIDTH + 30, TABLE_INITIAL_HEIGHT));
        add(controlPanelScrollPane, BorderLayout.LINE_START);        

        
        // Control Panel contents are specified in the next section 
        
        // Count Panel
        countPanel  =  new JPanel();
        countPanel.setBorder(new TitledBorder(new EtchedBorder(), "Current Count"));
        countPanel.setPreferredSize(new Dimension(CONTROL_PANEL_WIDTH - 20, 50 ));
        countLabel = new JLabel();
        countPanel.add(countLabel);
        countLabel.setText("0");
        controlPanel.add(countPanel);
        countPanel.setVisible(false);
        
        // Player cash
        JPanel playerMoneyPanel = new JPanel();
        	playerMoneyPanel.setBorder(new TitledBorder(new EtchedBorder(), "Player Money"));
        	playerMoneyPanel.setPreferredSize(new Dimension(CONTROL_PANEL_WIDTH - 20, 50 ));
        	playerMoneyLabel = new JLabel();
        	playerMoneyPanel.add(playerMoneyLabel);
        	playerMoneyLabel.setText(" ");
        	controlPanel.add(playerMoneyPanel);
        	
        // Number of players
        JPanel numberOfPlayersPanel = new JPanel();
            numberOfPlayersPanel.setBorder(new TitledBorder(new EtchedBorder(), "Number of Players"));
            numberOfPlayersPanel.setPreferredSize(new Dimension(CONTROL_PANEL_WIDTH - 20, 90));
                numberOfPlayers = new JSlider(1, 5, 2);
                numberOfPlayers.setMajorTickSpacing(1);
                numberOfPlayers.setMinorTickSpacing(1);
                numberOfPlayers.setPaintTrack(true); 
                numberOfPlayers.setPaintTicks(true); 
                numberOfPlayers.setPaintLabels(true);
                numberOfPlayers.setPreferredSize(new Dimension(CONTROL_PANEL_WIDTH - 30, 60));
            numberOfPlayersPanel.add(numberOfPlayers);
        controlPanel.add(numberOfPlayersPanel);
        
        
        //Number of Decks
        JPanel numberOfDecksPanel = new JPanel();
            numberOfDecksPanel.setBorder(new TitledBorder(new EtchedBorder(), "Number of Decks in shoe"));
            numberOfDecksPanel.setPreferredSize(new Dimension(CONTROL_PANEL_WIDTH - 20, 90));
                numberOfDecks = new JSlider(1, 4, 1);
                numberOfDecks.setMajorTickSpacing(1);
                numberOfDecks.setMinorTickSpacing(1);
                numberOfDecks.setPaintTrack(true); 
                numberOfDecks.setPaintTicks(true); 
                numberOfDecks.setPaintLabels(true);
                numberOfDecks.setPreferredSize(new Dimension(CONTROL_PANEL_WIDTH - 30, 60));
            numberOfDecksPanel.add(numberOfDecks);
        controlPanel.add(numberOfDecksPanel);
        
        
        // Cheat Methods Panel
        JPanel cheatMethodsPanel = new JPanel();
            cheatMethodsPanel.setBorder(new TitledBorder(new EtchedBorder(), "Cheating Methods"));
            cheatMethodsPanel.setPreferredSize(new Dimension(CONTROL_PANEL_WIDTH - 20, 90));
            cardCounting = new JCheckBox("Card Counting");
            deckPeaking = new JCheckBox("Deck Peaking");
            cheatMethodsPanel.add(cardCounting);
            cheatMethodsPanel.add(deckPeaking);
        controlPanel.add(cheatMethodsPanel);
        
        //Starting Money Panel
        JPanel startingMoneyPanel = new JPanel();
        	startingMoneyPanel.setBorder(new TitledBorder(new EtchedBorder(), "Starting Money"));
        	startingMoneyPanel.setPreferredSize(new Dimension(CONTROL_PANEL_WIDTH - 20, 50));
        	SpinnerNumberModel startingMoneySpinnerModel = new SpinnerNumberModel(50, 20, 100, 5);
        	startingMoneySpinner = new JSpinner(startingMoneySpinnerModel);
        	startingMoneyPanel.add(startingMoneySpinner);
        controlPanel.add(startingMoneyPanel);
        	
        //Start Game button
        newGameButton = new JButton("New Game");
        	newGameButton.setPreferredSize(new Dimension(CONTROL_PANEL_WIDTH - 20, 50));
        controlPanel.add(newGameButton);
        newGameButton.addActionListener(new newGameActionListener());
        

        // Clear button
        clearButton = new JButton("Clear Table");
          clearButton.setPreferredSize(new Dimension(CONTROL_PANEL_WIDTH - 20, 50));
        controlPanel.add(clearButton);
        
        // Hit Button
        hitButton = new JButton("Hit");
          hitButton.setLocation(0, 400);
          hitButton.setSize(200, 50);
        tablePlayArea.add(hitButton);
        hitButton.addActionListener(new hitButtonActionListener());
        
        // Stick Button
        stickButton = new JButton("Stick");
          stickButton.setLocation(0, 0);
          stickButton.setSize(100, 50);
        tablePlayArea.add(stickButton);
        stickButton.addActionListener(new stickButtonActionListener());
        
        // bet size button
        JLabel betSizeLabel = new JLabel();
        betSizeLabel.setText("BetSize");
        SpinnerNumberModel betSizeSpinnerNumberModel = new SpinnerNumberModel(5, 5, 100, 5);
        betSizeSpinner = new JSpinner(betSizeSpinnerNumberModel);
        
     // Make Bet Button
        makeBetButton = new JButton("Make Bet");
        makeBetButton.setLocation(0,0);
        makeBetButton.setSize(100, 50);
     //tablePlayArea.add(makeBetButton);
        makeBetButton.addActionListener(new betButtonActionListener());
        
        JPanel betPanel = new JPanel();
        betPanel.setBorder(new TitledBorder(new EtchedBorder(), "Bet Size"));
        betPanel.setBackground(tablePlayArea.getBackground());
        betPanel.setPreferredSize(new Dimension(250, 55));
        betPanel.add(betSizeSpinner);
        betPanel.add(makeBetButton);
        tablePlayArea.add(betPanel);
        
        
        // player card locations;
        dealerPanel = new JPanel();
        dealerPanel.setBorder(new TitledBorder(new EtchedBorder(), "Dealer"));
        dealerPanel.setBackground(tablePlayArea.getBackground());
        dealerPanel.setPreferredSize(new Dimension(TABLE_INITIAL_WIDTH-20, 150));
        tablePlayArea.add(dealerPanel);
        
        playerOnePanel = new JPanel();
        playerOnePanel.setBorder(new TitledBorder(new EtchedBorder(), "Player 1 (You)"));
        playerOnePanel.setBackground(tablePlayArea.getBackground());
        playerOnePanel.setPreferredSize(new Dimension(TABLE_INITIAL_WIDTH-20, 150));
        tablePlayArea.add(playerOnePanel);
        
        playerTwoPanel = new JPanel();
        playerTwoPanel.setBorder(new TitledBorder(new EtchedBorder(), "Player 2"));
        playerTwoPanel.setBackground(tablePlayArea.getBackground());
        playerTwoPanel.setPreferredSize(new Dimension((TABLE_INITIAL_WIDTH-50)/2, 150));
        tablePlayArea.add(playerTwoPanel);
        
        playerThreePanel = new JPanel();
        playerThreePanel.setBorder(new TitledBorder(new EtchedBorder(), "Player 3"));
        playerThreePanel.setBackground(tablePlayArea.getBackground());
        playerThreePanel.setPreferredSize(new Dimension((TABLE_INITIAL_WIDTH-50)/2, 150));
        tablePlayArea.add(playerThreePanel);
        
        playerFourPanel = new JPanel();
        playerFourPanel.setBorder(new TitledBorder(new EtchedBorder(), "Player 4"));
        playerFourPanel.setBackground(tablePlayArea.getBackground());
        playerFourPanel.setPreferredSize(new Dimension((TABLE_INITIAL_WIDTH-50)/2, 150));
        tablePlayArea.add(playerFourPanel);
        
        playerFivePanel = new JPanel();
        playerFivePanel.setBorder(new TitledBorder(new EtchedBorder(), "Player 5"));
        playerFivePanel.setBackground(tablePlayArea.getBackground());
        playerFivePanel.setPreferredSize(new Dimension((TABLE_INITIAL_WIDTH-50)/2, 150));
        tablePlayArea.add(playerFivePanel);
        
     // init arrays
        dealerCards = new ArrayList<JPanel>();
        playerTwoCards = new ArrayList<JPanel>();
        playerThreeCards = new ArrayList<JPanel>();
        playerOneCards = new ArrayList<JPanel>();
        playerFourCards = new ArrayList<JPanel>();
        playerFiveCards = new ArrayList<JPanel>();
        for(int i=0; i<5; i++) {
        	JPanel panel = new JPanel();
        	panel.setPreferredSize(new Dimension((dealerPanel.getWidth()-10)/5, dealerPanel.getHeight()));
        	panel.setBackground(tablePlayArea.getBackground());
        	panel.setBorder(new TitledBorder(new EtchedBorder(), "Card 1"));
        	dealerCards.add(panel);
        	dealerPanel.add(panel);
        }
        
        // Message area
        messageArea = new JTextArea();
        messageArea.setEditable(false);
        messageArea.setBackground(table.getBackground());
        JScrollPane textAreaScrollPane = new JScrollPane(messageArea);
        textAreaScrollPane.setBorder(new TitledBorder(new EtchedBorder(), "Console"));
        textAreaScrollPane.setPreferredSize(new Dimension(CONTROL_PANEL_WIDTH + TABLE_INITIAL_WIDTH, MESSAGE_AREA_HEIGHT));
        add(textAreaScrollPane, BorderLayout.PAGE_END);

        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        pack();
        setVisible(true);
        
    }  // end of the DrawingApplication constructor method
    
    // getter methods
    
    public int getUserBet() {
    	return Integer.parseInt(betSizeSpinner.getValue().toString());
    }
    
    public boolean getBetFlag() {
    	return betButtonFlag;
    }
    
    public boolean getStickFlag() {
    	return stickButtonFlag;
    }
    
    public boolean getHitFlag() {
    	return hitButtonFlag;
    }
    
    // setter methods
    public void setHitFlag() {
    	hitButtonFlag = false;
    }
    
    public void setStickFlag(boolean input) {
    	stickButtonFlag = input;
    }
    
    public void setBetFlag() {
    	betButtonFlag = false;
    }
    
    // methods
    
    public void writeToConsole(String input) {
    	messageArea.append(input);
    	messageArea.setCaretPosition(messageArea.getDocument().getLength());
    	
    }
    
    public void updatePlayerCash(int cash) {
    	playerMoneyLabel.setText(Integer.toString(cash));
    }
    
    public void updateCardCount(int count) {
    	countLabel.setText(Integer.toString(count));
    }
    
    class newGameActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//try {
			
				int startingMoney = Integer.parseInt(startingMoneySpinner.getValue().toString());
				playerMoneyLabel.setText(Integer.toString(startingMoney));
				int playerNumber = numberOfPlayers.getValue();
				int deckNumber = numberOfDecks.getValue();
				boolean peek = deckPeaking.isSelected();
				boolean count = cardCounting.isSelected();
				countPanel.setVisible(count);
				new Thread() {
					public void run() {
						gameInstance = new Game(startingMoney, playerNumber, deckNumber, peek, count, blackjackInstance);
					}
				}.start();
			//}catch(Exception exc){
			//	messageArea.append("Error collecting Game Parameters");
			//	System.out.println(exc.toString());
			//}
			
		}
    	
    }
    
    class betButtonActionListener implements ActionListener{
    	
    	public void actionPerformed(ActionEvent e) {
    		betButtonFlag = true;
    	}
    }
    
    class hitButtonActionListener implements ActionListener{
    	
    	public void actionPerformed(ActionEvent e) {
    		hitButtonFlag = true;
    	}
    }
    
    class stickButtonActionListener implements ActionListener{
    	
    	public void actionPerformed(ActionEvent e) {
    		stickButtonFlag = true;
    	}
    }
    
    public static void main(String[] args) {
        blackjackInstance = new Blackjack();
    }
    
}
