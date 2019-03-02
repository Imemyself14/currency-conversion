package org.avivatest.tests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.avivatest.model.Currency;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class CurrencyTest {
	static Currency testCurrency;

	@Before
	public void setUp() {
		testCurrency = new Currency("Test Currency", "TST", 0.75);
	}

	@Test
	public void test_calculatePriceInThisCurrency() {
		assertEquals("The price of " + testCurrency.getCurrencyName() + " does not match", Double.valueOf(75.00),
				Double.valueOf(testCurrency.calculatePriceInThisCurrency(100.00)));
		assertTrue(testCurrency.getPriceInThisCurrency() == 75.0);
	}
	
	@Test
	public void test_allGettersInCurrency_forConstructorValues(){
		assertTrue(testCurrency.getPriceInThisCurrency() == 0.0);
		assertTrue(testCurrency.getConversionFactor() == 0.75);
		assertTrue(testCurrency.getCurrencyName().equals("Test Currency"));
		assertTrue(testCurrency.getCurrencyCode().equals("TST"));
		testCurrency.calculatePriceInThisCurrency(200.00);
		assertTrue(testCurrency.getPriceInThisCurrency() == 150.0);
	}
	
	@Test
	public void test_allSettersInCurrency(){
		testCurrency.setConversionFactor(1.25);
		assertTrue(testCurrency.getConversionFactor() == 1.25);
		testCurrency.setPriceInThisCurrency(125.0);
		assertTrue(testCurrency.getPriceInThisCurrency() == 125.0);
		testCurrency.setCurrencyCode("USD");
		assertTrue(testCurrency.getCurrencyCode().equals("USD"));
		testCurrency.setCurrencyName("United States Dollar");
		assertTrue(testCurrency.getCurrencyName().equals("United States Dollar"));
	}
	
	@AfterClass
	public static void tearDown() {
		testCurrency = null;
	}

}
