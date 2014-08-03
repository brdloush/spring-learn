spring-learn
============

Just a couple of trial-and-error projects I made (and don't want to forget) while learning couple of spring-subproject I didn't use yet..

* **spring-ws-server-wmocktests**
  * project derived from official spring-ws tutorial (the Server part). 
  * mvn jetty-run friendly
  * WSDL is autogenered by spring-ws. Its location: http://localhost:8080/spring-ws-server-wmocktests/holidayService/holiday.wsdl
  * JUnit semi-integration test (starts up spring contexts, mocks "business" bean called by webservice endpoint using Mockito) 
  
* **spring-ws-client**
  * simple webapp containing Spring MVC + Thymeleaf + simple SpringWS client (using JAXB2 marshalled objects)
  
* **spring-ws-schema**
  * shared project containing just the XSD schema files & auto-generated JAXB2 classes for those XSD files
  * manually-built XSD schema 
  * autogenerated JAXB2-beans 
