FROM adoptopenjdk:11-jdk-hotspot

MAINTAINER kelvinVargas

USER root

COPY wait-for-it.sh /wait-for-it.sh

RUN chmod +x /wait-for-it.sh

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} AdmUsers.jar

ENTRYPOINT ["/bin/bash", "-c", "./wait-for-it.sh"]
