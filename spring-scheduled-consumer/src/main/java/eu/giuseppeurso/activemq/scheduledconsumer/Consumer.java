package eu.giuseppeurso.activemq.scheduledconsumer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.log4j.Logger;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

/**
 * 
 * @author Giuseppe Urso
 *
 */
public class Consumer implements MessageListener {

	private static Logger _log = Logger.getLogger(Consumer.class);
	private Object sampleListener;

	/**
	 * @return the sampleListener
	 */
	public Object getSampleListener() {
		return sampleListener;
	}

	/**
	 * @param sampleListener the sampleListener to set
	 */
	public void setSampleListener(Object sampleListener) {
		this.sampleListener = sampleListener;
	}

	/**
	 * The onMessage() listener.
	 */
	public void onMessage(Message message)
    {
		 _log.info("Consuming message...");
        try
        {
        	if (message instanceof TextMessage) {
        		String content = ((ActiveMQTextMessage)message).getText();
                _log.info("Message received >> " + content);
                _log.info("Message JMS Correlation ID >> " + message.getJMSCorrelationID());
            } else {
            	_log.info("Invalid message received");
            }
        }
        catch (JMSException e)
        {
        	_log.error("Caught:" + e);
            
        }
    }
    
    /**
	 * The method invoked by the Quartz MethodInvokingJobDetailFactoryBean in order to start a scheduled message consumption
	 * (see the Spring bean definition file).
	 */
	public void startConsumption(){
		_log.info("Starting the Consumer...");
		((DefaultMessageListenerContainer) getSampleListener()).start();
		_log.info("JOB STARTED!");
	}
	
	/**
	 * The method invoked by the Quartz MethodInvokingJobDetailFactoryBean in order to stop a message consumption.
	 * (see the Spring bean definition file).
	 */
	public void stopConsumption(){
		_log.debug("Stopping the Consumer... ");
		((DefaultMessageListenerContainer) getSampleListener()).stop();
		_log.info("JOB FINISHED!");
	}
	
	
	
}
