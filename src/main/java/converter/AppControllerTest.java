package converter;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import converter.downloader.Downloader;

public class AppControllerTest {

	Downloader download;
	AppController controller;
	static double expected;
	static double actual;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception 
	{
	}

	@Before
	public void setUp() throws Exception 
	{
		controller = new AppController();
		download = new Downloader();
		download.downloadFile();
	}

	@After
	public void tearDown() throws Exception 
	{
	}

	@Test
	public void testGetRateDollarsToEuros() throws FileNotFoundException 
	{
		actual = controller.getRate("usd", "eur");
		expected = .888;
		assertEquals("Failed to convert dollars to euros", expected, actual, .05);
	}
	
	@Test
	public void testGetRateEurosToDollars() throws FileNotFoundException 
	{
		actual = controller.getRate("eur", "usd");
		expected = 1.13;
		assertEquals("Failed to convert dollars to euros", expected, actual, .05);
	}
	
	@Test(expected=java.lang.RuntimeException.class)
	public void testGetRateDollarsToDollars() throws FileNotFoundException
	{
		controller.getRate("usd", "usd");
	}
	
	@Test
	public void testGetRate2CanadianToAustralain() throws FileNotFoundException
	{
		actual = 2*(controller.getRate("cad", "aud"));
		expected = 2.09;
		assertEquals("Failed to convert dollars to euros", expected, actual, .05);
	}
	
	@Test(expected=java.lang.RuntimeException.class)
	public void testGetRateDollarsToNothing() throws FileNotFoundException
	{
		controller.getRate("usd", "");
	}
	
	@Test(expected=java.lang.Exception.class)
	public void testGetRateNothingToDollars() throws FileNotFoundException
	{
		controller.getRate("", "usd");
	}

}
