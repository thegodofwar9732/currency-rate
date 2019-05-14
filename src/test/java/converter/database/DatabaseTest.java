package converter.database;

import static com.mongodb.client.model.Filters.eq;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

class DatabaseTest {

	Database database;
	JsonObject expected;
	JsonObject actual;
	String expectedDate;
	String actualDate;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		database = new Database();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testgetCurrencyDataUSD() {
		actual = database.getCurrencyData("usd", 0);
		String currencyJsonString = database.getTheCollection().find(eq("upload", database.getUploadDate())).first().toJson();
        JsonParser parser = new JsonParser();
        expected = parser.parse(currencyJsonString).getAsJsonObject().getAsJsonObject("files").getAsJsonObject("usd");
        assertEquals(expected, actual);
	}
	
	@Test
	void testgetCurrencyDataGBP() {
		actual = database.getCurrencyData("gbp", 0);
		String currencyJsonString = database.getTheCollection().find(eq("upload", database.getUploadDate())).first().toJson();
        JsonParser parser = new JsonParser();
        expected = parser.parse(currencyJsonString).getAsJsonObject().getAsJsonObject("files").getAsJsonObject("gbp");
        assertEquals(expected, actual);
	}
	
	@Test
	void testgetCurrencyDataGBPUSD() {
		actual = database.getCurrencyData("gbp", 0);
		String currencyJsonString = database.getTheCollection().find(eq("upload", database.getUploadDate())).first().toJson();
        JsonParser parser = new JsonParser();
        expected = parser.parse(currencyJsonString).getAsJsonObject().getAsJsonObject("files").getAsJsonObject("usd");
        assertNotEquals(expected, actual);
	}
	
	@Test
	void testGetUploadDateBlankString() {
		expectedDate = database.getUploadDate();
		actualDate = "";
		assertNotEquals(expectedDate, actualDate);
	}
	
	@Test
	void testGetUploadDateWrongDate() {
		expectedDate = database.getUploadDate();
		actualDate = "2019-10-12";
		assertNotEquals(expectedDate, actualDate);
	}
	
	@Test
	void testGetUploadDateWrongFormat() {
		expectedDate = database.getUploadDate();
		actualDate = "5/3/2019";
		assertNotEquals(expectedDate, actualDate);
	}
	
	@Test
	void testGetUploadDate2BlankString() {
		expectedDate = database.getUploadDate();
		actualDate = "";
		assertNotEquals(expectedDate, actualDate);
	}
	
	@Test
	void testGetUploadDate2WrongDate() {
		expectedDate = database.getUploadDate();
		actualDate = "2019-10-12";
		assertNotEquals(expectedDate, actualDate);
	}
	
	@Test
	void testGetUploadDate2WrongFormat() {
		expectedDate = database.getUploadDate();
		actualDate = "5/3/2019";
		assertNotEquals(expectedDate, actualDate);
	}
	
	//Will not always pass
	@Test
	void testGetUploadDate2() {
		expectedDate = database.getUploadDate(1);
		actualDate = "2019-05-12";
		System.out.println(expectedDate);
		assertEquals(expectedDate, actualDate);
	}
}
