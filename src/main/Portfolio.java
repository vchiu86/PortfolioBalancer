package main;
import java.util.HashMap;
import java.util.Map.Entry;

public class Portfolio {
	private HashMap<Fund, Integer> funds = new HashMap<Fund, Integer>();
	private final HashMap<String, Double> marketValueOfFunds = new HashMap<String, Double>();
	private final HashMap<String, Double> percentagesOfFunds = new HashMap<String, Double>();
	private final HashMap<String,Double> targetAllocationPercentage = new HashMap<String, Double>();
	private double combinedMarketValueAllFunds = 0;
	
	public Portfolio()
	{
	}
	
	public Portfolio(final HashMap<Fund, Integer> funds)
	{
		this.funds = funds;
		calculateMarketValueOfFunds();
		calculateCombinedMarketValue();
		calculatePercentagesOfFunds();
	}
	
	public void addFund(final Fund fund, final int numUnits, final double targetAllocation)
	{
		funds.put(fund, numUnits);
		targetAllocationPercentage.put(fund.getSymbol(), targetAllocation);
		calculateMarketValueOfFunds();
		calculateCombinedMarketValue();
		calculatePercentagesOfFunds();
	}
	
	public final HashMap<Fund, Integer> getFunds()
	{
		return this.funds;
	}
	
	private final void calculateMarketValueOfFunds()
	{
		for (Entry<Fund, Integer> set : funds.entrySet())
		{
			double totalMarketValue = set.getValue() * set.getKey().getValue();
			marketValueOfFunds.put(set.getKey().getSymbol(), totalMarketValue);
		}
	}
	
	private final void calculateCombinedMarketValue()
	{
		combinedMarketValueAllFunds = 0;
		for (double marketValueOfFund : marketValueOfFunds.values())
		{
			combinedMarketValueAllFunds += marketValueOfFund;
		}
	}
	
	private final void calculatePercentagesOfFunds()
	{
		for (String symbol : marketValueOfFunds.keySet())
		{
			final double percentage = (marketValueOfFunds.get(symbol) / combinedMarketValueAllFunds) * 100D;
			percentagesOfFunds.put(symbol, percentage);
		}
	}
	
	public double getCombinedMarketValueAllFunds()
	{
		return this.combinedMarketValueAllFunds;
	}
	
	public HashMap<String, Double> getMarketValueOfFunds()
	{
		return marketValueOfFunds;
	}
	
	public HashMap<String, Double> getTargetAllocationPercentage()
	{
		return targetAllocationPercentage;
	}
	
	public void clear()
	{
		funds.clear();
		marketValueOfFunds.clear();
		percentagesOfFunds.clear();
		targetAllocationPercentage.clear();
		combinedMarketValueAllFunds = 0;
	}
	
	public final String toString()
	{
		StringBuffer sb = new StringBuffer("Portfolio contains:\n");
		for (Entry<Fund, Integer> set : funds.entrySet())
		{
			final int numUnits = set.getValue();
			final Fund fund = set.getKey();
			final String symbol = fund.getSymbol();
			final double marketPrice = fund.getValue();
			final double marketValue = marketValueOfFunds.get(symbol);
			final double percentage = percentagesOfFunds.get(symbol);
			final double targetAllocation = targetAllocationPercentage.get(symbol);
			
			
			sb.append(symbol).append(" Units:").append(numUnits).append(" Market Price:").
			append(String.format("%.2f", marketPrice)).append(" Total Market Value:").
			append(String.format("%.2f", marketValue)).append(" Portfolio %:").
			append(String.format("%.2f", percentage)).append("% Target %:").append(String.format("%.2f", targetAllocation)).append("%\n");
		}
		sb.append("The total market value of the portfolio is ").append(String.format("%.2f", combinedMarketValueAllFunds)).append(".");
		return sb.toString();
	}
	
	
}
