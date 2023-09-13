FROM maven:3.8.4-openjdk-17-slim AS build

COPY pom.xml ./
COPY .mvn .mvn
COPY src src

RUN mvn clean install -DskipTests

FROM openjdk:17-jdk-slim

WORKDIR auth-side-services

COPY --from=build target/*.jar /app/auth-side-services.jar

EXPOSE 9090

ENTRYPOINT ["java", "-jar", "/app/auth-side-services.jar"]
