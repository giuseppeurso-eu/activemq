package eu.giuseppeurso.activemq.springbasic;

import java.util.HashMap;

import javax.jms.Destination;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jms.core.JmsTemplate;

public class Producer {

	private static Logger _log = Logger.getLogger(Producer.class);
	
	private JmsTemplate template;
	private int count = 5;
	private int total;
	private Destination[] destinations;
	
	private HashMap<Destination,ProducerMessageCreator> creators = new HashMap<Destination,ProducerMessageCreator>();
		
	public void start() {
		while (total < 10) {
			for (int i = 0; i < count; i++) {
				sendMessage();
			}
			total += count;
			_log.debug("Published '" + count + "' of '" + total + "' price messages");
			try {
				Thread.sleep(10000+100*total);
			}
			catch (InterruptedException x) {}
		}
	}
	
	protected void sendMessage() {
		int idx = 0;
		while (true) {
			idx = (int)Math.round(destinations.length * Math.random());
			if (idx < destinations.length) {
				break;
			}
		}
		Destination destination = destinations[idx];
		template.send(destination, getMessageCreator(destination));
	}
	
	private ProducerMessageCreator getMessageCreator(Destination dest) {
		if (creators.containsKey(dest)) {
			return creators.get(dest);
		} else {
			ProducerMessageCreator creator = new ProducerMessageCreator(dest);
			creators.put(dest, creator);
			return creator;
		}
	}
	
	public void setTemplate(JmsTemplate template) {
		this.template = template;
	}
	
	public void setDestinations(Destination[] destinations) {
		this.destinations = destinations;
	}

}