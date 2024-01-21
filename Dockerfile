# Use a imagem do OpenJDK 17 como base
FROM openjdk:17 as builder

# Configurar o diretório de trabalho
WORKDIR /app

# Copiar o Gradle Wrapper e os arquivos relacionados com o build
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Copiar o código-fonte
COPY src src

# Configurar permissões e construir o projeto (excluindo os testes)
RUN chmod +x ./gradlew && ./gradlew build

# Stage final: criar a imagem mínima do OpenJDK 17 e copiar o arquivo JAR construído
FROM openjdk:17-slim

# Copiar o arquivo JAR construído a partir do estágio anterior
COPY --from=builder /app/build/libs/itau-gt8-challenge-0.0.1-SNAPSHOT.jar /app/itau-challenge.jar

# Definir a variável de ambiente para o perfil Spring Boot (se necessário)
# ENV SPRING_PROFILES_ACTIVE=prd

# Comando de entrada para iniciar o aplicativo Spring Boot
ENTRYPOINT ["java", "-jar", "/app/itau-challenge.jar"]
