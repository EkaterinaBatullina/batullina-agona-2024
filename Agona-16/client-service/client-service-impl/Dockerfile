FROM openjdk:21-slim

WORKDIR /app

COPY target/client-service-impl-1.0-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]