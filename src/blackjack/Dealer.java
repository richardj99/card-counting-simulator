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
    private int sum;
    private Card[] hand;
    private boolean stickFlag;
    private int noOfCards;
    
    public Dealer(){
        sum = 0;
        hand = new Card[5];
    }
    
    //todo: setter methods, logic for hit and stick, hand
    
    // getters
    public Card[] getHand(){
        return hand;
    }
    
    public int getSum(){
        return sum;
    }
    
    public boolean getStick(){
        return stickFlag;
    }
    
    // setters
    
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
    
    public int pushCard(Card newCard) {
    	if(noOfCards<5) {
    		sum = sum + newCard.getValue();
    		System.out.println("New Sum " + sum);
    		for(int i=0; i<=5; i++) {
    			if(hand[i] == null) {
    				System.out.println("Adding card to index " + i);
    				hand[i] = newCard;
    				noOfCards++;
    				break;
    			}
    		}
    		return 1;
    	} else return 0;
    }
    
    public boolean hitStickDecide() {
    	boolean stick; // returns true if player wants to stick
    	if(sum > 14) {
    		stick = true;
    	}
    	else {
    		stick = false;
    	}
    	return stick;
    }
    
    public void emptyHand() {
    	hand = new Card[5];
    	noOfCards = 0;
    	sum = 0;
    }
}
