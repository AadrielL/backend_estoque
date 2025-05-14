# Stage 1: Build com Maven 3.9.9 e JDK 21
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copia os arquivos do projeto para dentro do container
COPY pom.xml .
COPY src ./src

# Compila o projeto e gera o jar, pulando testes para acelerar
RUN mvn clean package -DskipTests

# Stage 2: Runtime com JRE 21 Alpine
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copia o jar gerado do stage anterior
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]