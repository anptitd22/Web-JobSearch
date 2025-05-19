# Build stage
FROM maven:3.8.6-openjdk-21 AS build
WORKDIR /src
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn install -DskipTests=true

# Run stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /run
COPY --from=build /src/target/hcaredemo-0.0.1-SNAPSHOT.jar .
EXPOSE 8888
ENV JAVA_OPTIONS="-Xmx2048m -Xms256m"
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTIONS -jar hcaredemo-0.0.1-SNAPSHOT.jar"]
