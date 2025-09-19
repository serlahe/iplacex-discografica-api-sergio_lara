# Paso 1: Compilacion con Gradle
FROM gradle:8.10.2-jdk22 AS builder
WORKDIR /app
COPY . .
RUN gradle clean build -x test

# Paso 2: Ejecucion con OpenJDK
FROM openjdk:22-slim
WORKDIR /app
COPY --from=builder /app/build/libs/discografia-1.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
