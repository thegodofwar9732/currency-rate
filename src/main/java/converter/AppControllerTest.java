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
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		controller = new AppController();
		download = new Downloader();
		download.downloadFile();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetRate() throws FileNotFoundException {
		actual = controller.getRate("USD", "EUR");
		System.out.println("actual " + actual);
		expected = .888;
		assertEquals("Failed to convert dollars to euros", expected, actual, .05);
	}

}
