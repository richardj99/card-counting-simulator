package blackjack;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.io.*;
/**
 *
 * @author Rory
 */
public class Blackjack extends JFrame{

    private final int TABLE_INITIAL_WIDTH = 800;
    private final int TABLE_INITIAL_HEIGHT = 640;
    private final int CONTROL_PANEL_WIDTH = 200;
    private final int MESSAGE_AREA_HEIGHT = 100;
    
    class tableArea extends JPanel
    {

        private void setbackground(Color black) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        ;
    }
    
    private tableArea table;
    
    private JPanel controlPanel, tablePlayArea;
    //private JLabel coordinatesLabel;
    //private JRadioButton ;
    private JSlider numberOfPlayers, numberOfDecks;
    private JCheckBox cardCounting, deckPeaking;
    private JButton clearButton, hitButton, stickButton;
    private JTextArea messageArea;
    private JMenuBar menuBar;
    
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
        tablePlayArea.setBackground(Color.green);
        tablePlayArea.setPreferredSize(new Dimension(TABLE_INITIAL_WIDTH - 20, TABLE_INITIAL_HEIGHT - 30));
        
        table.add(tablePlayArea);
        
        // Menu bar
        menuBar = new JMenuBar();
          JMenu fileMenu = new JMenu("File");
            JMenuItem fileSaveMenuItem = new JMenuItem("Save");
            fileMenu.add(fileSaveMenuItem);
            JMenuItem fileLoadMenuItem = new JMenuItem("Load");
            fileMenu.add(fileLoadMenuItem);
            fileMenu.addSeparator();
            JMenuItem fileExitMenuItem = new JMenuItem("Exit");
            fileMenu.add(fileExitMenuItem);
          menuBar.add(fileMenu);
          JMenu helpMenu = new JMenu("Help");
            JMenuItem helpAboutMenuItem = new JMenuItem("About");
            helpMenu.add(helpAboutMenuItem);
          menuBar.add(helpMenu);
        add(menuBar, BorderLayout.PAGE_START);
        
        // Control Panel
        controlPanel = new JPanel();
          controlPanel.setBorder(new TitledBorder(new EtchedBorder(), "Control Panel"));
          controlPanel.setPreferredSize(new Dimension(CONTROL_PANEL_WIDTH, TABLE_INITIAL_HEIGHT));
          // the following two lines put the control panel in a scroll pane (nicer?).      
          JScrollPane controlPanelScrollPane = new JScrollPane(controlPanel);
          controlPanelScrollPane.setPreferredSize(new Dimension(CONTROL_PANEL_WIDTH + 30, TABLE_INITIAL_HEIGHT));
        add(controlPanelScrollPane, BorderLayout.LINE_START);        

        
        // Control Panel contents are specified in the next section 
        
        // Number of players
        JPanel numberOfPlayersPanel = new JPanel();
            numberOfPlayersPanel.setBorder(new TitledBorder(new EtchedBorder(), "Number of Players"));
            numberOfPlayersPanel.setPreferredSize(new Dimension(CONTROL_PANEL_WIDTH - 20, 90));
                numberOfPlayers = new JSlider(0, 4, 2);
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
                numberOfDecks = new JSlider(0, 4, 2);
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
        

        // Clear button
        clearButton = new JButton("Clear Table");
          clearButton.setPreferredSize(new Dimension(CONTROL_PANEL_WIDTH - 20, 50));
        controlPanel.add(clearButton);
        
        // Hit Button
        hitButton = new JButton("Hit");
          hitButton.setLocation(0, 400);
          hitButton.setSize(200, 50);
        tablePlayArea.add(hitButton);
        
        // Stick Button
        stickButton = new JButton("Stick");
          stickButton.setLocation(0, 0);
          stickButton.setSize(100, 50);
        tablePlayArea.add(stickButton);
        
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
    
    public static void main(String[] args) {
        // TODO code application logic here
    	// TODO swing ui
        Blackjack blackjackInstance = new Blackjack();
    }
    
}
