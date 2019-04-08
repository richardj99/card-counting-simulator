/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;

/**
 *
 * @author SLAD Team 3, Rory Gee, Shafqat 
 */
public class deck {
    private int deckNo;
    //private x cards; use array of cards
    
    // constructor method
    public deck(int chosenDeckNo){
    	deckNo = chosenDeckNo;
    }// end of constructor method
    
    
    // getters
    public int getDeckNo(){
        return deckNo;
    }
    
    // TODO: Discuss whether this setter is necessary (deck number is decided on construction).
    // setters
    /*public void setDeckNo(int newDeckNo){
        this.deckNo = newDeckNo;
    } */
    
    // methods
    public void shoe(){
    }
    
    public void shuffle(){
    }
}
