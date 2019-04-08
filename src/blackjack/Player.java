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
    //private x hand; (what data structure to use? array of cards?)
    //private x profile; (what data structure to use? array of strings or booleans?)
    private boolean stickFlag;
    
    
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
    
    // methods
    public void makeBet(){
    
    }
    
    public void hit(){
        
    }
    
    public void stick(){
    
    }
}
