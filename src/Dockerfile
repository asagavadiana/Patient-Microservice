FROM gradle:6.9.1-jdk11 AS builder

WORKDIR /app

COPY . .

RUN ./gradlew build --no-daemon

RUN ls -la /app/build/libs/

FROM adoptopenjdk/openjdk11:jre-11.0.12_7-alpine

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

CMD ["java", "-jar", "app.jar"]
