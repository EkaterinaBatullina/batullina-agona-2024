FROM openjdk:21-slim

WORKDIR /app

COPY target/user-service-impl-1.0-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]