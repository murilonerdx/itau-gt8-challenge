# Stage 1: Build the Java application
FROM openjdk:17 as build

WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

RUN chmod +x ./gradlew

# Use the "install" task to build the application and dependencies (excluding tests)
RUN ./gradlew install -x test

# Stage 2: Create the final image
FROM openjdk:17-slim

# Copy the built JAR file from the previous stage
COPY --from=build /app/build/libs/itau-gt8-challenge-0.0.1-SNAPSHOT.jar /itau-challenge.jar

ENTRYPOINT ["java","-jar","/itau-challenge.jar"]
