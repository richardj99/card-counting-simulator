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
public class Player {
    
    private int sum;
    private int cash;
    private Card[] hand; //(what data structure to use? array of cards?)
    //private x profile; (what data structure to use? array of strings or booleans?)
    private int currentBet;
    private boolean stickFlag;
    
    // constructor method
    public Player(int startingCash){
        sum = 0;
        currentBet = 0;
        cash = startingCash;
    }
    
    // getters
    public Card[] getHand(){
        return hand;
    }
    
    public int getCash(){
        return cash;
    }
    
    public int getSum(){
        return sum;
    }
    
    public boolean getStick(){
        return stickFlag;
    }
    
    // setters
    
    
    public void setCash(int newCash){
        this.cash = newCash;
    }
    
    public void setSum(int newSum){
        this.sum = newSum;
    }
    
    public void updateSum(){
        int tempSum = 0;
        for(int i=0; i<hand.length; i++){
            tempSum = tempSum + hand[i].getValue();
        }
        setSum(tempSum);
    }
    
    public void setStick(boolean newStick){
        this.stickFlag = newStick;
    }
    
    // methods
    public void makeBet(int betAmount){
        cash = cash - betAmount;
        currentBet = betAmount;
    }
    
    public void hit(){
        setStick(false);
        // Todo: draw from deck and add to hand
        
        updateSum();
    }
    
    public void stick(){
        setStick(true);
    }
}
