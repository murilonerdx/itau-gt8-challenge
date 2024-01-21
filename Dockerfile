# Use a imagem oficial do OpenJDK 17 como base
FROM openjdk:17-jdk

# Defina o diretório de trabalho
WORKDIR /app

# Copie o arquivo JAR do seu aplicativo Spring Boot para o contêiner
COPY build/libs/itau-gt8-challenge-0.0.1-SNAPSHOT.jar itau-challenge.jar

# Comando de entrada para iniciar o aplicativo Spring Boot
CMD ["java", "-jar", "itau-challenge.jar"]
