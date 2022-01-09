FROM maven:latest 

WORKDIR /tmp/backend
COPY ./ /tmp/backend/

RUN mvn clean package
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "target/word-counter-0.0.1-SNAPSHOT.jar"]