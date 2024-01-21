FROM openjdk:17 as build

WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

RUN chmod +x ./gradlew

# Instalar findutils
RUN apt-get update && apt-get install -y findutils

RUN ./gradlew build -x test

ENV SPRING_PROFILES_ACTIVE=prd

FROM openjdk:17-slim
COPY --from=build /build/libs/itau-gt8-challenge-0.0.1-SNAPSHOT itau-challenge.jar

ENTRYPOINT ["java","-jar","/itau-challenge.jar"]
