## Builder stage
#FROM maven:3-openjdk-17-slim AS builder
#WORKDIR /application
#COPY . .
#RUN mvn clean package -DskipTests
#
## Final stage
#FROM openjdk:17-jdk-slim
#WORKDIR /application
#COPY --from=builder /application/target/*.jar /application/app.jar
#ENTRYPOINT ["java", "-jar", "/application/app.jar"]
#EXPOSE 8000


# Builder stage
FROM openjdk:17-jdk-slim AS builder
WORKDIR /application
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract


# Final stage
FROM openjdk:17-jdk-slim
WORKDIR /application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
EXPOSE 8080
