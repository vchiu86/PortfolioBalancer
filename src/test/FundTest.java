package test;

import static org.junit.Assert.*;
import main.Fund;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FundTest {
	@Test
	public void testEqualsEqual() 
	{
		final Fund fund = new Fund("ASD", 10);
		final Fund other = new Fund("ASD", 10);
		assertTrue(fund.equals(other));
	}
	
	@Test
	public void testEqualsNotEqual()
	{
		final Fund fund = new Fund("ASD", 10);
		final Fund other = new Fund("QWE", 20);
		assertFalse(fund.equals(other));
	}
}
