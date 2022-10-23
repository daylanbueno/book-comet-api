FROM openjdk:17
LABEL maintainer="Dailan Bueno / daylansantos@gmail.com"
WORKDIR /app
COPY   target/book-comet-api-0.0.1-SNAPSHOT.jar /app/book-comet-api.jar
ENTRYPOINT ["java", "-jar", "book-comet-api.jar"]
EXPOSE 8181
