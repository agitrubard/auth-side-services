FROM maven:3.9.6-amazoncorretto-21 AS build

COPY pom.xml ./
COPY .mvn .mvn
COPY src src

RUN mvn clean install -DskipTests

RUN yum update && yum install -y curl

FROM openjdk:21-jdk

WORKDIR auth-side-services

COPY --from=build target/*.jar /app/auth-side-services.jar

EXPOSE 9090

ENTRYPOINT ["java", "-jar", "/app/auth-side-services.jar"]
