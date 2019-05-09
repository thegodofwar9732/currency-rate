package converter;

import converter.downloader.Downloader;
import org.junit.*;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;

public class AppControllerTest {

    Downloader download;
    AppController controller;
    static double expected;
    static double actual;

    @BeforeClass
    public static void setUpBeforeClass() {
    }

    @AfterClass
    public static void tearDownAfterClass() {
    }

    @Before
    public void setUp() {
        controller = new AppController();
        download = new Downloader();
        download.downloadFile(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetRateDollarsToEuros() throws FileNotFoundException {
        actual = controller.getRate("usd", "eur");
        expected = .888;
        assertEquals("Failed to convert dollars to euros", expected, actual, .05);
    }

    @Test
    public void testGetRateEurosToDollars() throws FileNotFoundException {
        actual = controller.getRate("eur", "usd");
        expected = 1.13;
        assertEquals("Failed to convert dollars to euros", expected, actual, .05);
    }

    @Test(expected = java.lang.RuntimeException.class)
    public void testGetRateDollarsToDollars() throws FileNotFoundException {
        controller.getRate("usd", "usd");
    }

    @Test
    public void testGetRate2CanadianToAustralain() throws FileNotFoundException {
        actual = 2 * (controller.getRate("cad", "aud"));
        expected = 2.09;
        assertEquals("Failed to convert dollars to euros", expected, actual, .05);
    }

    @Test(expected = java.lang.RuntimeException.class)
    public void testGetRateDollarsToNothing() throws FileNotFoundException {
        controller.getRate("usd", "");
    }

    @Test(expected = java.lang.Exception.class)
    public void testGetRateNothingToDollars() throws FileNotFoundException {
        controller.getRate("", "usd");
    }
}
