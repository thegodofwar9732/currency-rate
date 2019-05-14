package converter;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CurrencyConverterAppTest {

	CurrencyConverterApp app;
	static boolean expected;
    static boolean actual;
    String dateNow;
	
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
	
	//This test will fail unless the file has been downloaded today 
	//and the database has been updated
	@Test
	void testDownloadedTodayWithRighDate() {
		dateNow = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
		actual = app.downloadedToday(dateNow);
		expected = true;
        assertEquals("Failed to compare download date", expected, actual);
	}

}
