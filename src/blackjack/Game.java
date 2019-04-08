/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;

/**
 *
 * @author Rory
 */
public class Game {
    private int noOfDecks;
    private int noOfPlayers;
    private boolean togglePeek;
    private int countPeek;
    // start values?
    // player bets?
    // shoe?
    
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
    
    public int getCountPeek(){
        return countPeek;
    }
    
    // setters
    
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
    
    
    // methods
    
    public void createGame(){
        
    }
    
    public void writeToConsole(){
    
    }
    
    public void saveLog(){
    
    }
    
    public void endGame(){
    
    }
}
