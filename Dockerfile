# Use a imagem oficial do OpenJDK 17 como base
FROM openjdk:17 as builder

# Defina o diretório de trabalho
WORKDIR /app

# Copie o Gradle Wrapper e os arquivos relacionados com o build
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

COPY .gradle .gradle

RUN chmod +x ./gradlew && ./gradlew build -x test

FROM openjdk:17-slim

WORKDIR /app

COPY --from=builder /app/build/libs/itau-gt8-challenge-0.0.1-SNAPSHOT.jar itau-challenge.jar

# ENV SPRING_PROFILES_ACTIVE=prd

ENTRYPOINT ["java", "-jar", "itau-challenge.jar"]
