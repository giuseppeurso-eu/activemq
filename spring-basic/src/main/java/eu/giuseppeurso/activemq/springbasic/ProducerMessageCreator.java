package eu.giuseppeurso.activemq.springbasic;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.log4j.Logger;
import org.springframework.jms.core.MessageCreator;

/**
 * 
 * @author cira
 *
 */
public class ProducerMessageCreator implements MessageCreator {

	private static Logger _log = Logger.getLogger(ProducerMessageCreator.class);
	
	Destination destination;
	
	public ProducerMessageCreator(Destination destination) {
		this.destination = destination;
	}
	
	public Message createMessage(Session session) throws JMSException {
		double i = Math.random();
		MapMessage mapMessage = session.createMapMessage();
		mapMessage.setString("action", "action_"+i);
		mapMessage.setString("ID", "ID"+i);
		mapMessage.setString("description", "messageStr"+i);
		_log.debug("Sending: " + ((ActiveMQMapMessage)mapMessage).getContentMap() + " on destination: " + destination);
		return mapMessage;
	}

}
