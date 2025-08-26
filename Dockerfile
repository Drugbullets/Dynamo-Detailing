
# Stage 1: Build the JAR with Maven
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /src
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run the JAR with Java
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /src/target/*.jar app.jar
ENV PORT=8080
EXPOSE 8080
CMD ["java","-jar","/app/app.jar"]
