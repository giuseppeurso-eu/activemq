====================================================================
Code examples about Java Message Service and Apache Active MQ message broker
====================================================================
**Giuseppe Urso - GITHUB**

Url: https://github.com/giuseppeurso-eu/ 
===========================================================

This repository contains the source code of some java samples which could refer to:

http://www.giuseppeurso.eu

This code and the accompanying materials are made available under the
terms of the GPLv3 license. The GPL (V2 or V3) is a copyleft license that
requires anyone who distributes code or a derivative work to make the
source available under the same terms. V3 is similar to V2, but further
restricts use in hardware that forbids software alterations (see LICENSE.txt).

=================================
JMS and ACTIVE MQ EXAMPLES
=================================

**Requirements**

Apache Maven 2.+
Java 1.6+
Active _MQ 5.8+

**Compile Source**
```
$ mvn clean compile
```
**Active MQ Console**

http://localhost:8161/admin/
admin / admin

Example 01: async-queues-failover
----------------------------------
Point-to-point messaging. The Producer creates 2 queues and sends messages to a Master/Slave AMQ Broker using the failover protocol.
Messages are processed from a Consumer in an asynchronous manner.
```
$ cd async-queues-failover
$ mvn clean compile
$ mvn exec:java -Pproducer
$ mvn exec:java -Pconsumer
```

Example 02: ssl-transport
----------------------------------
SSL transport. Producer and Consumer send/receive messages over a SSL tunnel. In order to successfully connect to the AMQ broker via SSL, the keystore, 
the keystore password, and the truststore are provided using JSSE.
```
$ cd ssl-transport
$ mvn clean install
$ mvn exec:java -Pproducer
$ mvn exec:java -Pconsumer
```

Example 03: spring-basic
------------------------
Simple project for asynchronous requests (like Ex. 01), but based on Spring-JMS. 
Running the application, it starts Spring context and the producer; then Spring automatically instantiates the message listeners (for consumers). 
```
$ cd spring-basic
$ mvn exec:java
```

Example 04: rest-producer
------------------------
Messaging example using the Active MQ REST APIs.

- Consumer
The example uses the DefaultMessageListenerContainer of spring framework for the JMS Consumer. The Consumer is created through the Spring beans context which 
automatically instantiates the message listener when you run the example.

- Producer
The Producer consists of a series of regular HTTP POST which send messages to a queue named "Coda01" as JSON format.
The messages are published on the ActiveMQ Server using the RESTful API implementation. A numbers of messages can be produced by sending a POST request to the
server with Basic Authentication. The ActiveMQ RESTful API is available by default at:

http://your_server_amq:8161/api/message

```
### For example, you can produce by sending a POST request to the server, like:
> curl -u admin:admin -H "Content-Type: application/json" -d '{"myMessage":"this is the message"}' http://localhost:8161/api/message/Coda01?type=queue
```

- Run example
``` 
$ cd amq-example
$ mvn clean compile
$ mvn exec:java -Dexec.args="[AMQ_HOST] [NUMBER_OF_MESSAGES]"

### For example, the following sends 10 messages to the localhost AMQ Server:
$ mvn exec:java -Dexec.args="localhost 10"
```
Press CTRL+X to finish



Finally , messages are processed from the Consumer in asynchronous manner.


**LINK** 

http://activemq.apache.org/index.html
