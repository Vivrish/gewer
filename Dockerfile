FROM gradle:8.5-jdk17 AS build
WORKDIR /app
COPY . .
RUN gradle dependencies
RUN gradle shadowJar --no-daemon

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
