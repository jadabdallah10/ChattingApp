# Database and RabbitMQ Connection Utilities

This project provides utility classes for connecting to an Oracle database and RabbitMQ message broker using Java. The connection details are specified in a properties file.

## Prerequisites

- **Java Development Kit (JDK)**: Ensure you have JDK installed on your machine.
- **Apache Maven** (optional): For managing dependencies, though not required for this setup.
- **JDBC Driver**: Required for connecting to the database. If using a different database, download the appropriate JDBC driver.

## Configuration

### Combined Properties File

Create a file named `connection.properties` in the root directory of your project with the following structure:

```properties
# Database connection properties
db.username=<your_database_username>
db.password=<your_database_password>
db.url=<jdbc:database_type://host:port/database_name>

# RabbitMQ connection properties
rabbitmq.host=<rabbitmq_host>
rabbitmq.username=<rabbitmq_username>
rabbitmq.password=<rabbitmq_password>



# WEB-INF Dependencies
- activation-1.1.jar
- asm-5.0.4.jar
- commons-logging-1.2.jar
- cxf-core-3.1.11.jar
- cxf-rt-bindings-soap-3.1.11.jar
- cxf-rt-bindings-xml-3.1.11.jar
- cxf-rt-databinding-jaxb-3.1.11.jar
- cxf-rt-frontend-jaxws-3.1.11.jar
- cxf-rt-frontend-simple-3.1.11.jar
- cxf-rt-transports-http-3.1.11.jar
- cxf-rt-ws-addr-3.1.11.jar
- cxf-rt-wsdl-3.1.11.jar
- cxf-rt-ws-policy-3.1.11.jar
- jackson-annotations-2.17.0.jar
- jackson-core-2.17.0.jar
- jackson-databind-2.17.0.jar
- jackson-dataformat-xml-2.17.0.jar
- javax.activation-api-1.2.0.jar
- javax.annotation-3.1.1.jar
- javax.ws.rs-api-2.1.1.jar
- jaxb-api-2.3.1.jar
- jaxb-core-2.2.11.jar
- jaxb-impl-2.2.11.jar
- jaxws-api-2.2.6.jar
- jsr181-api-1.0-MR1.jar
- jstl-api-1.2.jar
- neethi-3.0.3.jar
- orai18n-19.3.0.0.jar
- saaj-api-1.3.4.jar
- spring-aop-4.3.8.RELEASE.jar
- spring-beans-4.3.8.RELEASE.jar
- spring-context-4.3.8.RELEASE.jar
- spring-core-4.3.8.RELEASE.jar
- spring-expression-4.3.8.RELEASE.jar
- spring-web-4.3.8.RELEASE.jar
- stax2-api-3.1.4.jar
- woodstox-core-asl-4.4.1.jar
- wsdl4j-1.6.3.jar
- xml-resolver-1.2.jar
- xmlschema-core-2.2.1.jar


# Java Resources Dependencies
- ampq-client-5.0.0.jar
- javax.servlet-3.1.jar
- javax.servlet-api-4.0.1.jar
- json-20240303.jar
- ojdbc8.jar
- orai18n-19.3.0.0.jar
- sl4j-api-1.7.36.jar
- sl4j-core-1.7.36.jar

# Tomcat 7.0 Dependencies
- ampq-client-5.0.0.jar
- annotations-api.jar
- catalina.jar
- catalina-ant.jar
- catalina-ha.jar
- catalina-tribes.jar
- commons-collections4-4.4.jar
- commons-compress-1.26.2.jar
- commons-io-2.16.1.jar
- commons-logging-1.2.jar
- ecj-4.2.2.jar
- ecj-4.4.2.jar
- ecj-P20140317-1600.jar
- el-api.jar
- jackson-annotations-2.17.0.jar
- jackson-core-2.17.0.jar
- jackson-databind-2.17.0.jar
- jackson-dataformat-xml-2.17.0.jar
- jasper.jar
- jasper-el.jar
- javax.servlet-3.1.jar
- javax.servlet-api-4.0.1.jar
- json-20230618.jar
- jsp-api.jar
- log4j.properties
- log4j-1.2.15.jar
- ojdbc8.jar
- orai18n-19.3.0.0.jar
- servlet-api.jar
- slf4j-api-1.7.36.jar
- slf4j-simple-1.7.36.jar
- SparseBitSet-1.2.jar
- tomcat7-websocket.jar
- tomcat-api.jar
- tomcat-coyote.jar
- tomcat-dbcp.jar
- tomcat-i18n-de.jar
- tomcat-i18n-es.jar
- tomcat-i18n-fr.jar
- tomcat-i18n-ja.jar
- tomcat-i18n-ko.jar
- tomcat-i18n-ru.jar
- tomcat-i18n-zh-CN.jar
- tomcat-jdbc.jar
- tomcat-util.jar
- websocket-api.jar
- xmlbeans-3.1.0.jar

