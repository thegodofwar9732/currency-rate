package converter;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CurrencyTest {

	String expected;
	String actual;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

    @Test 
    public void GetCodeEuro(){
    	expected = "eur";
    	actual = Currency.EUR.getCode();
    	assertEquals(expected, actual);
    }
    
    @Test
    public void getCodeDollars() {
    	expected = "usd";
    	actual = Currency.USD.getCode();
    	assertEquals(expected, actual);
    }
    
    @Test
    public void getCodeDollarsWrong() {
    	expected = "USD (U.S.Dollar)";
    	actual = Currency.USD.getCode();
    	assertNotEquals(expected, actual);
    }
    
    @Test
    public void getCodeDollarsEuros() {
    	expected = "usd";
    	actual = Currency.EUR.getCode();
    	assertNotEquals(expected, actual);
    }
	
	@Test
    public void toStringEuro() {
    	expected = "EUR (Euro)";
    	actual = Currency.EUR.toString();
    	assertEquals(expected, actual);
    }
    
    @Test
    public void toStringDollars() {
    	expected = "USD (U.S.Dollar)";
    	actual = Currency.USD.toString();
    	assertEquals(expected, actual);
    }
    
    @Test
    public void toStringDollarsEuros() {
    	expected = "USD (U.S.Dollar)";
    	actual = Currency.EUR.toString();
    	assertNotEquals(expected, actual);
    }
    
    @Test
    public void toStringDollarsWrong() {
    	expected = "usd";
    	actual = Currency.USD.toString();
    	assertNotEquals(expected, actual);
    }
    
}