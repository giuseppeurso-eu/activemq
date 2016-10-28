/**
 * This file is part of GiuseppeUrso-EU Software.
 * 
 * GiuseppeUrso-EU Software is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *             
 * GiuseppeUrso-EU Software Software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with GiuseppeUrso-EU Software.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package eu.giuseppeurso.activemq.camelpollingdirectory;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.RouteDefinition;

/**
 * The concrete Camel RouteBuilder for the file Consumer. It implements the routing configurations by using Java DSL (Domain Specific Language).
 * The use of DSL avoids the need for XML-based configurations.
 * A Java DSL is used to build DefaultRoute instances in a CamelContext for smart routing.
 * 
 * @see <a href="http://camel.apache.org/java-dsl.html">Java DSL and Camel</a> 
 * @see <a href="http://camel.apache.org/component.html">Camel File Component</a>
 * @see <a href="http://camel.apache.org/polling-consumer.html">Polling Consumer</a>
 * @see <a href="http://www.enterpriseintegrationpatterns.com/patterns/messaging/PollingConsumer.html">EIP Polling Consumer</a>
 * 
 * @author Giusepe Urso
 *
 */
public class ConsumerRouteBuilder extends RouteBuilder {
 
	private static ResourceBundle rb = ResourceBundle.getBundle("configuration");
	
	final static String SOURCE_FOLDER = rb.getString("consumer.sourceFolder").trim();
	final static String DEST_FOLDER = rb.getString("consumer.destFolder").trim();
	final static String CONSUMER_INITIAL_DELAY = rb.getString("consumer.initialDelay").trim();
	final static String CONSUMER_DELAY = rb.getString("consumer.delay").trim();
	final static String CONSUMER_LOCK = rb.getString("consumer.lock").trim();
	final static String CONSUMER_LOCK_INTERVAL = rb.getString("consumer.lock.checkInterval").trim();
	
	
	/**
	 * The Camel route definition. The method uses the fluent builder syntax to build routing configurations.</br>
	 * The route includes only "from" definition which is the consumer that is kick-starting our routing flow. It will wait for 
	 * messages to arrive on the direct queue and then dispatch the message. Instead of "to" definition, 
	 * the invoice importer Processor is invoked.</br>
	 * The URI format makes uses of Camel File component which provides access to file systems, allowing files 
	 * to be processed by any other Camel Components or messages from other components to be saved to disk.
	 * 
	 * @see <a href="http://camel.apache.org/file2.html">Camel URI Options</a>
	 * 
	 */
    public void configure() throws Exception {
		if (!SOURCE_FOLDER.isEmpty()) {
			System.out.println("Configuring Camel Endpoint with source dir: " + SOURCE_FOLDER);
			RouteDefinition def = from("file://" + SOURCE_FOLDER +
					"?initialDelay=" + CONSUMER_INITIAL_DELAY +
					"&delay=" + CONSUMER_DELAY +
					"&readLock="+ CONSUMER_LOCK +
					"&readLockCheckInterval=" + CONSUMER_LOCK_INTERVAL);
			def.process(new ConsumerProcessor());
			System.out.println("Consumer processor initialized. Ready to receive files in: " + SOURCE_FOLDER);
		} else {
			System.err.println("Consumer processor initialization interrupted. The Route Defintion was not created because SOURCE_FOLDER is not setted in the configuration file.");
		}	
    }
 

    public static Map<String, String> getPropertiesMap(){
    	Map<String, String> values = new HashMap<>();
    	values.put("SOURCE_FOLDER", SOURCE_FOLDER);
    	values.put("CONSUMER_DELAY", CONSUMER_DELAY);
    	values.put("CONSUMER_INITIAL_DELAY", CONSUMER_INITIAL_DELAY);
    	values.put("CONSUMER_LOCK", CONSUMER_LOCK);
    	values.put("CONSUMER_LOCK_INTERVAL", CONSUMER_LOCK_INTERVAL);
    	return values;
	}


	public static String getDestFolder() {
		return DEST_FOLDER;
	}
    
    
}
