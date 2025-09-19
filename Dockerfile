# Paso 1: Compilación con Gradle
FROM gradle:jdk21 AS builder
WORKDIR /app

COPY ./build.gradle .
COPY ./settings.gradle .
COPY ./src ./src

RUN gradle build --no-daemon

# Paso 2: Ejecución con OpenJDK
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=builder /app/build/libs/discografia-1.jar discografia-1.jar
EXPOSE 443
ENTRYPOINT ["java", "-jar", "discografia-1.jar"]
