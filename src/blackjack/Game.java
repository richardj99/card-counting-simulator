/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    	boolean playerAliveFlag = true;
    	while(shoe.size() >= noOfPlayers*5 && (players[0] != null && (playerAliveFlag = true))) {
    		this.runIncrement();
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
    	this.endGame();
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
    	Card c = shoe.pop();
		int index = dealer.pushCard(c);
		uiInstance.addCard(c.getSuit(), c.getTitle(), -1, index);
		c = null;
		c = shoe.pop();
		index = dealer.pushCard(c);
    	if(togglePeek) {
    		uiInstance.addCard(c.getSuit(), c.getTitle(), -1, index);
    		dealer.pushCard(shoe.pop());
    	}
    	else {
    		uiInstance.addCard(-1, index);
    	}
    	
    	c = shoe.pop();
    	index = players[0].pushCard(c);
    	uiInstance.addCard(c.getSuit(), c.getTitle(), 0, index);
    	c = shoe.pop();
    	index = players[0].pushCard(c);
    	uiInstance.addCard(c.getSuit(), c.getTitle(), 0, index);
    	for(int i=1; i<players.length; i++) {
    		c = shoe.pop();
    		index = players[i].pushCard(c);
    		if(togglePeek) {
    			uiInstance.addCard(c.getSuit(), c.getTitle(), i, index);
    		} else {
    			uiInstance.addCard(i, index);
    		}
    		c = shoe.pop();
			index = players[i].pushCard(shoe.pop());
			if(togglePeek) {
				uiInstance.addCard(c.getSuit(), c.getTitle(), i, index);
			}else {
				uiInstance.addCard(i, index);
			}
    	}
		Card [] userHand = players[0].getHand();
		userHand = players[0].getHand();
		for(Card card: userHand) {
			if(card == null) {
				continue;
			}
			else {
				this.writeToConsole(card.getTitle() + card.getSuit() + " ");
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
    				c = shoe.pop();
    				index = players[i].pushCard(c);
    				if(togglePeek) {
    					uiInstance.addCard(c.getSuit(), c.getTitle(), i, index);
    				} else {
    					uiInstance.addCard(i, index);
    				}
    				switch(index){
    				case -1:
    					this.writeToConsole("Player " + (i+1) + " cannot hit, Forced to stick\n");
    					stickFlag = true;
    					break;
    				default:
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
	    		c = shoe.pop();
    			index = players[0].pushCard(c);
	    		uiInstance.addCard(c.getSuit(), c.getTitle(), 0, index);
	    		switch(index) {
	    		case -1:
	    			this.writeToConsole("You already have 5 cards, you're forced to stick \n");
	    			uiInstance.setStickFlag(true);
	    			break;
	    		default:
	    			this.writeToConsole("You chose to hit\n");
	    			this.writeToConsole("Your Hand: ");
	    			userHand = players[0].getHand();
	    			for(Card card: userHand) {
	    				if(card == null) {
	    					continue;
	    				}
	    				else {
	    					uiInstance.writeToConsole(card.getTitle() + card.getSuit() + " ");
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
    			c = shoe.pop();
    			index = dealer.pushCard(shoe.pop());
    			if(togglePeek) {
    				uiInstance.addCard(c.getSuit(), c.getTitle(), -1, index);
    			}else {
    				uiInstance.addCard(-1, index);
    			}
    			if(index == 0) {
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
    		for(Card card: playerHand) {
    			if(card == null){
    				continue;
    			}
    			this.writeToConsole(card.getTitle() + card.getSuit() + " ");
    			if(toggleCount) {
    				System.out.println("Count Activated");
					if(card.getValue() >= 10) {
						count--;
					}
					if(card.getValue() <= 6) {
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
    			for(Card card: players[i].getHand()) {
    				if(card == null) {
    					continue;
    				}
    				if((userBest == null)) {
    					userBest = card;
    				}
    				else if(userBest.getPriority() < card.getPriority()) {
    					
    				}
    			}
    			for(Card card: dealer.getHand()) {
    				if(card == null) {
    					continue;
    				}
    				if((dealerBest == null)) {
    					dealerBest = card;
    				}
    				else if(dealerBest.getPriority() < card.getPriority()) {
    					dealerBest = card;
    				}
    			}
    			if(userBest.getPriority() > dealerBest.getPriority()) {
    				this.writeToConsole("Player " + (i+1) +" beat the stalemate with the " + userBest.getTitle() + userBest.getSuit() + "\n");
    			} else if (userBest.getPriority() < dealerBest.getPriority()) {
    				this.writeToConsole("The dealer beat Player " + (i+1) + "'s card in the stalemate with the " + dealerBest.getTitle() + dealerBest.getSuit() + "\n");
    			} else {
    				this.writeToConsole("Absolute Stalemate between Player" + (i+1) + "and the dealer, dealer wins by default");
    			}
    		}
    		else if(players[i].getSum() < dealer.getSum() && dealer.getSum() <= 21) {
    			this.writeToConsole("The Dealer beat Player " + (i+1) + " with a score of " + dealer.getSum() + " against a score of " + players[i].getSum() + "\n");
    		}
    		else if(players[i].getSum() > dealer.getSum() | dealer.getSum() > 21) {
    			this.writeToConsole("Player " + (i+1) + " beat the Dealer with a score of " + players[i].getSum() + " against a score of " + dealer.getSum() + "\n");
    			players[i].payPlayer(players[i].getCurrentBet()*2);
    		}
    	}
    	
    	this.writeToConsole("Dealer's Final Hand: ");
		for(Card card: dealer.getHand()) {
			if(card == null){
				continue;
			}
			if(toggleCount) {
				System.out.println("Count Activated");
				if(card.getValue() >= 10) {
					count--;
				}
				if(card.getValue() <= 6) {
					count++;
				}
			}
			this.writeToConsole(card.getTitle() + card.getSuit() + " ");
		}
		this.writeToConsole("\n");
		uiInstance.updateCardCount(count);
    	
    	userHand = players[0].getHand();
    	this.writeToConsole("Your Final Hand: ");
		for(Card card: userHand) {
			if(card == null){
				continue;
			}
			if(toggleCount) {
				System.out.println("Count Activated");
				if(card.getValue() >= 10) {
					count--;
				}
				if(card.getValue() <= 6) {
					count++;
				}
			}
			this.writeToConsole(card.getTitle() + card.getSuit() + " ");
		}
		this.writeToConsole("\n");
		uiInstance.updateCardCount(count);
		if(players[0].getSum() > 21) {
			this.writeToConsole("You went bust\n");
		}
		else if(players[0].getSum() == dealer.getSum()) {
			Card userBest = null;
			Card dealerBest = null;
			for(Card card: userHand) {
				if(card == null) {
					continue;
				}
				if((userBest == null)) {
					userBest = card;
				}
				else if(userBest.getPriority() < card.getPriority()) {
					userBest = card;
				}
			}
			for(Card card: dealer.getHand()) {
				if(card == null) {
					continue;
				}
				if((dealerBest == null)) {
					dealerBest = card;
				}
				else if(dealerBest.getPriority() < card.getPriority()) {
					dealerBest = card;
				}
			}
			if(userBest.getPriority() > dealerBest.getPriority()) {
				this.writeToConsole("You beat the stalemate with the " + userBest.getTitle() + userBest.getSuit() + "\n");
				players[0].payPlayer(players[0].getCurrentBet());
			} else if (userBest.getPriority() < dealerBest.getPriority()) {
				this.writeToConsole("The dealer beat your card in the stalemate with the " + dealerBest.getTitle() + dealerBest.getSuit() + "\n");
			} else {
				this.writeToConsole("Absolute Stalemate between you and the dealer, dealer wins by default");
			}
		}
		else if(players[0].getSum() < dealer.getSum() && dealer.getSum() <= 21) {
			this.writeToConsole("The Dealer beat you with a score of " + dealer.getSum() + " against a score of " + players[0].getSum() + "\n");
		}
		else if(players[0].getSum() > dealer.getSum() | dealer.getSum() > 21) {
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
    	this.saveLog();
    	try{
    		Thread.sleep(10000);
    	} catch(InterruptedException e) {
    		;
    	}
    	uiInstance.clearTable();
    }
    
    public void saveLog(){
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		LocalDateTime now = LocalDateTime.now();
		//System.out.println(dtf.format(now)); //2016/11/16 12:08:43
		File newFile = new File(dtf.format(now));
		try{
			PrintWriter writer = new PrintWriter(dtf.format(now)+".txt", "UTF-8");
			writer.print(uiInstance.outputConsole());
			writer.close();
			this.writeToConsole("File Saved Successfully\n");
		} catch(IOException e) {
			e.printStackTrace();
			this.writeToConsole("Issue saving to file\n");
		}
		
    }
    
    public void endGame(){
    	this.writeToConsole("Game finished");
    	this.saveLog();
    	uiInstance.clearTable();

    }
    
    public void writeToConsole(String s) {
    	uiInstance.writeToConsole(s);
    }
}
