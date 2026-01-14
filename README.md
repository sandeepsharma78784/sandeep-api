to run the application
1. mvn clean package [while doing this make sure strong net connection , coz it will download jar mentioned in pom.xml] 
2. then run java -jar target\application-0.0.1-SNAPSHOT.jar
3. version used:
    Spring Boot 3.5.4
    → Spring Framework 6.2.x
        Spring Boot 3.x ka rule:
        ❌ Java 8 / 11 NOT supported
        ✅ Java 17+ REQUIRED
    with  Spring Boot 3.5.4 we will get Spring Security 6.4.x
4. psql server to installation k time hi statrt ho gaya tha and as a service continue chl raha he
    hum psql client programme named as psql ka use kr k database se interact krte he and db operation krte he.
    so humara doubt ki springboot start krne se phle db start krna  
        nahi db server always in running state as window service.
     bs app.properties me config de diya and related dependecy add kr di pom.xml ,
        is dependecy me vo db se interact krne ka code he
        jiska use hum jpa repository me krege.
