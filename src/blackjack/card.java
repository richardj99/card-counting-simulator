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
public class card {
    private short priority;  // Shows suit and card priority
    private int value;
    private char suit;
    private String title;
    
    public card(int inputTitle, char suitInput) {
    	title = Integer.toString(inputTitle);
    	value = inputTitle;
    }
    
    public card(String inputTitle, char suitInput) {
    	title = inputTitle;
    	if(inputTitle.equals("A")) value = 11;
    	else value = 10;    	
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
    
    // setters
    public void setPriority(int newPriority){
        this.priority = newPriority;
    }
    
    public void setValue(int newValue){
        this.value = newValue;
    }
    
    public void setSuit(char newSuit){
        this.suit = newSuit;
    }
}
