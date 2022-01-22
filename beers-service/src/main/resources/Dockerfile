FROM openjdk:8
VOLUME /tmp
ADD ../..beers-service-1.0-SNAPSHOT.jar beers-service.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","beers-service.jar"]