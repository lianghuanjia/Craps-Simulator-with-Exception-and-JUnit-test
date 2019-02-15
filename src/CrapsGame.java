/*
 * Student UCI ID: huanjial
 * Name: Huanjia Liang
 * Student ID No.: 10244014
 * 
 * This CrapsGame.java is a file that handles the logic of a SINGLE craps game.
 * it use random to randomly generate the dice number, then according to the dice
 * number and the games rule, it will return true or false and set one of those 
 * specific game result(crapOut, natural, etc.) to true so when the game simulates
 * it will know the exact result of one single game.
 */

package lab3;

import java.util.Random;

public class CrapsGame {
	private int numOfRolls;
	CrapsMetricsMonitor localMonitor;
	private boolean crapOut;
	private boolean craps;
	private boolean natural;
	private boolean rollPoint;
	
	
	//CrapsGame's constructor;
	CrapsGame(){		
	}
	
	//CrapsGame's constructor with crapsMetricsMonitor;
	CrapsGame(CrapsMetricsMonitor monitor){	
		this.localMonitor = monitor;
		this.numOfRolls = 0;
		this.crapOut = false;
		this.craps = false;
		this.natural = false;
		this.rollPoint = false;
	}
	
	//playGame() is the logic for one single time Craps game.
	//In this function, first it calls the rollDice() function to get the random number between 1-12.
	//Then according to the first time dice, it determines if the result belongs to the following situation:
	//Natural, Craps, or keep rolling dice.
	//If it's natural, it will return true, representing the player wins.
	//also it will set the private variable natural as true, so in the CrapsSimulation.java, 
	//the game will know the player wins whether for natrual or roll the point.
	//It works the same for the craps, crapout, and rollpoint. If the player loses, it returns false, otherwise returns true.
	//Meanwhile it set's the one of the 4 result situations(crapOut, craps, natural, rollPoint) become true so the player will
	//know what reason they win exactly, and it is easy for gathering the data of the winning situation.
	boolean playGame() 
		{
		int numOfDice = rollDice();
		System.out.println("Rolled a " + numOfDice);
		numOfRolls += 1;
		if (numOfDice == 7 || numOfDice == 11) 
			{
			//the first time roll if it's 7 or 11, then the player wins
			//also this is called NATURAL
			localMonitor.increaseNumOfGamesWon();
			localMonitor.increaseNaturalRollsCount();
			natural = true;
			if (numOfRolls > localMonitor.getMaxNumOfRollsInSingleGame()) 
				{
				localMonitor.setMaxNumOfRollsInSingleGame(numOfRolls);
				}
			numOfRolls = 0;
			return true;	
			}
		
		else if (numOfDice == 2 || numOfDice == 3 || numOfDice == 12) 
			{
			localMonitor.increaseNumOfGamesLost();
			localMonitor.increaseCrapsRollsCount();
			craps = true;
			if (numOfRolls > localMonitor.getMaxNumOfRollsInSingleGame()) 
				{
				localMonitor.setMaxNumOfRollsInSingleGame(numOfRolls);
				}
			numOfRolls = 0;
			return false;
			}
		
		else 
			{
			int pointNumber = numOfDice;
			while(true) 
				{
				int rollDice = rollDice();
				System.out.println("Rolled a " + rollDice);
				numOfRolls += 1;
				if (rollDice == pointNumber) 
					{
					rollPoint = true;
					if (numOfRolls > localMonitor.getMaxNumOfRollsInSingleGame())
						{
						localMonitor.setMaxNumOfRollsInSingleGame(numOfRolls);
						}
					numOfRolls = 0;
					localMonitor.increaseNumOfGamesWon();
					return true;
					}
				
				else if (rollDice == 7) 
					{
					crapOut = true;
					if (numOfRolls > localMonitor.getMaxNumOfRollsInSingleGame()) 
						{
						localMonitor.setMaxNumOfRollsInSingleGame(numOfRolls);
						}
					numOfRolls = 0;
					localMonitor.increaseNumOfGamesLost();
					return false;
					}
				else 
					{		
					}
				
				}
			}
		}
	
	
	
	//rollDice() creates two single random objects and get their random numbers.
	//then the function adds these two random numbers and return it,
	//this sum number is the number that a player rolls for one time.
	private int rollDice() {
		Random generator1 = new Random();
		int dice1 = generator1.nextInt(6)+1;
		Random generator2 = new Random();
		int dice2 = generator2.nextInt(6)+1;
		int totalNumber = dice1 + dice2;
		return totalNumber;
	
	}

	//isCrapOut() is a getter function of the private variable crapOut.
	//it returns the value of the variable crapOut.
	public boolean isCrapOut() {
		return crapOut;
	}

	//setCrapOut() is a setter function of the private variable crapOut.
	//It sets the crapOut back to it's original status: false.
	public void setCrapOut() {
		this.crapOut = false;
	}
	
	//isCraps() is a getter function of the private variable craps.
	//it returns the value of the variable craps
	public boolean isCraps() {
		return craps;
	}

	//setCraps() is a setter function of the private variable crapOut.
	//it sets the craps back to it's original status: false.
	public void setCraps() {
		this.craps = false;
	}

	//isNatural() is a getter function of the private variable natural.
	//it returns the value of the variable natural.
	public boolean isNatural() {
		return natural;
	}

	//setNatural() is a setter function of the private variable natural.
	//it sets the variable natural back to it's original status: false.
	public void setNatural() {
		this.natural = false;
	}
	
	//isRollPoint() is a getter function of the private variable rollPoint.
	//it returns the value of the variable rollPoint.
	public boolean isRollPoint() {
		return rollPoint;
	}

	//setRollPoint() is a setter function of the private variable rollPoint.
	//it sets the variable rollPoint back to it's original status: false.
	public void setRollPoint() {
		this.rollPoint = false;
	}
	
	
	
}
