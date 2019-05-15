package converter;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;

public class ApiClientTest {

	double expected;
	double actual;
	ApiClient api;
	Parser parser;
	final String BASE_URL = "http://www.floatrates.com/";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		api = new ApiClient();
		parser = new Parser();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testgetCurrentRateEurosToDollars() throws Exception, ParserConfigurationException, SAXException, IOException {
		actual = api.getCurrentRate("eur", "usd");
		
		URL url = new URL(String.format("%sdaily/%s.xml", BASE_URL, "eur"));
        expected = parser.getRate(url, "usd");
		
		assertEquals("failure to convert euros to Dollars", expected, actual, .01);
	}
	
	@Test
	public void testgetCurrentRateCanadianToAustralain() throws Exception, ParserConfigurationException, SAXException, IOException {
		actual = 2 *( api.getCurrentRate("cad", "aud"));
		
		URL url = new URL(String.format("%sdaily/%s.xml", BASE_URL, "cad"));
		expected = 2* (parser.getRate(url, "aud"));
		
		assertEquals("failure to convert Canadian to Australian", expected, actual, .01);
	}
	
	@Test (expected = Exception.class)
    public void testgetCurrentRateDollarsToDollars() throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {
		actual = api.getCurrentRate("usd", "usd");
		
		URL url = new URL(String.format("%sdaily/%s.xml", BASE_URL, "usd"));
        expected = parser.getRate(url, "usd");
		
		assertEquals("failure to convert euros to Dollars", expected, actual, .01);
	}

	@Test (expected = Exception.class)
    public void testgetCurrentRateDollarsToNothing() throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {
		actual = api.getCurrentRate("usd", "");
		
		URL url = new URL(String.format("%sdaily/%s.xml", BASE_URL, "usd"));
        expected = parser.getRate(url, "");
		
		assertEquals("failure to convert euros to Dollars", expected, actual, .01);
	}
	
	@Test (expected = Exception.class)
    public void testgetCurrentRateNothingToDollars() throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {
		actual = api.getCurrentRate("", "usd");
		
		URL url = new URL(String.format("%sdaily/%s.xml", BASE_URL, ""));
        expected = parser.getRate(url, "usd");
		
		assertEquals("failure to convert euros to Dollars", expected, actual, .01);
	}
}
