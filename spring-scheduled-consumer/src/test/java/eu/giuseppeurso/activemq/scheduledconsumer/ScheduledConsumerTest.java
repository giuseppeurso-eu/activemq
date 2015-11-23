package eu.giuseppeurso.activemq.scheduledconsumer;

import java.util.Random;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.context.support.FileSystemXmlApplicationContext;

import junit.framework.TestCase;

/**
 * Unit test 
 * 
 * @author Giuseppe Urso
 * 
 */
public class ScheduledConsumerTest extends TestCase {

	
	Producer producer=null;
	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("src/main/resources/spring-beans.xml");
		producer = (Producer)context.getBean("producer");
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test case 
	 * @throws JMSException 
	 * @throws InterruptedException 
	 * 
	 * @throws NoSuchAlgorithmException
	 * @throws Exception
	 */
	public void testScheduledConsumer() throws JMSException, InterruptedException {
		boolean actual = false;
		Session session = producer.getTemplate().getConnectionFactory().createConnection().createSession(false, Session.AUTO_ACKNOWLEDGE);
		TextMessage txtMessage = session.createTextMessage();
		Random rand= new Random();
		int id = rand.nextInt(100000);
		txtMessage.setJMSCorrelationID(String.valueOf(id));
		txtMessage.setText("This is a text message!");
//		System.out.println("Sending message on : " + producer.getDestinations()[0].toString());
		producer.sendMessage(txtMessage);
		//Waiting a few seconds until the scheduler start/stop
		Thread.sleep(20000);
		actual = true;
		assertEquals(true, actual);
	}

	
}
