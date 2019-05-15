package converter;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ApiClient {
    static public final String BASE_URL = "http://www.floatrates.com/";
    private Parser parser;

    public ApiClient() {
        parser = new Parser();
    }

    public double getCurrentRate(String sourceCurrency, String targetCurrency) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
        URL url = new URL(String.format("%sdaily/%s.xml", BASE_URL, sourceCurrency));
        double rate = parser.getRate(url, targetCurrency);

        return rate;
    }

    public double getYesterdayRate(String sourceCurrency, String targetCurrency) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
        String yesterday = LocalDate.now().minusDays(1).format(DateTimeFormatter.ISO_LOCAL_DATE);
        URL url = new URL(String.format("%shistorical-exchange-rates.html?currency_date=%s&base_currency_code=%s&format_type=xml", BASE_URL, yesterday, sourceCurrency));
        double rate = parser.getRate(url, targetCurrency);

        return rate;
    }
}
