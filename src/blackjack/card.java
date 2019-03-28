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
    private int priority;
    private int value;
    private char suit;
    
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
