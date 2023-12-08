FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/drone-api-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8081

CMD ["java", "-jar", "app.jar"]
