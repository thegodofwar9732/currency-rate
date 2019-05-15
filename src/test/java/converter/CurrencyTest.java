package converter;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CurrencyTest {

	String expected;
	String actual;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
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
