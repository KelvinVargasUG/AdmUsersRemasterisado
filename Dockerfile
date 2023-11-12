FROM adoptopenjdk:11-jdk-hotspot
MAINTAINER kelvinVargas

RUN groupadd -r spring && useradd -r -g spring spring

USER spring:spring

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} AdmUsers-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/AdmUsers-0.0.1-SNAPSHOT.jar"]