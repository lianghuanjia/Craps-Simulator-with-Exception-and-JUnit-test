/*
 * Student UCI ID: huanjial
 * Name: Huanjia Liang
 * Student ID No.: 10244014
 * 
 * This CrapsMetricsMonitor.java file handles the information of the statistics
 * during running the game. It sets all those statistic variables and define some
 * of their's getters and setters so that they can be accessed during the game
 * and do the statistics.
 */

package lab3;

public class CrapsMetricsMonitor {
	private int numOfGamesPlayed = 0;
	private int numOfGamesWon = 0;
	private int numOfGamesLost = 0;
	private int maxNumOfRollsInSingleGame = 0;
	private int naturalRollsCount = 0;
	private int crapsRollsCount = 0;
	private int maxWinningStreak = 0;
	private int maxLosingStreak = 0;
	private double maxBalance = 0;
	private int gameNumberOfMaxBalance = 0;
	
	//This function increases the numOfGamesPlayed variable by 1
	public void increaseNumOfGamesPlayed() {
		numOfGamesPlayed += 1;
	}
	
	//This function increases the numOfGamesWon variable by 1
	public void increaseNumOfGamesWon() {
		numOfGamesWon += 1;
	}
	
	//This function increases the numOfGamesLost variable by 1
	public void increaseNumOfGamesLost() {
		numOfGamesLost += 1;
	}
	
	//This function is a setter function to assign the coutingNumber to the variable maxNumOfRollsInSingleGame
	public void setMaxNumOfRollsInSingleGame(int countingNumber) {
		maxNumOfRollsInSingleGame = countingNumber;
	}
	
	public int getMaxNumOfRollsInSingleGame() {
		return maxNumOfRollsInSingleGame;
	}
	
	//This function increases the naturalRollsCount variable by 1
	public void increaseNaturalRollsCount() {
		naturalRollsCount += 1;
	}
	
	//This function increases the crapsRollsCount variable by 1
	public void increaseCrapsRollsCount() {
		crapsRollsCount += 1;
	}
	
	public void setMaxWinningStreak(int countingNumber) {
		maxWinningStreak = countingNumber;
	}
	
	public int getMaxWinningStreak() {
		return maxWinningStreak;
	}
	
	public void setMaxLosingStreak(int countingNumber) {
		maxLosingStreak = countingNumber;
	}
	
	public int getMaxLosingStreak() {
		return maxLosingStreak;
	}
	
	public void setMaxBalance(double countingNumber) {
		maxBalance = countingNumber;
	}
	
	public double getMaxBalance() {
		return maxBalance;
	}
	
	public void setGameNumberOfMaxBalance(int gameNumber) {
		gameNumberOfMaxBalance = gameNumber;
	}
	
	public void setNumberOfMaxBalance(int thGame) {
		gameNumberOfMaxBalance = thGame;
	}
	
	/*
	 * printStatistics() is a method to print out the statistics 
	 * in the console
	 */
	void printStatistics() {
	System.out.println("* * * * * * * * * * * * * * * * * * * * * * *");
	System.out.println("* * * * * * SIMULATION STATISTICS * * * * * *");
	System.out.println("* * * * * * * * * * * * * * * * * * * * * * *");
	System.out.println("Games played: " + numOfGamesPlayed);
	System.out.println("Games won: " + numOfGamesWon);
	System.out.println("Games lost: " + numOfGamesLost);
	System.out.println("Maximum Rolls in a single game: " + maxNumOfRollsInSingleGame);
	System.out.println("Natural Count: " + naturalRollsCount);
	System.out.println("Craps Count: " + crapsRollsCount);
	System.out.println("Maximum Winning Streak: " + maxWinningStreak);
	System.out.println("Maximum Losing Streak: " + maxLosingStreak);
	System.out.println("Maximum balance: " + maxBalance + " during game " + gameNumberOfMaxBalance);
	}
	
	/* reset() is a method to reset all statistics if a user
	 * wants to restart a game
	 */
	void reset() {
		this.numOfGamesPlayed = 0;
		this.numOfGamesWon = 0;
		this.numOfGamesLost = 0;
		this.maxNumOfRollsInSingleGame = 0;
		this.naturalRollsCount = 0;
		this.crapsRollsCount = 0;
		this.maxWinningStreak = 0;
		this.maxLosingStreak = 0;
		this.maxBalance = 0;
		this.gameNumberOfMaxBalance = 0;
	}
	
	
	
}
