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
public class Dealer {
    private int cash; // is this necessary if the dealer is implied to have an unlimited float?
    private int sum;
    private Card[] hand;
    private boolean stickFlag;
    
    public Dealer(){
        sum = 0;
    }
    
    //todo: setter methods, logic for hit and stick, hand
    
    // getters
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
    
    public void setStick(boolean newStick){
        this.stickFlag = newStick;
    }
    
    public void updateSum(){
        int tempSum = 0;
        for(int i=0; i<hand.length; i++){
            tempSum = tempSum + hand[i].getValue();
        }
        setSum(tempSum);
    }
    
    // methods
    
    public void checkHitFloor(){
        if(sum > 14){
            stick();
        }else{
            hit();
        }
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
