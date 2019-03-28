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
public class dealer {
    //private x hand; (what data structure to use? array of cards?)
    private int cash;
    private int sum;
    
    //todo: setter methods, logic for hit and stick, hand
    
    // getters
    public int getCash(){
        return cash;
    }
    public int getSum(){
        return sum;
    }
    
    // setters
    public void setCash(int newCash){
        this.cash = newCash;
    }
    
    public void setSum(int newSum){
        this.sum = newSum;
    }
    
    // methods
    public void hit(){
    }
    
    public void stick(){
    }
}
