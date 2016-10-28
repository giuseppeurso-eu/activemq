package eu.giuseppeurso.activemq.camelpollingdirectory;

import junit.framework.TestCase;

/**
 * Test cases 
 * 
 * @author Giuseppe Urso
 * 
 */
public class CamelExecutorTest extends TestCase {

	private static String testResourceDir;
	private static String testFile01;

	@Override
	protected void setUp() throws Exception {
		testResourceDir = "src/test/resources";	
		testFile01 = testResourceDir + "/test.txt";
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
		
	/**
	 * Test 
	 * 
	 */
	public void testInit() {
		boolean assertActual = false;
		CamelExecutor camelExecutor = new CamelExecutor();
		try {
			camelExecutor.init();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			assertActual=true;
			assertEquals("TEST FAILURE " + this.getName(), true, assertActual);	
		}
	}
}
