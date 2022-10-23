# book-comet-api
You are hired as a software engineer to develop a new project: an e-commerce called "bookcomet.com", for a book publisher.
To integrate with other systems you decide to write an API for that.
You received only the following information from the business team:

## Business rules:
* The system cannot remove a book with positive inventory;
* The system cannot have a negative inventory;
* The system cannot have duplicate books (same name and author).

### Tecnologies
- Java
- JDK 17
- Spring Boot
- Spring Data JPA
- Lombok
- Junit
- H2 Database
- Modelmapper
- Docker


### Intalation.
mvn install 

### Create docker image
- The command must be run in the folder where the dockerfile is
- docker build  -t book-comet-api:1.0 .



### Create container 
- To test the application you can create a container after creating the image
- By default it will start from an in-memory database.
#### Command to create the container.
docker run -p 8181:8181 --name book-comet-api book-comet-api:1.0

### Testing API
After the application is running. Inside the project, there is a postman folder to facilitate testing.
You just need to import in postman and you will be able to do the tests.

![image](https://user-images.githubusercontent.com/17939912/197399174-51cf99a5-e8a7-4d38-8173-a042afb34814.png)


### Visit my repositories to see more projects like this.
- https://github.com/daylanbueno/sw-service
- https://github.com/daylanbueno/library-api

