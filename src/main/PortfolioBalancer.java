package main;
import java.util.HashMap;
import java.util.Map.Entry;


public class PortfolioBalancer {
	private static final double TO_PERCENTAGE = 1.0/100.0;
	private PortfolioBalancer()
	{
	}
	
	public static HashMap<String, Integer> getUnitsToBalancePortfolio(final Portfolio portfolio, final double netChange, final HashMap<String, Double> sharePrices)
	{
		HashMap<String, Integer> unitsToBalancePortfolio = new HashMap<String, Integer>();
		final double totalPortfolioValue = portfolio.getCombinedMarketValueAllFunds() + netChange;
		final HashMap<String,Double> targetAllocation = portfolio.getTargetAllocationPercentage();
		for(Entry<Fund, Integer> set : portfolio.getFunds().entrySet())
		{
			final Fund fund = set.getKey();
			final String symbol = fund.getSymbol();
			final Double fundTargetallocation = targetAllocation.get(symbol);
			final double fundMarketValueTotal = portfolio.getMarketValueOfFunds().get(symbol);
			final double fundTargetValueTotal = totalPortfolioValue * (fundTargetallocation * TO_PERCENTAGE);
			final double sharePrice = sharePrices.get(symbol);
			final int sharesToBalance = calculateSharesToBalance(fundMarketValueTotal, fundTargetValueTotal, sharePrice);
			unitsToBalancePortfolio.put(symbol, sharesToBalance);
		}
		return unitsToBalancePortfolio;
	}
   
	
	private static int calculateSharesToBalance(final double marketValueTotal, final double targetValueTotal, final double sharePrice)
	{
		final double difference = targetValueTotal - marketValueTotal;
		return (int) Math.floor(difference/sharePrice);
	}
}