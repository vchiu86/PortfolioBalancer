package main;

public class Fund {
	private final String symbol;
	private final double value;
	
	public Fund(final String symbol, final double value)
	{
		this.symbol = symbol;
		this.value = value;
	}
	
	public String getSymbol()
	{
		return this.symbol;
	}
	
	public double getValue()
	{
		return this.value;
	}
	
	public boolean equals(Fund other)
	{
		return this.symbol.equalsIgnoreCase(other.getSymbol());
	}
}
