package eu.giuseppeurso.activemq.scheduledconsumer;

import org.springframework.context.support.FileSystemXmlApplicationContext;

public class App 
{
	public static void main(String[] args) throws Exception {
        FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("src/main/resources/spring-beans.xml");
        Producer producer = (Producer)context.getBean("producer");
        producer.start();
	}
}