package blackjack;

import java.util.Collections;
import java.util.Stack;

/**
 *
 * @author SLAD Team 3, Rory Gee, Shafqat 
 */
public class Deck {
    private int deckNo;
    private Stack<Card> cards;
    // suits array stores all possible suits values
    private char[] suits = {("S").charAt(0), ("H").charAt(0), ("C").charAt(0), ("D").charAt(0)};
    // picCardValues array stores all possible values of the picture cards.
    private String[] picCardValues = {("J"), ("Q"), ("K"), ("A")};
    //private x cards; use array of cards
    
    // constructor method
    public Deck(int chosenDeckNo){
    	deckNo = chosenDeckNo;
    	cards = new Stack<Card>();
    	for(char c: suits) {
    		for(int i=1; i<=10; i++) {
    			cards.add(new Card(i, c));
    		}
    		for(String s: picCardValues) {
    			cards.add(new Card(s, c));
    		}
    	}
    	this.shuffle();
    	
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
    // pops cards out of deck while building shoe.
    public Card shoe(){
    	return cards.pop();
    }
    
    // implements Collection shuffle on cards stack
    public void shuffle(){
    	Collections.shuffle(cards);
    }
}
