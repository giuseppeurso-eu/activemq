package eu.giuseppeurso.activemq.springbasic;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.log4j.Logger;

/**
 * 
 * @author cira
 *
 */
public class Consumer implements MessageListener {

	private static Logger _log = Logger.getLogger(Consumer.class);
	
    public void onMessage(Message message)
    {
        try
        {
            if (message instanceof MapMessage) {
            	MapMessage mapMessage = (MapMessage)message;
                String description = ((ActiveMQMapMessage)mapMessage).getContentMap().toString();
                _log.debug("Message map received >> " + description);
                try {
					Thread.sleep((long)(1000+Math.random()*4000));
				} catch (InterruptedException e) {	}
                _log.debug("Message map processed >> " + description);
            } else {
            	_log.debug("Invalid message received");
            }
        }
        catch (JMSException e)
        {
            System.out.println("Caught:" + e);
            e.printStackTrace();
        }
    }

    
}
