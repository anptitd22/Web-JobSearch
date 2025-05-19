# Build stage - Sử dụng JDK 21 để build
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy và cache dependencies trước
COPY Back-end/Back-end/pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code và build
COPY Back-end/Back-end/src ./src
RUN mvn clean package -DskipTests -Dmaven.test.skip=true -Dmaven.compile.fork=true

# Run stage - Sử dụng JRE 21 để chạy
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copy file JAR từ stage build
COPY --from=build /app/target/webIT-0.0.1-SNAPSHOT.jar app.jar

# Thiết lập các tham số JVM
ENV JAVA_OPTS="-Xms512m -Xmx1024m -XX:+UseContainerSupport -Dfile.encoding=UTF-8"
EXPOSE 8080

# Security best practice - chạy bằng non-root user
RUN addgroup --system javauser && adduser --system --ingroup javauser javauser
USER javauser

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
