package converter;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.net.URL;

public class Parser {

    /**
     * parse xml response
     *
     * @param url    the api endpoint
     * @param target the target name of currency
     * @return the exchange rate
     * @throws ParserConfigurationException if unable to create Document
     * @throws IOException                  if url is invalid
     * @throws SAXException                 if response is not xml format
     * @throws XPathExpressionException     if expression is invalid
     */
    public double getRate(URL url, String target) throws ParserConfigurationException, IOException, XPathExpressionException, SAXException {
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(url.openStream());
        document.getDocumentElement().normalize();

        XPath xpath = XPathFactory.newInstance().newXPath();
        String expr = String.format("//item[targetCurrency='%s']/exchangeRate/text()", target.toUpperCase());
        XPathExpression xPathExpression = xpath.compile(expr);
        Object result = xPathExpression.evaluate(document, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;

        double rate = Double.parseDouble(nodes.item(0).getNodeValue());

        return rate;
    }
}
