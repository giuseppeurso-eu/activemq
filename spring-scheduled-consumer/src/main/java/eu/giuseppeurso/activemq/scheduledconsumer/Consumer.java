package eu.giuseppeurso.activemq.scheduledconsumer;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.log4j.Logger;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

/**
 * 
 * @author Giuseppe Urso
 *
 */
public class Consumer implements MessageListener {

	private static Logger _log = Logger.getLogger(Consumer.class);
	
	private Object consumerListener;
	
    /**
	 * @return the consumerListener
	 */
	public Object getConsumerListener() {
		return consumerListener;
	}

	/**
	 * @param consumerListener the consumerListener to set
	 */
	public void setConsumerListener(Object consumerListener) {
		this.consumerListener = consumerListener;
	}


	/**
	 * The onMessage() listener.
	 */
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

    
    
    /**
	 * The method invoked by the Quartz MethodInvokingJobDetailFactoryBean in order to start a scheduled message consumption
	 * (see the Spring bean definition file).
	 */
	public void startConsumption(){
		_log.info("Starting the Consumer...");
		((DefaultMessageListenerContainer) getConsumerListener()).start();
		_log.info("JOB STARTED!");
	}
	
	/**
	 * The method invoked by the Quartz MethodInvokingJobDetailFactoryBean in order to stop a message consumption.
	 * (see the Spring bean definition file).
	 */
	public void stopConsumption(){
		_log.debug("Stopping the Consumer... ");
		((DefaultMessageListenerContainer) getConsumerListener()).stop();
		_log.info("JOB FINISHED!");
	}
	
	
	
}
