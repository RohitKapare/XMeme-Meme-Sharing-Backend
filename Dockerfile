# Stage 1: Build the application
FROM gradle:7.4.2-jdk11-alpine AS build
WORKDIR /app
COPY build.gradle settings.gradle /app/
COPY config /app/config
COPY src /app/src
RUN gradle clean build -x test -x checkstyleMain -x checkstyleTest

# Stage 2: Create the final image
FROM eclipse-temurin:11-jre-alpine
WORKDIR /app
EXPOSE 8081
COPY --from=build /tmp/external_build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
