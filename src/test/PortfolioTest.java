package test;

import static org.junit.Assert.*;
import main.Fund;
import main.Portfolio;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PortfolioTest {
	private static final double DELTA = 1e-15;
	Portfolio portfolio;
	
	public PortfolioTest()
	{
		portfolio = new Portfolio();
		final Fund fund = new Fund("ASD", 10);
		final Fund fund2 = new Fund("QWE", 20);
		portfolio.addFund(fund, 5, 50);
		portfolio.addFund(fund2, 20, 50);
	}
	
	@Test
	public void testAddFund() 
	{
		Portfolio portfolio1 = new Portfolio();
		final Fund f = new Fund("ZXC", 10);
		portfolio1.addFund(f, 10, 100);
		assertEquals(1, portfolio1.getFunds().size());
	}
	
	@Test
	public void testGetCombinedMarketValueAllFunds()
	{
		final double expected = (5 * 10) + (20 * 20);
		assertEquals(expected, portfolio.getCombinedMarketValueAllFunds(), DELTA);
	}
	
	@Test
	public void testGetMarketValueOfFunds()
	{
		final double marketValueASD = 5 * 10;
		final double marketValueQWE = 20 * 20;
		assertEquals(marketValueASD, portfolio.getMarketValueOfFunds().get("ASD"), DELTA);
		assertEquals(marketValueQWE, portfolio.getMarketValueOfFunds().get("QWE"), DELTA);
	}
	
	@Test
	public void testGetTargetAllocationPercentage()
	{
		final double expected = 50;
		assertEquals(expected, portfolio.getTargetAllocationPercentage().get("ASD"), DELTA);
		assertEquals(expected, portfolio.getTargetAllocationPercentage().get("QWE"), DELTA);
	}
	
	@Test
	public void testToString()
	{
		final StringBuilder expected = new StringBuilder();
		expected.append("Portfolio contains:\n");
		expected.append("ASD Units:5 Market Price:10.00 Total Market Value:50.00 Portfolio %:11.11% Target %:50.00%\n");
		expected.append("QWE Units:20 Market Price:20.00 Total Market Value:400.00 Portfolio %:88.89% Target %:50.00%\n");
		expected.append("The total market value of the portfolio is 450.000.");
		assertEquals(expected.toString(), portfolio.toString());
	}
	
	@Test
	public void testClear()
	{
		Portfolio portfolio1 = new Portfolio();
		final Fund f = new Fund("ZXC", 10);
		portfolio1.addFund(f, 10, 100);
		portfolio1.clear();
		assertEquals(0, portfolio1.getFunds().size());
		assertEquals(0, portfolio1.getMarketValueOfFunds().size());
		assertEquals(0, portfolio1.getTargetAllocationPercentage().size());
		assertEquals(0, portfolio1.getCombinedMarketValueAllFunds(), DELTA);
	}

}
