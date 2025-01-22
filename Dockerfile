# Use an official OpenJDK runtime as a parent image
FROM openjdk:23-jdk AS build

# Set the working directory in the container
WORKDIR /app

# Copy the application JAR file to the container
COPY target/appointment-management-0.0.1-SNAPSHOT.jar /app/appointment-management-0.0.1-SNAPSHOT.jar

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app/appointment-management-0.0.1-SNAPSHOT.jar"]