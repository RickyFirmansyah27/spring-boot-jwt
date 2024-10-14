FROM openjdk:23-jdk-slim
WORKDIR /app
COPY target/myapp-api-0.0.1-SNAPSHOT.jar springbootjwt.jar
EXPOSE 8080
ENTRYPOINT exec java -jar springbootjwt.jar