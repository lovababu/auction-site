# Auction API.

## Technology Stack.
 - SpringBoot.
 - Hibernate ORM.
 - H2 database.
 - HikariCP.
 - Jersey.
 - JSON.
 - Lombok.
 - Logback.

## Prerequisites.
 - JDK 8.
 - Maven.  

## App Configuration.  
 - App Configuration can be found in ```resources/applicaiton.properties```.  

## How to Run.  
  ### From command prompt.  
  - Step into root directory and issue the below command.  
    ``` $mvn spring-boot:run ```  
  ### From IDE.  
  - Open ```SapAuctionSiteApplication.java``` and run as java application.  

## H2 console Access.
 - http://[host:port]/h2-console
 - and specify the jdbcURl as below.
   ```jdbc:h2:<AppRoot directory>\db-data\auction;MODE=MySQL;DB_CLOSE_ON_EXIT=FALSE```
