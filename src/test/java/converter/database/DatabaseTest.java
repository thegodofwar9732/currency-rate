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
}
