# Stage 1: Build the application using Maven
FROM maven:3.8.1-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create the final lean runtime image
FROM openjdk:17-jre-slim
WORKDIR /app
# Copy the JAR file from the 'build' stage
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
