# Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY Back-end/Back-end/pom.xml .
RUN mvn dependency:go-offline
COPY Back-end/Back-end/src ./src
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/DrComputer-0.0.1-SNAPSHOT.war drcomputer.war
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "drcomputer.war"]
