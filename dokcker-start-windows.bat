copy beers-service\target\beers-service-1.0-SNAPSHOT.jar beers-service\target\classes
docker-compose -f beers-service\target\classes\docker-compose.yml start beers-service