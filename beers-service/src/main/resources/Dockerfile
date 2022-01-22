FROM openjdk:8
VOLUME /tmp
COPY beers-service-1.0-SNAPSHOT.jar beers-service.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/beers-service.jar"]