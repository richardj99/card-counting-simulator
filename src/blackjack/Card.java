/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;


public class Card {
    private int priority;  // Shows suit and card priority values range between 0-19. Simply quantify any card's priority.
    private int value;  // value of the card, values range from 1-11
    private char suit;  // suit of the card: e.g. C, S, H, D
    private String title;  // Shows title of the card: E.g. 10, A, Q, 9 etc.
    
    // start of constructor method for number cards (pass integer and character)
    public Card(int titleInput, char suitInput) {
    	title = Integer.toString(titleInput);
    	value = titleInput;
    	suit = suitInput;
    	// determines priority from suit input
    	switch(Character.toString(suitInput)) {
    	case "S": priority = 3; break;
    	case "H": priority = 2; break;
    	case "C": priority = 1; break;
    	case "D": priority = 0; break;
    	default: System.err.println("Error in Suit determination: Card Class");
    	}
    }
    
    // start of constructor method for picture cards (pass String and character)
    public Card(String titleInput, char suitInput) {
    	title = titleInput;
    	suit = suitInput;
    	// determines priority from suit input
    	switch(Character.toString(suitInput)) {
    	case "S": priority = 3; break;
    	case "H": priority = 2; break;
    	case "C": priority = 1; break;
    	case "D": priority = 0; break;
    	default: System.err.println("Error in Suit determination: Card Class"); break;
    	}
    	// determines priority from picture type, adds value on to suit priority value
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
