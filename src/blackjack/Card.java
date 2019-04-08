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
public class Card {
    private int priority;  // Shows suit and card priority
    private int value;
    private char suit;
    private String title;
    
    public Card(int titleInput, char suitInput) {
    	title = Integer.toString(titleInput);
    	value = titleInput;
    	suit = suitInput;
    	switch(Character.toString(suitInput)) {
    	case "S": priority = 3; break;
    	case "H": priority = 2; break;
    	case "C": priority = 1; break;
    	case "D": priority = 0; break;
    	default: System.err.println("Error in Suit determination: Card Class");
    	}
    }
    public Card(String titleInput, char suitInput) {
    	title = titleInput;
    	suit = suitInput;
    	switch(Character.toString(suitInput)) {
    	case "S": priority = 3; break;
    	case "H": priority = 2; break;
    	case "C": priority = 1; break;
    	case "D": priority = 0; break;
    	default: System.err.println("Error in Suit determination: Card Class"); break;
    	}
    	switch(titleInput) {
    	case "J": priority = priority + 4; value = 10; break;
    	case "Q": priority = priority + 8; value = 10; break;
    	case "K": priority = priority + 12; value = 10; break;
    	case "A": priority = priority + 16; value = 11; break;
    	default: System.err.println("Error in Picture Card Determination: Card Class"); break;
    	}
    }
    
    // getters
    public int getPriority(){
        return priority;
    }
    
    public int getValue(){
        return value;
    }
    
    public char getSuit(){
        return suit;
    }
    
    public String getTitle() {
    	return title;
    }
    
    /*
    // setters
    public void setPriority(short newPriority){
        this.priority = newPriority;
    }
    
    public void setValue(int newValue){
        this.value = newValue;
    }
    
    public void setSuit(char newSuit){
        this.suit = newSuit;
    }
    */
}
