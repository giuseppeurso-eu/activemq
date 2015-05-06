package eu.giuseppeurso.activemq.springbasic;

import org.springframework.context.support.FileSystemXmlApplicationContext;

public class App 
{
	public static void main(String[] args) throws Exception {
        FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("src/main/resources/SpringBeans.xml");
        Producer producer = (Producer)context.getBean("producer");
        producer.start();
	}
}