FROM openjdk:17-jdk-slim

# Refer to Maven build -> finalName
ARG JAR_FILE=target/webflux-heroes-application-0.0.1-SNAPSHOT.jar

# cd /opt/app
WORKDIR /opt/app

# cp target/heroes-application-0.0.1-SNAPSHOT.jar /opt/app/app.jar
COPY ${JAR_FILE} app.jar

# java -jar /opt/app/app.jar
ENTRYPOINT ["java","-jar","app.jar"]