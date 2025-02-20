FROM openjdk:23
WORKDIR /app
COPY ./build/libs/spring-webflux-docker-0.0.1-SNAPSHOT.jar app.jar
COPY ./src/main/resources/db/migration ./app/resources/db/migration
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]