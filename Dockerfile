FROM maven:3.9.4-amazoncorretto-20-al2023 as builder
COPY pom.xml /app/
COPY src /app/src/
COPY .git/ ./.git/
RUN mvn -f /app/pom.xml package -DskipTests

FROM openjdk:20-jdk-slim AS runner
COPY --from=builder /app/target/altisima-0.0.1-SNAPSHOT.jar ./
ENTRYPOINT ["java", "-jar", "altisima-0.0.1-SNAPSHOT.jar"]
