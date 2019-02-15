/*
 * Student UCI ID: huanjial
 * Name: Huanjia Liang
 * Student ID No.: 10244014
 * 
 * This is the CrapsSimulation Test class.
 * It contains normal test cases, boundary test cases, and error test cases.
 * This test class is to test my simulation and make sure it runs well and catch all those errors I can think of.
 */

package lab3;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;



public class CrapsSimulationTest {
	CrapsSimulation cs = new CrapsSimulation();
	
	//Normal Test Case ============================================================================
	
	@BeforeClass
	public static final void beforeClass() {
		System.out.println("This is before class");
	}
	
	@AfterClass
	public static final void afterClass() {
		System.out.println("This is AFTER class");
	}
	
	@Before
	public void before() {
		System.out.println("HMMMMMM before");
	}
	
	@After
	public void after() {
		System.out.println("OHHHHHHH after");
	}
	
	@Test
	public void assertTrueMoney() {
		try{
			assertEquals(cs.getMoneyValidation(10),true);
		}
		catch(negativeBalanceException e) {
			System.out.println("Your money balance cannot be negative.");
		}
		catch(zeroMoneyException e) {
			System.out.println("Your money cannot be 0.");
		}
	}
	
	
	
	
	@Test
	public void betValidation() {
		try {
			assertEquals(cs.getBetValidation(0.5, 0.7),true);
		}
		catch(balanceLimitException e) {
			
		}
		catch(negativeBetException e) {
			
		}
		catch(zeroBetException e) {
			
		}
	}
	
	@Test
	public void betValidationIdealSituation() {
		try {
			assertEquals(cs.getBetValidation(50, 100),true);
		}
		catch(balanceLimitException e) {
			
		}
		catch(negativeBetException e) {
			
		}
		catch(zeroBetException e ) {
			
		}
	}
	
	@Test
	public void correctBetAgainAnswer() {
		try {
			assertEquals(cs.betAgainValidation("Y"),true);
			assertEquals(cs.betAgainValidation("y"),true);
			assertEquals(cs.betAgainValidation("n"),false);
			assertEquals(cs.betAgainValidation("N"),false);
		}
		catch(unknownAnswerException e) {
			
		}
	}
	
	@Test
	public void correctPlayAgainAnswer() {
		try {
			assertEquals(cs.rePlayValidation("Y"),true);
			assertEquals(cs.rePlayValidation("y"),true);
			assertEquals(cs.rePlayValidation("n"),false);
			assertEquals(cs.rePlayValidation("N"),false);
		}
		catch(unknownAnswerException e) {
			
		}
	}
	

	//Boundary test ============================================================================
	
	//The money is 0
	@Test(expected = zeroMoneyException.class)
	public void zeroMoney() throws negativeBalanceException, zeroMoneyException{
		cs.getMoneyValidation(0);
	}
	
	//The bet is 0 when money is above 1
	
	@Test(expected = balanceLimitException.class)
	public void zeroBet() throws balanceLimitException,negativeBetException,zeroBetException{
		cs.getBetValidation(0, 100);
	}
	
	//the bet is 0 when money is between 0 and 1
	
	@Test(expected = zeroBetException.class)
	public void zeroBetLowerThanOne() throws balanceLimitException,negativeBetException,zeroBetException{
		cs.getBetValidation(0, 0.8);
	}
	
	
	//the bet is 1 when the balance is above 1
	
	@Test
	public void oneBetBalanceAboveOne() {
		try {
			assertEquals(cs.getBetValidation(1, 100),true);
		}
		catch(balanceLimitException e) {		
		}
		catch(negativeBetException e) {			
		}
		catch(zeroBetException e) {		
		}
	}
	
	
	//The bet is the maximum money when the money is >1
	@Test
	public void maxBetMoneyAboveOne() {
		try {
			assertEquals(cs.getBetValidation(100, 100),true);
		}
		catch(balanceLimitException e) {		
		}
		catch(negativeBetException e) {			
		}
		catch(zeroBetException e) {		
		}
	}
	
	//The bet is out of boundary of max balance
	@Test(expected = balanceLimitException.class)
	public void exceedBetDouble() throws balanceLimitException,negativeBetException,zeroBetException{
		cs.getBetValidation(100.01,100);
	}
	
	//The bet is at maximum money when the money is < 1
	@Test
	public void maxBetMoneyLowerOne() {
		try {
			assertEquals(cs.getBetValidation(0.5, 0.5),true);
		}
		catch(balanceLimitException e) {		
		}
		catch(negativeBetException e) {			
		}
		catch(zeroBetException e) {		
		}
	}
	
	//Same maxBalnce as the cmm's max balance
	@Test
	public void renewMaxBalance() {
		CrapsMetricsMonitor cmm = new CrapsMetricsMonitor();
		cmm.setMaxBalance(1000);
		double balance = 1000.0;
		assertEquals(cs.renewMaxBalanceAndThRound(cmm, 5, balance),false);
	}
	
	//Error test ============================================================================
	@Test(expected = invalidPlayerNameException.class)
	public void invalidNameInput() throws invalidPlayerNameException {
			cs.getNameValidation(" ");
			cs.getNameValidation("      ");
	}
	
	@Test(expected = negativeBalanceException.class)
	public void negativeBalance() throws negativeBalanceException, zeroMoneyException{
		cs.getMoneyValidation(-2);
	}
	
	@Test(expected = balanceLimitException.class)
	public void exceedBet() throws balanceLimitException,negativeBetException,zeroBetException{
		cs.getBetValidation(500,100);
	}
	
	@Test(expected = balanceLimitException.class)
	public void limitBet() throws balanceLimitException,negativeBetException,zeroBetException{
		cs.getBetValidation(0.5,100);
	}
	
	@Test(expected = negativeBetException.class)
	public void negativeBet() throws balanceLimitException,negativeBetException,zeroBetException{
		cs.getBetValidation(-2,100);
	}
	
	@Test(expected = unknownAnswerException.class)
	public void IncorrectBetAgainAnswer() throws unknownAnswerException
	{
		cs.betAgainValidation("huh?");
	}
	
	@Test(expected = unknownAnswerException.class)
	public void IncorrectRePlayAnswer() throws unknownAnswerException
	{
		cs.rePlayValidation("huh?");
	}
	
}

