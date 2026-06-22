# Build Stage
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
#COPY pom.xml .
#COPY src ./src
#RUN mvn clean package -DskipTests

# Build common module first
COPY hospital-common-module ./hospital-common-module
RUN cd hospital-common-module && mvn clean install -DskipTests

# Build doctor service
COPY appointment-service ./appointment-service
RUN cd appointment-service && mvn clean package -DskipTests

#runttime stage
FROM amazoncorretto:21-alpine-jdk
WORKDIR /app
COPY --from=build /app/appointment-service/target/*.jar app.jar
LABEL authors="akhil"

EXPOSE 8083
ENTRYPOINT ["java","-jar","app.jar"]