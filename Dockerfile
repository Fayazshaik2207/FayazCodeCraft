# Use Java 17 JDK
FROM openjdk:17-jdk-slim

# Set working directory inside the container
WORKDIR /app

# Copy jar file from target folder
COPY target/*.jar app.jar

# Expose the port (Render uses $PORT, so we map it dynamically later)
ENV PORT 8080
EXPOSE $PORT

# Run the Spring Boot app
ENTRYPOINT ["java","-jar","/app/app.jar"]
