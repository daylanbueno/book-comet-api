# book-comet-api


### Tecnologies
- Java
- JDK 17
- Spring Boot
- Sring Data JPA
- Lombok
- Junit
- H2 Database
- Modelmapper
- Docker


### Intalation.
mvn install 

### Create docker image
- The command must be run in the folder where the dockerfile is
docker build  -t book-comet-api:1.0 .



### Create container 
- To test the application you can create a container after creating the image
- By default it will start from an in-memory database.
#### Command to create the container.
docker run -p 8181:8181 --name book-comet-api book-comet-api:1.0


