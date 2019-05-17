package converter;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class StatusTest {

	File file;
	
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
	public void TestNotNullUp() {
		file = new File("src/main/resources/img/up.png"); 
		assertTrue(file.exists());
	}
	
	@Test
	public void TestNotNullDown() {
		file = new File("src/main/resources/img/down.png"); 
		assertTrue(file.exists());
	}
	
	@Test
	public void TestNotNullUnchanged() {
		file = new File("src/main/resources/img/same.png"); 
		assertTrue(file.exists());
	}
	
	@Test
	public void TesNull() {
		file = new File("src/main/resources/img/xxxx.png"); 
		assertFalse(file.exists());
	}
}
