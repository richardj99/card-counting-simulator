/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;

import java.util.Stack;

public class Game {
	private int count;
    private int noOfDecks;
    private int noOfPlayers;
    private Player[] players;  // user player is index 0, computer
    private boolean togglePeek;
    private boolean toggleCount;
    // start values?
    // player bets?
    private Stack<Card> shoe;
    
    //constructor method
    public Game() {
    	// init shoe
    	shoe = new Stack<Card>();
    	Deck[] decks = new Deck[noOfDecks];
    	for(int i=0; i<noOfDecks-1; i++) {
    		decks[i] = new Deck(i);
    	}
    	for(int i=0; i<=52; i++) {
    		for(int j=0; j<=noOfDecks-1; j++) {
    			shoe.push(decks[j].shoe());
    		}
    	}
    	
    	// init dealer
    	
    	
    	// init players
    	players = new Player[noOfPlayers];
    	for(int i=0; i<noOfPlayers; i++) {
    		players[i] = new Player();
    	}
    	// get whether the card count is enabled
    		// TODO: Waiting for UI to be complete.
    	// if card counting is an option, instantiate the count parameter
    	if(toggleCount) {
    		count = 0;
    	}
    	// get togglePeek settings
    	
    	
    }
    
    // getters
    
    public int getNoOfDecks(){
        return noOfDecks;
    }
    
    public int getNoOfPlayers(){
        return noOfPlayers;
    }
    
    public boolean getTogglePeek(){
        return togglePeek;
    }
    
    public int getToggleCount(){
        return toggleCount;
    }
    
    /* setters
    
    public void setNoOfDecks(int newNoOfDecks){
        this.noOfDecks = newNoOfDecks;
    }
    
    public void setNoOfPlayers(int newNoOfPlayers){
        this.noOfPlayers = newNoOfPlayers;
    }
    
    public void setTogglePeek(boolean newTogglePeek){
        this.togglePeek = newTogglePeek;
    }
    
    public void setCountPeek(int newCountPeek){
        this.countPeek = newCountPeek;
    }
    */
    
    // methods    
    
    public void createGame(){
        
    }
    
    public void writeToConsole(String message){
    	
    }
    
    public void saveLog(){
    
    }
    
    public void endGame(){
    
    }
}
