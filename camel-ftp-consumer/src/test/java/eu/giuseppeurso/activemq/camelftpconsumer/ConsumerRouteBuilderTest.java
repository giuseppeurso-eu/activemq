package eu.giuseppeurso.activemq.camelftpconsumer;

import java.util.Map;

import eu.giuseppeurso.activemq.camelftpconsumer.ConsumerRouteBuilder;
import junit.framework.TestCase;

/**
 * Test cases 
 * 
 * @author Giuseppe Urso
 * 
 */
public class ConsumerRouteBuilderTest extends TestCase {

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
	public void testGetPropertiesMap() {
		boolean assertActual = false;
		
		if (ConsumerRouteBuilder.getPropertiesMap()!=null && ConsumerRouteBuilder.getPropertiesMap().size()>0) {
			assertActual=true;
			for (Map.Entry<String, String> entry : ConsumerRouteBuilder.getPropertiesMap().entrySet())
			{
			    System.out.println("TEST [" + this.getName()+"] "+entry.getKey() + ": " + entry.getValue());
			}
		}
		assertEquals("TEST FAILURE " + this.getName(), true, assertActual);	
	}
}
