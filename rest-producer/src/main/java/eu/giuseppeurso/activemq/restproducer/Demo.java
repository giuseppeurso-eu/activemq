package eu.giuseppeurso.activemq.restproducer;

import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import org.apache.log4j.Logger;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.springframework.context.support.FileSystemXmlApplicationContext;


/**
 * This class simulates a Point-to-point messaging. The example uses the DefaultMessageListenerContainer of spring framework for the JMS Consumer.
 * The messages are published on the ActiveMQ Server using the RESTful API implementation.
 * A numbers of messages can be produced by sending a POST request to the server with Basic Authentication. The ActiveMQ RESTful API is available
 * by default at http://localhost:8161/api/message</br>
 * The Consumer is created through the Spring beans context.
 * The Producer consists of a series of regular HTTP POST which send messages to a queue named "Coda01" as JSON format.

 * @author Giuseppe Urso
 *
 */
public class Demo {
	
private static Logger _log = Logger.getLogger(Demo.class);

/**
 * The standard main method which is used by JVM to start execution of the Java class.
 * @param args
 * @throws Exception
 */
public static void main(String[] args) throws Exception {
		
		// Start Spring context for the Consumer 
		FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("src/main/resources/SpringBeans.xml");
		
        String total = null;
        String host = "localhost";
        try {
        	host = args[0];
        	total = args[1];
        	_log.info("-------------------------------------");
        	_log.info("Active MQ host >>>> "+host);
        	_log.info("Total messages to send >>>> "+total);
        	_log.info("-------------------------------------");
        	// Start the  publishing of some  messages using regular HTTP POST
        	sendMessage(host, Integer.valueOf(total));
		} catch (Exception e) {
			_log.info("------------------------------------------------------------------------------------------------------------------");
        	_log.info("PAY ATTENTION!!! You must enter the Active MQ host and a number representing the total  messages to produce");
        	_log.info("------------------------------------------------------------------------------------------------------------------");
        	_log.info("Example: mvn exec:java -Dexec.args=\"localhost 10\"");
			System.exit(1);
		}
	}
	
/**
 * A method to publish messages using a regular HTTP POST with Basic Authentication. You must provide the 
 * Active MQ host and total number of messages to send.
 * @param host - the Active MQ hostname or IP
 * @param total - the total message to send
 * @throws InterruptedException
 */
public static void sendMessage(String host, int total) throws InterruptedException{
	for (int i = 0; i < total; i++) {
		_log.info("Sending the message number: "+i);

		// This is equivalent to:
		//> curl -u admin:admin -H "Content-Type: application/json" -d '{"idnodo":10}' http://localhost:8161/api/message/Coda01?type=queue
		try {
			String url = "http://"+host+":8161/api/message/Coda01?type=queue";
			URL obj = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			String userpass = "admin" + ":" + "admin";
			String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes("UTF-8"));
			conn.setRequestProperty ("Authorization", basicAuth);

			File f = new File("src/test/resources/test-01.jpg");

			Base64 b64 = new Base64();
			byte[] base64Image =  b64.encode(FileUtils.readFileToByteArray(f));
			Random random = new Random();
			
			// Create the plain text message as JSON object
			JSONObject json = new JSONObject();
			json.put("idnodo", random.nextInt(900000));
			json.put("feature", "[0.1,0.2]");
			// Uncomment the base64 image format, if you want a verbose output stacktrace.   
			//json.put("image", new String(base64Image, "UTF8"));
			json.put("image", "base64 dell'immagine principale");
			json.put("alert", true);
			json.put("idSospetto", random.nextInt(900000));
			json.put("distanza", random.nextInt(900000));
			json.put("imagectx", "base64 dell'immagine di contesto di individuazione");

			OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
			out.write(json.toString());
			out.close();

			new InputStreamReader(conn.getInputStream());   

		} catch (Exception e)                      {
			e.printStackTrace();
		}
		// Simulate a sleep (from 1 to 5 secs) between a produced message and another. 
		Thread.sleep((long)(1000+Math.random()*4000));
	}      
  }

/**
 * A method to check if a string can be parsed as Integer
 * @param s
 * @return
 */
public static boolean isInteger(String s) {
    try { 
        Integer.parseInt(s); 
    } catch(NumberFormatException e) {
        return false; 
    } catch(NullPointerException e) {
        return false;
    }
    return true;
}

}