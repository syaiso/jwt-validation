FROM openjdk:17-jdk-slim-buster

MAINTAINER Sergio Aiso <syaiso@hotmail.com>

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} jwt-validation.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/jwt-validation.jar"]