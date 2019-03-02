package org.avivatest.model;
import java.text.DecimalFormat;

public class Currency {
	
	private String currencyName;
	
	private transient String currencyCode;

	
	private transient double conversionFactor;
	
	private double priceInThisCurrency;

	public Currency() {

	}

	public Currency(String currencyName, String currencyCode, double conversionFactor) {
		super();
		this.currencyName = currencyName;
		this.conversionFactor = conversionFactor;
		this.setCurrencyCode(currencyCode);
	}

	public double calculatePriceInThisCurrency(double priceInAnotherCurrency) {
		setPriceInThisCurrency(this.getConversionFactor() * priceInAnotherCurrency);
		return getPriceInThisCurrency();
	}

	public double getConversionFactor() {
		return conversionFactor;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public double getPriceInThisCurrency() {
		DecimalFormat format = new DecimalFormat("##.00");
		return Double.valueOf((format.format(this.priceInThisCurrency)));
	}

	public void setConversionFactor(double conversionFactor) {
		this.conversionFactor = conversionFactor;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public void setPriceInThisCurrency(double priceInThisCurrency) {
		this.priceInThisCurrency = priceInThisCurrency;
	}

	@Override
	public String toString() {
		return "Currency [currencyName=" + currencyName + ", currencyCode=" + getCurrencyCode() + ", conversionFactor="
				+ conversionFactor + ", priceInThisCurrency=" + getPriceInThisCurrency() + "]";
	}

}
