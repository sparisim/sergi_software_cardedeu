# sergi_software_cardedeu

Check Api Documentation<br/>
http://localhost:8080/swagger-ui.html

**Start docker**<br/>
Run a build as usual, executing next command mvn clean install

* For windows machine run docker-start-windows script
* For linux/max run docker-start-windows bat

**Other command docker useful**<br/>
* docker ps - list registered images
* docker container ls - list of containers
* docker port beers-service - test if the port works
* docker-compose up -- build the docker image/container and start up

The docker image/container could be created directly using the mvn repackage given that is using spring boot 2.3 or higher

Also, if it assumes that the operating system were always the same operating system, it could create the docker container/image during the build using running the script with maven plugins.

** Basic API ** 
By now all requests are allowed. In a future version the acccess will be restricted

* find one - http://localhost:8080/getCatalogueBeerId?beerId={beerId} - Http.OK if it finds, otherwise returns 404

* find all - http://localhost:8080/getAllBeers - Http.OK if it finds, otherwise returns 404

* adding beer - http://localhost:8080/addingBeer - HttpOk if it can be created, otherwise 404
- Example Request {
 "manufacturer":"Damm",
 "graduation":8,
 "name": "Free Dam"
}
Updating - http://localhost:8080/updateBeer?beerId=1 - If exists update all values else eihter existing beer or other issue. In that case returning 404. 
* delete http://localhost:8080/deleteCatalogueBeerId?beerId={beerId} Returns OK, if it has been removed, otherwise returns BAD_REQUEST

 Obviously, the api can be improved and new feautrs will be added in future versions



