package test;

import static org.junit.Assert.*;

import java.util.HashMap;

import main.Fund;
import main.Portfolio;
import main.PortfolioBalancer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PortfolioBalancerTest {
	private static final double DELTA = 1e-15;

	@Test
	public void testGetUnitsToBalancePortfolio0Change() 
	{
		final Fund fund = new Fund("ASD", 10);
		final Portfolio portfolio = new Portfolio();
		portfolio.addFund(fund, 5, 100);
		final HashMap<String, Double> sharePrices = new HashMap<>();
		sharePrices.put("ASD", 10.0);
		final HashMap<String, Integer> unitsToBalance = PortfolioBalancer.getUnitsToBalancePortfolio(portfolio, 0.0, sharePrices);
		assertEquals(0, unitsToBalance.get("ASD"), DELTA);
	}
	
	@Test
	public void testGetUnitsToBalancePortfolio1Change() 
	{
		final Fund fund = new Fund("ASD", 10);
		final Portfolio portfolio = new Portfolio();
		portfolio.addFund(fund, 5, 100);
		final HashMap<String, Double> sharePrices = new HashMap<>();
		sharePrices.put("ASD", 10.0);
		final HashMap<String, Integer> unitsToBalance = PortfolioBalancer.getUnitsToBalancePortfolio(portfolio, 10.0, sharePrices);
		assertEquals(1, unitsToBalance.get("ASD"), DELTA);
	}
	
	@Test
	public void testGetUnitsToBalancePortfolioMinus1Change() 
	{
		final Fund fund = new Fund("ASD", 10);
		final Portfolio portfolio = new Portfolio();
		portfolio.addFund(fund, 5, 100);
		final HashMap<String, Double> sharePrices = new HashMap<>();
		sharePrices.put("ASD", 10.0);
		final HashMap<String, Integer> unitsToBalance = PortfolioBalancer.getUnitsToBalancePortfolio(portfolio, -10.0, sharePrices);
		assertEquals(-1, unitsToBalance.get("ASD"), DELTA);
	}
	
	@Test
	public void testGetUnitsToBalancePortfolioMultipleFunds()
	{
		final Fund fund = new Fund("QWE", 10);
		final Fund fund1 = new Fund("ASD", 5);
		final Portfolio portfolio = new Portfolio();
		portfolio.addFund(fund, 1, 50);
		portfolio.addFund(fund1, 1, 50);
		final HashMap<String, Double> sharePrices = new HashMap<>();
		sharePrices.put("QWE", 10.0);
		sharePrices.put("ASD", 5.0);
		final HashMap<String, Integer> unitsToBalance = PortfolioBalancer.getUnitsToBalancePortfolio(portfolio, 26.0, sharePrices);
		assertEquals(1, unitsToBalance.get("QWE"), DELTA);
		assertEquals(3, unitsToBalance.get("ASD"), DELTA);
	}

}
