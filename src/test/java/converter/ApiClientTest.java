package converter;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

class ApiClientTest {

    private ApiClient apiClient;
    private double actual;
    private double expected;

    @Before
    public void setUp() {
        apiClient = new ApiClient();
    }

    @Test
    public void testgetCurrentRateEurosToDollars() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        actual = apiClient.getCurrentRate("eur", "usd");
        expected = 1.13;
        assertEquals("Failed to convert dollars to euros", expected, actual, .05);
    }

    @Test(expected = RuntimeException.class)
    public void testgetCurrentRateDollarsToDollars() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        apiClient.getCurrentRate("usd", "usd");
    }

    @Test
    public void testgetCurrentRateCanadianToAustralain() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        actual = 2 * (apiClient.getCurrentRate("cad", "aud"));
        expected = 2.09;
        assertEquals("Failed to convert dollars to euros", expected, actual, .05);
    }

    @Test(expected = java.lang.RuntimeException.class)
    public void testgetCurrentRateDollarsToNothing() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        apiClient.getCurrentRate("usd", "");
    }

    @Test(expected = java.lang.Exception.class)
    public void testgetCurrentRateNothingToDollars() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        apiClient.getCurrentRate("", "usd");
    }

}
