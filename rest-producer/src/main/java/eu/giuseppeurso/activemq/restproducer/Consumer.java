package eu.giuseppeurso.activemq.restproducer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import org.json.JSONObject;

import org.apache.log4j.Logger;


public class Consumer implements MessageListener {

	private static Logger _log = Logger.getLogger(Consumer.class);
	
    public void onMessage(Message message)
    {
        try
        {
         if (message instanceof TextMessage) {
        		TextMessage textMessage = (TextMessage) message;
        		System.out.println("Received message: '" + textMessage.getText()+ "'");
        		// TODO...
//        		JSONObject json = new JSONObject(textMessage.getText());
//        		json.getString("image");
        		try {
        			// Simulate a sleep (from 1 to 5 secs) before a new message consumption
        			Thread.sleep((long)(1000+Math.random()*4000));
				} catch (InterruptedException e) {	}
                _log.debug("Message map processed >> " + textMessage);
            } else {
            	_log.debug("Invalid message received");
            }
        }
        catch (JMSException e)
        {
            System.out.println("Caught:" + e);
            _log.error("Consumer exception: "+ e);
            e.printStackTrace();
        }
    }

    
}
