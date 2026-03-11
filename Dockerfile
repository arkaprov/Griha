# Stage 1: Build the application using our local Maven installation
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

# Copy the local maven wrapper and files we created earlier
COPY .maven ./.maven
COPY src ./src
COPY pom.xml ./

# Set execute permissions on the maven binary and build the application
RUN chmod +x .maven/apache-maven-3.9.6/bin/mvn
RUN .maven/apache-maven-3.9.6/bin/mvn clean package -DskipTests

# Stage 2: Create the runtime image
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy the generated jar file from the build stage
COPY --from=build /app/target/griha-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
