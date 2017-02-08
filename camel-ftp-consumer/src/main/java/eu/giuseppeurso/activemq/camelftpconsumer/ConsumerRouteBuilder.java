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
package eu.giuseppeurso.activemq.camelftpconsumer;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;


/**
 * The concrete Camel RouteBuilder for the file Consumer. It implements the routing configurations by using Java DSL (Domain Specific Language).
 * The use of DSL avoids the need for XML-based configurations.
 * A Java DSL is used to build DefaultRoute instances in a CamelContext for smart routing.
 * 
 * @see <a href="http://camel.apache.org/java-dsl.html">Java DSL and Camel</a> 
 * @see <a href="http://camel.apache.org/ftp.html">Camel FTP Component</a>
 * 
 * @author Giusepe Urso - www.giuseppeurso.eu
 *
 */
public class ConsumerRouteBuilder extends RouteBuilder {
 
	private static ResourceBundle rb = ResourceBundle.getBundle("configuration");
	
	final static String DEST_FOLDER = rb.getString("consumer.destFolder").trim();
	
	final static String FTP_HOST = rb.getString("consumer.ftp.host").trim();
	final static String FTP_PORT = rb.getString("consumer.ftp.port").trim();
	final static String FTP_USER = rb.getString("consumer.ftp.user").trim();
	final static String FTP_PASSWORD = rb.getString("consumer.ftp.password").trim();
	final static String FTP_PROTOCOL = rb.getString("consumer.ftp.protocol").trim();
	final static String FTP_DIR = rb.getString("consumer.ftp.directoryName").trim();
	final static String FTP_OPTIONS = rb.getString("consumer.ftp.options").trim();
	
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
    	if (!FTP_HOST.isEmpty()) {
			System.out.println("Trying to create the Camel route definition on the remote FTP server:" + FTP_HOST);
			String ftpUri = FTP_PROTOCOL+"://" + FTP_USER+"@"+FTP_HOST+":"+FTP_PORT+"/"+FTP_DIR+"?password="+FTP_PASSWORD;
			from(ftpUri+"&"+FTP_OPTIONS).to("file://"+DEST_FOLDER).log(LoggingLevel.INFO, "Processing ${id}");
			System.out.println("Camel FTP route initialized. Ready to receive Invoices from: " + ftpUri);
		}else {
			System.out.println("FTP Route not intialized. Check property 'ftp.host' in the configuration file");
		}	
    }
 

    /**
     * For test purpose only.
     * @return
     */
    public static Map<String, String> getPropertiesMap(){
    	Map<String, String> values = new HashMap<>();
    	values.put("DEST_FOLDER", DEST_FOLDER);
    	values.put("FTP_HOST", FTP_HOST);
    	values.put("FTP_PORT", FTP_PORT);
    	values.put("FTP_USER", FTP_USER);
    	values.put("FTP_PASSWORD", FTP_PASSWORD);
    	values.put("FTP_PROTOCOL", FTP_PROTOCOL);
    	values.put("FTP_DIR", FTP_DIR);   	
    	return values;
	}  
    
    
    
}
