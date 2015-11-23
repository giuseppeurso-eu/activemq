package eu.giuseppeurso.activemq.scheduledconsumer;


import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;

import org.apache.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;

/**
 * The message Producer.
 * @author Giuseppe Urso
 *
 */
public class Producer {

	private static Logger _log = Logger.getLogger(Producer.class);
	private static JmsTemplate template;
	private Destination[] destinations;
	
	/**
	 * A default constructor.
	 */
	public Producer() {
    }    
	
	/**
	 * Basic setter injection for property "JmsTemplate" using Spring Beans definition. See the Spring XML
	 * application context file. 
	 * @param template
	 */
	public void setTemplate(JmsTemplate template) {
		this.template = template;
	}
	
	/**
	 * JmsTemplate getter.
	 * @return the template
	 */
	public static JmsTemplate getTemplate() {
		return template;
	}
	
	
	/**
	 * @return the destinations
	 */
	public Destination[] getDestinations() {
		return destinations;
	}

	/**
	 * @param destinations the destinations to set
	 */
	public void setDestinations(Destination[] destinations) {
		this.destinations = destinations;
	}

	/**
	 * This method uses the JmsTemplate's convertAndSend() to send messages on a given Queue as string.
	 * @param message
	 * @param queueName
	 * @throws JMSException
	 */
	public static void sendMessage(Message message, String queueName) throws JMSException {
		template.convertAndSend(queueName, message);
        _log.info("Published messages "+message.getJMSMessageID()+" on queue: "+queueName);
    }
	
	
	
	/**
	 * This method uses the JmsTemplate's convertAndSend() to send messages on a Queue which comes from the Destination property in the Spring bean definition.
	 * @param message
	 */
	public void sendMessage(Message message) {
		int idx = 0;
		while (true) {
			idx = (int)Math.round(destinations.length * Math.random());
			if (idx < destinations.length) {
				break;
			}
		}
		try {
			Destination destination = destinations[idx];
			template.convertAndSend(destination, message);
			_log.debug("Published message with JMS Correlation ID: "+message.getJMSCorrelationID()+" on queue: "+destination.toString());
		} catch (JMSException e) {
			_log.error(e);
		}
		
	}

}