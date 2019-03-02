package org.avivatest.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.jcr.Node;
import javax.jcr.NodeIterator;

import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.jcr.api.SlingRepository;
import org.avivatest.model.Currency;

import com.google.gson.Gson;

@SlingServlet(paths = "/bin/currencyConverterServlet", methods = "POST", metatype = true)
public class CurrencyConverterServlet extends org.apache.sling.api.servlets.SlingAllMethodsServlet {

	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger("CurrencyConverterServlet");

	private static final String DEFAULT_NODE = "/etc/currencies";

	Map<String, Currency> map = new HashMap<String, Currency>();

	@Reference
	private SlingRepository repository;

	public void bindRepository(SlingRepository repository) {
		this.repository = repository;
	}

	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServerException, IOException {
		try {
			String price = request.getParameter("priceInGBP");
			double productPriceInGBP = Double.parseDouble(price);
			List<Currency> currencyList = new ArrayList<Currency>();
			Node baseNode = request.getResourceResolver().getResource(DEFAULT_NODE).adaptTo(Node.class);
			NodeIterator nodeItr = baseNode.getNodes();
			while (nodeItr.hasNext()) {
				Node node = nodeItr.nextNode();
				// currencyCodeName is the property within etc/currencies where
				// the 3
				// letter ISO code is stored
				String currencyCodeName = node.getName();
				// conversionRate is the property within etc/currencies where
				// the conversion rate is stored
				String conversionRateInString = node.getProperty("conversionRate").getValue().toString();
				double conversionRate = Double.parseDouble(conversionRateInString);
				// currencyName is the property within etc/currencies where the
				// currency name is stored
				String currencyName = node.getProperty("currencyName").getValue().toString();
				Currency currency = new Currency(currencyName, currencyCodeName, conversionRate);
				currencyList.add(currency);
			}
			for (Currency c : currencyList) {
				priceInGivenCurrency(c, productPriceInGBP);
			}
			if (currencyList.isEmpty()) {
				map.put("", new Currency());
			}
			Gson gson =  new Gson();
			String json = gson.toJson(map);
			response.setContentType("application/json; charset=utf-8");
			PrintWriter outsearch = response.getWriter();
			outsearch.write(json);
			response.getWriter().write(json.toString());
		} catch (NumberFormatException e) {
			e.printStackTrace();
			logger.severe("Number Format Exception in CurrencyConverterServlet");
		} catch (Exception e) {
			logger.severe("Unhandled Exception in CurrencyConverterServlet");
		}
	}

	private double priceInGivenCurrency(Currency currency, double priceInAnotherCurrency) {
		double price = currency.calculatePriceInThisCurrency(priceInAnotherCurrency);
		map.put(currency.getCurrencyCode(), currency);
		return price;
	}

}