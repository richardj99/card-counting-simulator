/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;

import java.util.Random;

/**
 *
 * @author Rory
 */
public class Player {
    
    private int sum;
    private int cash;
    private Card[] hand; //(what data structure to use? array of cards?)
    private int noOfCards;
    //private x profile; (what data structure to use? array of strings or booleans?)
    private int currentBet;
    private boolean stickFlag;
    
    // constructor method
    public Player(int startingCash){
    	noOfCards = 0;
        sum = 0;
        currentBet = 0;
        cash = startingCash;
        hand = new Card[5];
    }
    
    //setters
    
    public void setBet() {
    	currentBet = 0;
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
    
    public int getCurrentBet() {
    	return currentBet;
    }
    
    // setter methods
    public void setCurrentBet(int betInput) {
    	currentBet = betInput;
    	cash = cash - currentBet;
    }
    
    // methods
    public int pushCard(Card newCard) {
    	int index = 0;
    	if(noOfCards<5) {
    		sum = sum + newCard.getValue();
    		System.out.println("New Sum " + sum);
    		for(int i=0; i<=5; i++) {
    			if(hand[i] == null) {
    				System.out.println("Adding card to index " + i);
    				hand[i] = newCard;
    				noOfCards++;
    				index = i;
    				break;
    			}
    		}
    		return index;
    	} else return -1;
    }
    
    public boolean hitStickDecide() {
    	if(sum > 14) {
    		stickFlag = true;
    	}
    	else {
    		stickFlag = false;
    	}
    	return stickFlag;
    }
    
    public int makeBet(){
    	int betAmount;
    	System.out.println(cash);
    	if(cash < 20) {
    		betAmount = cash;
    		cash = 0;
    	}
    	else {
    		Random randNumGen = new Random();
    		betAmount = randNumGen.nextInt(19) + 1;
    		System.out.println("Raw: Bet Amount " + betAmount);
    		cash = cash - betAmount;
    	}
    	currentBet = betAmount;
    	return betAmount;
    }
    
    public void payPlayer(int winnings) {
    	cash = cash + winnings;
    }
    
    public void emptyHand() {
    	hand = new Card[5];
    	noOfCards = 0;
    	sum = 0;
    }
}
