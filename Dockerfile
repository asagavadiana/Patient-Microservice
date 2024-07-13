FROM gradle:7.3.3-jdk17 AS builder

WORKDIR /app

RUN echo "Checking the contents of /app before copying files" && ls -la /app

COPY . .

RUN echo "Checking the contents of /app after copying files" && ls -la /app

RUN echo "Setting execution permissions for gradlew" && chmod +x gradlew

RUN ./gradlew build --no-daemon -x test

RUN ls -la /app/build/libs/

FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

CMD ["java", "-jar", "app.jar"]
