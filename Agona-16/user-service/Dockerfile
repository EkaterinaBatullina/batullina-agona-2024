FROM openjdk:21-slim

COPY user-service-impl/target/user-service-impl-1.0-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]