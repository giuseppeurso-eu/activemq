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

**LINK** 

http://activemq.apache.org/index.html
