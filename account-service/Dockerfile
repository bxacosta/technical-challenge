FROM eclipse-temurin:21-jdk-alpine AS compiler
WORKDIR /app
COPY . .
RUN sh gradlew bootJar

FROM eclipse-temurin:21-jre-alpine
COPY --from=compiler /app/build/libs/*.jar /home/app.jar
EXPOSE 8090
CMD ["java", "-server", "-Duser.timezone=UTC", "-jar", "/home/app.jar"]
