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



