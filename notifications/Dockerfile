FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn clean install -DskipTests

# Stage 2: Run the built JAR
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY --from=builder /app/target/notification-service-*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
