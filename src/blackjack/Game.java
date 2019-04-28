/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;

import java.util.Stack;

public class Game{
	private int startingMoney;
	private int count;
    private int noOfDecks;
    private int noOfPlayers;
    private Player[] players;  // user player is index 0, computer
    private Dealer dealer;
    private boolean togglePeek;
    private boolean toggleCount;
    // start values?
    // player bets?
    private Stack<Card> shoe;
    private int increment;	// stores what increment the blackjack game is on.
    private Blackjack uiInstance;
    
    //constructor method
    public Game(int startingMoneyInp, int playerNumberInp, int deckNumberInp, 
    		boolean peekInp, boolean countInp, Blackjack instance) 
    {
    	uiInstance = instance;
    	startingMoney = startingMoneyInp;
    	noOfPlayers = playerNumberInp;
    	noOfDecks = deckNumberInp;
    	toggleCount = countInp;
    	togglePeek = peekInp;
    	
    	// init shoe
    	shoe = new Stack<Card>();
    	Deck[] decks = new Deck[noOfDecks];
    	for(int i=0; i<noOfDecks; i++) {
    		decks[i] = new Deck(i);
    	}
    	for(int i=0; i<52; i++) {
    		for(int j=0; j<=noOfDecks-1; j++) {
    			Card card = decks[j].shoe();
    			if(j == 0) System.out.println(card.getTitle() + " " + card.getSuit());
    			shoe.push(card);
    		}
    	}
    	
    	// init dealer
    	dealer = new Dealer();
    	
    	// init players
    	players = new Player[noOfPlayers];
    	for(int i=0; i<noOfPlayers; i++) {
    		System.out.println("Adding player " +(i + 1));
    		players[i] = new Player(startingMoney);
    	}    	
    	
    	//init count
    	if(toggleCount) {
    		count = 0;
    	}
    	
    	//start to run game
    	increment = 0;
    	boolean playerAliveFlag = true;
    	while(shoe.size() >= noOfPlayers*5 && (players[0] != null && (playerAliveFlag = true))) {
    		this.runIncrement();
    		increment++;
    		// check for players with money
    		playerAliveFlag = false;
    		for(int i=1; i<players.length; i++) {
    			if(players[i] != null) {
    				playerAliveFlag = true;
    			}
    		}
    		if(players[0] == null) {
    			playerAliveFlag = false;
    		}
    		for(Player p: players) {
    			if(p == null) {
    				continue;
    			}
    			p.emptyHand();
    		}
    		dealer.emptyHand();
    	}
    }
    
    // getters
    
    public int getNoOfDecks(){
        return noOfDecks;
    }
    
    public int getNoOfPlayers(){
        return noOfPlayers;
    }
    
    public boolean getTogglePeek(){
        return togglePeek;
    }
    
    // Setters
    
    //public void setBetButtonFlag() {
    //	betButtonFlag = true;
    //	System.out.println("Button Flag activated\n");
    //}
    
    // methods
    
    public void runIncrement(){
    	dealer.pushCard(shoe.pop());
    	dealer.pushCard(shoe.pop());
    	
    	for(Player p: players) {
    		p.pushCard(shoe.pop());
			p.pushCard(shoe.pop());
    	}
    	
		Card [] userHand = players[0].getHand();
		
		userHand = players[0].getHand();
		for(Card c: userHand) {
			if(c == null) {
				continue;
			}
			else {
				this.writeToConsole(c.getTitle() + c.getSuit() + " ");
			}
		}
		uiInstance.writeToConsole("\n");
		
    	this.writeToConsole("Betting Phase: \n");
    	for(int i=1; i<players.length; i++) {
    		int playerBet = players[i].makeBet();
    		this.writeToConsole("Player " + (i+1) + " has made a bet of " + playerBet + "!\n");
    	}
    	this.writeToConsole("Please make a bet\n");
    	uiInstance.setBetFlag();
    	while((uiInstance.getBetFlag() == false) | (players[0].getCurrentBet() >= players[0].getCash())) {
    		uiInstance.setBetFlag();
    		try {
    			Thread.sleep(500);
    		} catch(InterruptedException e) {
    			e.printStackTrace();
    		}
    		int userBet = uiInstance.getUserBet();
    		if((userBet > players[0].getCash()) && (uiInstance.getBetFlag() == true)) {
    			uiInstance.setBetFlag();
    			this.writeToConsole("You do not have enough money to make this bet\n");
    			players[0].setBet();
    		}
    	}
    	int userBet = uiInstance.getUserBet();
    	this.writeToConsole("You made a bet of " + userBet +"\n");
    	players[0].setCurrentBet(userBet);
    	uiInstance.updatePlayerCash(players[0].getCash());
    	
    	this.writeToConsole("Game Phase:\n");
    	
    	// hit and sticks from computer players
    	for(int i=1; i<players.length; i++) {
    		if(players[i] == null) {
    			continue;
    		}
    		boolean stickFlag = false;
    		while(stickFlag == false) {
    			boolean decision = players[i].hitStickDecide();
    			if(decision == false) {
    				int success = players[i].pushCard(shoe.pop());
    				switch(success){
    				case 0:
    					this.writeToConsole("Player " + (i+1) + " cannot hit, Forced to stick\n");
    					stickFlag = true;
    					break;
    				case 1:
    					this.writeToConsole("Player " + (i+1) + " Chose to hit\n");
    					break;
    				}	
    			} else {
    				this.writeToConsole("Player " + (i+1) + " Chose to stick\n");
    				stickFlag = true;
    			}
    			
    		}
    		if(players[i].getSum() > 21) {
    			this.writeToConsole("Player " + (i+1) + " Went bust\n");
    		}
    	}
    	
    	// hit and stick for player
    	this.writeToConsole("Current Hand: " + userHand[0].getTitle() + userHand[0].getSuit() + " "
    			+ userHand[1].getTitle() + userHand[1].getSuit() + "\n");
    	this.writeToConsole("Please Hit or Stick\n");
    	uiInstance.setStickFlag(false);
    	while(uiInstance.getStickFlag() == false) {
    		uiInstance.setHitFlag();  // resets hit flag
    		while(uiInstance.getHitFlag() == false && uiInstance.getStickFlag() == false) {
    			try{
    				Thread.sleep(500);
    			} catch(InterruptedException e) {
    				e.printStackTrace();
    			}
    		}
    		if(uiInstance.getHitFlag()) {
	    		int success = players[0].pushCard(shoe.pop());
	    		switch(success) {
	    		case 0:
	    			this.writeToConsole("You already have 5 cards, you're forced to stick \n");
	    			uiInstance.setStickFlag(true);
	    			break;
	    		case 1:
	    			this.writeToConsole("You chose to hit\n");
	    			this.writeToConsole("Your Hand: ");
	    			userHand = players[0].getHand();
	    			for(Card c: userHand) {
	    				if(c == null) {
	    					continue;
	    				}
	    				else {
	    					uiInstance.writeToConsole(c.getTitle() + c.getSuit() + " ");
	    				}
	    			}
	    			uiInstance.writeToConsole("\n");
	    			break;
	    		}
	    		if(players[0].getSum() > 21) {
	    			uiInstance.setStickFlag(true);
	    			uiInstance.writeToConsole("Players sum is over 21, player went bust" + "\n");
	    		}
    		}
    	}
    	this.writeToConsole("You stuck with a total score of: " + players[0].getSum()+"\n");
    	
    	// hit and stick for dealer
    	this.writeToConsole("Dealer's Turn\n");
    	boolean stickFlag = false;
    	while(stickFlag == false) {
    		boolean stickDecision = dealer.hitStickDecide();
    		if(stickDecision == false) {
    			int cardPushSuccess = dealer.pushCard(shoe.pop());
    			if(cardPushSuccess == 0) {
    				this.writeToConsole("Dealer has the maximum of five cards, Forced to stick\n");
    				stickFlag = true;
    			} else{
    				this.writeToConsole("Dealer chose to hit\n");
    			}
    		} else {
    			this.writeToConsole("Dealer chose to stick\n");
    			stickFlag = true;
    		}
    	}
    	
    	// reveal scores and payout
    	for(int i=1; i<players.length; i++) {
    		if(players[i] == null) {
    			continue;
    		}
    		Card[] playerHand = players[i].getHand();
    		this.writeToConsole("Player " + (i+1) + "'s Hand: ");
    		for(Card c: playerHand) {
    			if(c == null){
    				continue;
    			}
    			this.writeToConsole(c.getTitle() + c.getSuit() + " ");
    			if(toggleCount) {
    				System.out.println("Count Activated");
					if(c.getValue() >= 10) {
						count--;
					}
					if(c.getValue() <= 6) {
						count++;
					}
				}
    		}
    		uiInstance.updateCardCount(count);
    		this.writeToConsole("\n");
    		
    		if(players[i].getSum() > 21) {
    			this.writeToConsole("Player " + (i+1) + " went bust\n");
    		}
    		else if(players[i].getSum() == dealer.getSum()) {
    			Card userBest = null;
    			Card dealerBest = null;
    			for(Card c: players[i].getHand()) {
    				if(c == null) {
    					continue;
    				}
    				if((userBest == null)) {
    					userBest = c;
    				}
    				else if(userBest.getPriority() < c.getPriority()) {
    					
    				}
    			}
    			for(Card c: dealer.getHand()) {
    				if(c == null) {
    					continue;
    				}
    				if((dealerBest == null)) {
    					dealerBest = c;
    				}
    				else if(dealerBest.getPriority() < c.getPriority()) {
    					dealerBest = c;
    				}
    			}
    			if(userBest.getPriority() > dealerBest.getPriority()) {
    				this.writeToConsole("You beat the stalemate with the " + userBest.getTitle() + userBest.getSuit() + "\n");
    			} else if (userBest.getPriority() < dealerBest.getPriority()) {
    				this.writeToConsole("The dealer beat your card in the stalemate with the " + dealerBest.getTitle() + dealerBest.getSuit() + "\n");
    			} else {
    				this.writeToConsole("Absolute Stalemate between you and the dealer, dealer wins by default");
    			}
    		}
    		else if(players[i].getSum() < dealer.getSum()) {
    			this.writeToConsole("The Dealer beat Player " + (i+1) + " with a score of " + dealer.getSum() + " against a score of " + players[i].getSum() + "\n");
    		}
    		else if(players[i].getSum() > dealer.getSum()) {
    			this.writeToConsole("Player " + (i+1) + " beat the Dealer with a score of " + players[i].getSum() + " against a score of " + dealer.getSum() + "\n");
    			players[i].payPlayer(players[i].getCurrentBet()*2);
    		}
    	}
    	
    	this.writeToConsole("Dealer's Final Hand: ");
		for(Card c: dealer.getHand()) {
			if(c == null){
				continue;
			}
			if(toggleCount) {
				System.out.println("Count Activated");
				if(c.getValue() >= 10) {
					count--;
				}
				if(c.getValue() <= 6) {
					count++;
				}
			}
			this.writeToConsole(c.getTitle() + c.getSuit() + " ");
		}
		this.writeToConsole("\n");
		uiInstance.updateCardCount(count);
    	
    	userHand = players[0].getHand();
    	this.writeToConsole("Your Final Hand: ");
		for(Card c: userHand) {
			if(c == null){
				continue;
			}
			if(toggleCount) {
				System.out.println("Count Activated");
				if(c.getValue() >= 10) {
					count--;
				}
				if(c.getValue() <= 6) {
					count++;
				}
			}
			this.writeToConsole(c.getTitle() + c.getSuit() + " ");
		}
		this.writeToConsole("\n");
		uiInstance.updateCardCount(count);
		if(players[0].getSum() > 21) {
			this.writeToConsole("You went bust\n");
		}
		else if(players[0].getSum() == dealer.getSum()) {
			Card userBest = null;
			Card dealerBest = null;
			for(Card c: userHand) {
				if(c == null) {
					continue;
				}
				if((userBest == null)) {
					userBest = c;
				}
				else if(userBest.getPriority() < c.getPriority()) {
					userBest = c;
				}
			}
			for(Card c: dealer.getHand()) {
				if(c == null) {
					continue;
				}
				if((dealerBest == null)) {
					dealerBest = c;
				}
				else if(dealerBest.getPriority() < c.getPriority()) {
					dealerBest = c;
				}
			}
			if(userBest.getPriority() > dealerBest.getPriority()) {
				this.writeToConsole("You beat the stalemate with the " + userBest.getTitle() + userBest.getSuit() + "\n");
			} else if (userBest.getPriority() < dealerBest.getPriority()) {
				this.writeToConsole("The dealer beat your card in the stalemate with the " + dealerBest.getTitle() + dealerBest.getSuit() + "\n");
			} else {
				this.writeToConsole("Absolute Stalemate between you and the dealer, dealer wins by default");
			}
		}
		else if(players[0].getSum() < dealer.getSum()) {
			this.writeToConsole("The Dealer beat you with a score of " + dealer.getSum() + " against a score of " + players[0].getSum() + "\n");
		}
		else if(players[0].getSum() > dealer.getSum()) {
			this.writeToConsole("You beat the Dealer with a score of " + players[0].getSum() + " against a score of " + dealer.getSum() + "\n");
			players[0].payPlayer(players[0].getCurrentBet()*2);
		}
    	uiInstance.updatePlayerCash(players[0].getCash());
    	
    	for(int i = 1; i<players.length; i++) {
    		if(players[i].getCash() <= 0) {
    			this.writeToConsole("Player " + (i+1) + " is out of money");
    			players[i] = null;
    			noOfPlayers--;
    		}
    	}
    }
    
    public void createGame(){
        
    }
    
    public void saveLog(){
    
    }
    
    public void endGame(){

    }
    
    public void writeToConsole(String s) {
    	uiInstance.writeToConsole(s);
    }
}
