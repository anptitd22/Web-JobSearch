# Build stage - Sử dụng JDK 21 để build
FROM maven:3.8.7-openjdk-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests=true

# Run stage - Sử dụng JRE 21 để chạy
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080  # Thay đổi port nếu cần
ENV JAVA_OPTS="-Xmx2048m -Xms256m"
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
