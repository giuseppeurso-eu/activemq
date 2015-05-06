package eu.giuseppeurso.activemq.asyncqueuesfailover;

import java.util.ResourceBundle;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Producer {

    ResourceBundle settings = ResourceBundle.getBundle("configuration");
	private String brokerURL = settings.getString("amq.url");
	private static transient ConnectionFactory factory;
    private transient Connection connection;
    private transient Session session;
    private transient MessageProducer producer;
    
    private static int count = 10;
    private static int total;
    private static int id = 1000000;
    
    private String jobs[] = new String[]{"coda1", "coda2"};
    
    public Producer() throws JMSException {
    	factory = new ActiveMQConnectionFactory(brokerURL);
    	System.out.println("Broker URL is: " + brokerURL);
    	connection = factory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        producer = session.createProducer(null);
        
    }    
    
    public void close() throws JMSException {
        if (connection != null) {
            connection.close();
        }
    }    
    
	public static void main(String[] args) throws JMSException {
    	Producer producer = new Producer();
        while (total < 300) {
            for (int i = 0; i < count; i++) {
            	producer.sendMessage();
            }
            total += count;
            System.out.println("Published '" + count + "' of '" + total + "' job messages");
            try {
              Thread.sleep(1000);
            } catch (InterruptedException x) {
            }
          }
        producer.close();

	}
	
    public void sendMessage() throws JMSException {
        int idx = 0;
        while (true) {
            idx = (int)Math.round(jobs.length * Math.random());
            if (idx < jobs.length) {
                break;
            }
        }
        String job = jobs[idx];
        Destination destination = session.createQueue("JOBS." + job);
        Message message = session.createObjectMessage(id++);
        // Create plain-text clear messages (used for test)
        //Message message = session.createTextMessage("valore id: " + id++);
        System.out.println("Sending: id: " + ((ObjectMessage)message).getObject() + " on queue: " + destination);
        producer.send(destination, message);
       
      
    }	

}
