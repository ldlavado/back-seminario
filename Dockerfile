FROM maven:latest AS build
COPY src /src
COPY pom.xml .
RUN mvn -f ./pom.xml clean package

FROM amazoncorretto:17-alpine-jdk
COPY --from=build /target/integraservicios-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app.jar"]