/*
 * Student UCI ID: huanjial
 * Name: Huanjia Liang
 * Student ID No.: 10244014
 * 
 * This file has the class of the CrapsSimulation.
 * It constructs a crapsMetricsMonitor object and crapsGame object
 * and handles everything that is needed to simulate the craps game
 * continuously.
 */

package lab3;

import java.util.Scanner;

public class CrapsSimulation {
	CrapsMetricsMonitor cmm = new CrapsMetricsMonitor();
	CrapsGame crapsGame = new CrapsGame(cmm);
	
	private String name;
	private int money;
	private double bet;
	private double balance;
	private boolean playGame;
	private int loseStreak; 
	private int winStreak; 
	private boolean firstRound;
	private boolean previousGameSituation;
	private int thGame;
	Scanner input;
	
	//This is the construction of CrapsSimulation object. It initializes all local variables.
	CrapsSimulation(){
		this.input = new Scanner(System.in);
		this.cmm = new CrapsMetricsMonitor();
		this.crapsGame = new CrapsGame(cmm);	
		this.name = "";
		this.money = 0;
		this.bet = 0.0;
		this.balance = 0;
		this.playGame = true;
		this.loseStreak = 0; // This is the current lose streak
		this.winStreak = 0; // This is the current win streak
		this.firstRound = true;
		this.previousGameSituation = true;
		this.thGame = 0;
	}

	//the start() method is to use while loops to run the craps game's simulation
	//this method handles the whole simulation of the game.
	void start() 
		{
		while (playGame == true) 
			{
			if (firstRound == true) 
				{
				this.name = getName();
				this.money = getMoney();
				this.balance = money;
				this.bet = getBet(balance);
				}
			renewMaxBalanceAndThRound(cmm, thGame, balance);
			
			System.out.println(this.name + "'s balance: " + this.balance + ". Playing a new game..."+ this.name + " bets " + bet);
			boolean singleRunResult = crapsGame.playGame();
			thGame += 1;
			cmm.increaseNumOfGamesPlayed();
			// ================== lose
			if (singleRunResult == false) {
				losingCondition();
			}
			// ================= win	
			else {
				winningCondition();
			}
			renewMaxBalanceAndThRound(cmm, thGame, balance);
			resetGameResultTerms();
				
			if (balance <= 0) // because balance is <= 0 then getting out of the loop
				{
				noBalanceSituation();
				}
			else  // still has balance, ask the user wants to bet again
				{
				boolean betAgain = betAgain();
				if (betAgain == false) {
					cmm.printStatistics();
					boolean replayChoice = rePlay();
					if (replayChoice == false) 
						{
						terminateGame();
						}
					else
						{
						resetWholeGame();
						}
					}
				else {
					bet = getBet(balance);
					}
				}
		}
	}	

	//terminateGame() set the this.playGame as false, close the scanner, and print out the message to tell
	//users that the game is terminated.
	private void terminateGame() {
		this.playGame = false;
		this.input.close();
		System.out.println("The game is terminated");
	}
	
	//The noBalanceSituation() will be used when the user's has no balance.
	//It will check whether the last lose will result in the max lose streak or not.
	//Also it will ask the user whether they want to play this game again or not.
	private void noBalanceSituation() {
		printEndingBalance(name, balance);
		if (loseStreak > cmm.getMaxLosingStreak())  //see if the last lose may make the loseStreak be the max or not.
			{
			cmm.setMaxLosingStreak(loseStreak);
			}
		cmm.printStatistics();
		boolean playAgain = rePlay();
		if (playAgain == false) 
			{
			terminateGame();
			}
	
		else {
			resetWholeGame();
			}
			
	}
	
	//The printEndingBalance(String name, int balance) is a method to print out the player's balance at the
	//end of the game when the player loses all the money. Because it doesn't have the "Playing a new game..." sentence,
	//so it is supposed to be put at the end of the game that when the player loses all the money.
	private void printEndingBalance(String name, double balance) {
		System.out.println(name + "'s balance: " + balance + "\n\n");
	}
	
	//losingCondition() is a method that handles the situation that player loses in a single game.
	//first it will check if this round's game is first round. 
	//If it is, then because the game situation is lost, so the previousGameSituation will be set as false.
	//Doing this is to have a comparison between the first game and the next one, to give it the initialized game situation. 
	//then it will check is this game has the same result as the last game. If they have the same result, then it means the 
	//user keeps losing, the loseStreak increases 1. If it's not, then it means the player's last game is winning, and this game
	//is losing, then it will first check the winStreak to see if it is bigger then the max winStreak in the crapsMetricsMonitor.
	//if it is, then the max winStreak be updated to this winStreak.
	//if not, it does nothing.
	//Also, it resets the loseStreak as 1, restart counting the loseStreak, and set the previousGameSituation as false, so
	//we can use the next round game to compare with current losing game.
	//After dealing with the loseStreak, the code subtract the balance with the bet.
	//After that it will check out the private variables crapOut and craps in the crapsGame, to figure out whether it loses 
	//because of craps or craps out, and then print out the corresponding statement.
	private void losingCondition() {
		if (firstRound == true) {
			previousGameSituation = false;
			}	
		firstRound = false;
		if (previousGameSituation == false) {
			loseStreak += 1;
			if (loseStreak > cmm.getMaxLosingStreak()) {
				cmm.setMaxLosingStreak(loseStreak);
				}
			}
		else {
			if (winStreak > cmm.getMaxWinningStreak()) {
				cmm.setMaxWinningStreak(winStreak);
				}
			loseStreak = 1;
			if (loseStreak > cmm.getMaxLosingStreak()) {
				cmm.setMaxLosingStreak(loseStreak);
				}
			previousGameSituation = false;
			}
		balance -= bet;
		if (crapsGame.isCrapOut() == true) {
			System.out.println("***** Crap out! You lose. ***** " + name + "'s balance: " + balance);
			}
		
		else if (crapsGame.isCraps() == true) {
			System.out.println("***** Craps! You lose. *****" + name +  "'s balance: " + balance);
			}
	}
	
	//winningCondition() is a method that handles the situation that player wins in a single game.
	//first it will check if this round's game is first round. 
	//If it is, then because the game situation is winning, so the previousGameSituation will be set as true.
	//Doing this is to have a comparison between the first game and the next one, to give it the initialized game situation. 
	//then it will check is this game has the same result as the last game. If they have the same result, then it means the 
	//user keeps winning, the winStreak increases 1. If it's not, then it means the player's last game is losing, and this game
	//is winning, then it will first compare the loseStreak to the max one in the crapsMetricsmonitor to see if it's bigger.
	//if it is bigger, then the max loseStreak will update with this one.
	//if not, it will do nothing.
	//what it also does is reseting the winStreak as 1, restart counting the winStreak, and set the previousGameSituation as true, 
	//so we can use the next round game to compare with current winning game.
	//After dealing with the winStreak, the code add the balance with the bet.
	//After that it will check out the private variables natural and rollPoint in the crapsGame, to figure out whether it wins 
	//because of natural or rollPoint, and then print out the corresponding statement.
	private void winningCondition() {
		if (firstRound == true) {
			previousGameSituation = true;
			}
		firstRound = false;
		if (previousGameSituation == true) {
			winStreak += 1;
			if (winStreak > cmm.getMaxWinningStreak()) {
				cmm.setMaxWinningStreak(winStreak);
			}
			}
		else {
			System.out.println("here");
			if (loseStreak > cmm.getMaxLosingStreak()) {
				cmm.setMaxLosingStreak(loseStreak);
				}
			previousGameSituation = true;
			winStreak = 1;
			if (winStreak > cmm.getMaxWinningStreak()) {
				cmm.setMaxWinningStreak(winStreak);
			}
			}
		balance += bet;
		if (crapsGame.isNatural() == true) {
			System.out.println("***** Natural! You win! *****" + name + "'s balance: " + balance);
		}
		else if (crapsGame.isRollPoint() == true) {
			System.out.println("***** Roll the point! You win! ***** " + "'s balance: " + balance);
		}
	}
	
	//renewMaxBalanceAndThRound() is to check whether the balance bigger then the crapsMetricsMonitor's.
	//if it is bigger, then the maxBalance and gameNumberOfMaxBalance in the crapsMetricsMonitor will be updated
	//to the current number of game and it's balance.
	public boolean renewMaxBalanceAndThRound(CrapsMetricsMonitor cmm, int thGame, double balance) {
		if (balance > cmm.getMaxBalance()) {
			cmm.setMaxBalance(balance);
			cmm.setNumberOfMaxBalance(thGame);
			return true;
		}
		else {
			return false;
		}
	}
	
	//resetGameResultTerms() is to reset the value of the crapOut, craps, natural, and rollPoint private variables
	//in the crapsGame object by using their setter functions.
	//Doing so is because in every single game, one of the terms will be set to be true so the simulator knows
	//what exactly reason the user wins or loses. After that, those terms should be reset to false to get prepared
	//for the next round.
	private void resetGameResultTerms() {
		crapsGame.setCrapOut();
		crapsGame.setCraps();
		crapsGame.setNatural();
		crapsGame.setRollPoint();
	}
	
	//resetWholeGame() is to reset all those variables to prepare for the new game.
	private void resetWholeGame() {
		this.cmm.reset();
		this.resetGameResultTerms();
		this.name = "";
		this.money = 0;
		this.bet = 0;
		this.balance = 0;
		this.playGame = true;
		this.loseStreak = 0;
		this.winStreak = 0;
		this.firstRound = true;
		this.previousGameSituation = true;
		this.thGame = 0;
		//===========
		System.out.println(""); // make an empty line to make the output more readable.
	}
	
	//gegName() is the function to get the name of the player.
	//It will prevent the player from entering blank space and
	//get their input.
	private String getName() 
		{
		boolean nameValidation = false;
		System.out.print("Welcome to SimCraps! Enter your user name: ");
		String name = "";
		while(nameValidation == false) 
			{
			try 
				{
				name = this.input.nextLine();
				getNameValidation(name);
				System.out.println("Hello " + name + "!");
				nameValidation = true;					
				}
			catch(invalidPlayerNameException e) 
				{
				System.out.println("The name cannot be blank.");
				System.out.print("Type again: ");
				}			
			}
		return name;
	}
	
	//getNameValidation() takes a String name as a parameter and examine if this name is legal or not.
	//It checks it's length after getting rid of the name's space. If it's 0, then it means the name
	//only contains space(s), which is illegal.
	public boolean getNameValidation(String name) throws invalidPlayerNameException{
		String examine = name.replaceAll(" ","");
		examine = examine.trim();
		if (examine.length() == 0) 
			{
			throw new invalidPlayerNameException();
			} 
		else {
			return true;
		}
		
	}
	
	
	//getMoney() is to get how much the player bring to the table.
	private int getMoney() {
		System.out.print("Enter the amount of money you will bring to the table: ");
		boolean balanceValidation = false;
		int money = 0;
		while(balanceValidation == false)
		{
			try 
				{
				money = this.input.nextInt();
				this.input.nextLine();
				getMoneyValidation(money);
				balanceValidation = true;					
				}
			catch (negativeBalanceException e) 
				{
				System.out.println("Your money balance cannot be negative.");
				System.out.print("Type again: ");
				}
			catch(zeroMoneyException e) {
				System.out.println("Your money cannot be 0.");
				System.out.print("Type again: ");
			}
			
		}
		return money;
	}
	
	//getMoneyValidation() is a money validation function. It takes the money as a parameter.
	// if the money < 0 or the money is = 0, it throws exceptions. Else it's legal money number.
	public boolean getMoneyValidation(int money) throws negativeBalanceException, zeroMoneyException{
		if (money < 0) {
			throw new negativeBalanceException();
		}
		else if (money == 0) {
			throw new zeroMoneyException();
		}
		else {
			return true;
		}
	}
	
	//getBet is to get how much the player's bet is during the game.
	//It takes the balance as a parameter to prevent the player from entering
	//a bet that is bigger than the balance.
	//This function will get a valid bet from the player
	private double getBet(double balance) {
		if (balance > 0 && balance <= 1) 
			{
			System.out.print("Enter the bet amount between $0 and $" + balance + ": ");
			}
		else {
			System.out.print("Enter the bet amount between $1 and $" + balance + ": ");
			}
		double bet = 0;
		boolean betValidation = false;
		while (betValidation == false)
			{
			try 
				{
				bet = this.input.nextDouble();
				this.input.nextLine();
				getBetValidation(bet,balance);
				betValidation = true;
				}			
			catch(balanceLimitException e) 
				{
				if (balance > 1) {
				System.out.print("Out of balance limit. Please enter a bet between $1 and $" + balance + ": ");
					}
				else {
					System.out.print("Out of balance limit. Please enter a bet between $0 and $" + balance + ": ");
					}
				}
			catch(negativeBetException e) 
				{
				System.out.print("Your bet cannot be negative. Type again: ");
				}
			
			 catch(zeroBetException e) 
				{
				 System.out.print("Your bet cannot be 0. Type again: ");
			 	}
			}
		return bet;
	}
	
	//getBetValidation() is to validate the bet number. It passes a bet and a balance. 
	//when the balance is bigger than 1, the bet has to be bigger than 1 and smaller than the balance
	//when the balance is smaller or equal than 1, the bet can be bigger than 0 and smaller than the balance
	//other situation will cause throwing exceptions.
	public boolean getBetValidation(double bet, double balance) throws balanceLimitException, negativeBetException,zeroBetException
		{
		if ((bet >= 0 && bet < 1 && balance > 1) || bet > balance) {
			throw new balanceLimitException();
			}
		else if (bet < 0) {
			throw new negativeBetException();
			}
		else if (bet == 0 && balance < 1) {
			throw new zeroBetException();
		}
		else {
			return true;
		}
		
	}
	
	//betAgain() is to prompt the user to enter "y" or "n" 
	// ( upper case or lower case is fine )to
	//let the program realize whether it should restart or
	//terminate the program.
	private boolean betAgain() {
		System.out.print("Bet again? 'y' or 'n': ");
		boolean booleanResult = true;
		boolean answerValidation = false;
		while(answerValidation == false) 
			{
			try 
				{
				String originalResult = this.input.nextLine(); //Why if i change it into input.nextLine() it will automatically input an empty and go to the catch?
				booleanResult = betAgainValidation(originalResult);
				answerValidation = true;
				}
			catch(unknownAnswerException e)
				{
				System.out.print("Invalid input. You can only type 'y' or 'n': ");
				}
			}
			return booleanResult;
		}
	
	//betAgainValidation() is to validate the answer of the betAgain prompt. Everything other than 
	//"y", "n", "N", and "Y" will cause throwing unknownAnswerException.
	public boolean betAgainValidation(String inputResult) throws unknownAnswerException{
		String result = inputResult.toLowerCase();
		if(result.equals("y")|| result.equals("n")) {
			if (result.equals("y")) {
				return true;
			}
			else{
				return false;
			}
		}
		else {
			throw new unknownAnswerException();
		}
	}
	
	
	//replay() is to prompt the user to enter "y" or "n" 
	// ( upper case or lower case is fine )to
	//let the program realize whether it should restart or
	//terminate the program.
	private boolean rePlay() {
		System.out.print("\nReplay? 'y' or 'n': ");
		boolean answerValidation = false;
		boolean answer = true;
		while(answerValidation == false) 
			{
			try {
				String originalResult = this.input.nextLine();//Why if I use nextLine() it will enter empty automatically and goes to the catch place?
				answer = rePlayValidation(originalResult);
				answerValidation = true;				
				}
			catch(unknownAnswerException e) {
				System.out.print("Invalid input. You can only type 'y' or 'n': ");
				}
			}
		return answer;
	}
	
	//rePlayValidation() is to validate the answer of the betAgain prompt. Everything other than 
	//"y", "n", "N", and "Y" will cause throwing unknownAnswerException.
	public boolean rePlayValidation(String inputResult) throws unknownAnswerException{
		String result = inputResult.toLowerCase();
		if(result.equals("y")|| result.equals("n")) {
			if (result.equals("y")) {
				return true;
			}
			else{
				return false;
			}
		}
		else {
			throw new unknownAnswerException();
		}
	}

	
}

