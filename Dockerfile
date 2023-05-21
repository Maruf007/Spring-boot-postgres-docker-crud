# Maven build container
FROM maven:3.8.6-openjdk-17 AS maven_build

COPY pom.xml /tmp/

COPY src /tmp/src/

WORKDIR /tmp/

RUN mvn package -DskipTests

#pull base image
FROM openjdk:17

#copy assignment to docker image from builder image
COPY --from=maven_build /tmp/target/assignment-0.0.1-SNAPSHOT.jar /data/assignment-0.0.1-SNAPSHOT.jar

#command to run jar
CMD java -jar /data/assignment-0.0.1-SNAPSHOT.jar