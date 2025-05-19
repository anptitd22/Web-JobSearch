# Build stage
FROM maven:3-openjdk-17 AS build
WORKDIR /app

# Copy the entire project structure
COPY . .

# Change to the directory containing pom.xml and build
WORKDIR /app/Back-end/Back-end
RUN mvn clean package -DskipTests

# Run stage
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the built WAR file from build stage
# Note: Adjust the path if your WAR file has a different name/path
COPY --from=build /app/Back-end/Back-end/target/DrComputer-0.0.1-SNAPSHOT.war drcomputer.war

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "drcomputer.war"]
