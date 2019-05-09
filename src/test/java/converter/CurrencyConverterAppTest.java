package converter;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CurrencyConverterAppTest {

	CurrencyConverterApp app;
	static boolean expected;
    static boolean actual;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		app = new CurrencyConverterApp();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testDownloadedTodayWithEmptyString() {
		actual = app.downloadedToday("");
		expected = false;
        assertEquals("Failed to compare download date", expected, actual);
	}
	
	@Test
	void testDownloadedTodayWithWrongDate() {
		actual = app.downloadedToday("2018-05-09");
		expected = false;
        assertEquals("Failed to compare download date", expected, actual);
	}
	
	@Test
	void testDownloadedTodayWithRighDate() {
		actual = app.downloadedToday("2019-05-09");
		expected = true;
        assertEquals("Failed to compare download date", expected, actual);
	}

}
