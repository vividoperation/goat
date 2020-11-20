# our base build image
FROM maven:3.6-openjdk-8 as maven

WORKDIR /app

# copy the pom file
COPY ./pom.xml ./pom.xml
COPY ./goat-common/pom.xml ./goat-common/pom.xml
COPY ./goat-dao/pom.xml ./goat-dao/pom.xml
COPY ./goat-facade/pom.xml ./goat-facade/pom.xml
COPY ./goat-service/pom.xml ./goat-service/pom.xml
COPY ./goat-web/pom.xml ./goat-web/pom.xml

# fetch all dependencies
RUN mvn dependency:go-offline -B

# copy other files
COPY ./ ./

# build for release
RUN mvn package && cp goat-web/target/goat-web-*.jar app.jar

# smaller, final base image
FROM openjdk:8-jre-alpine

# set deployment directory
WORKDIR /app

# copy over the built artifact from the maven image
COPY --from=maven /app/app.jar ./app.jar

# Expose application port
EXPOSE 8080

# set the startup command to run your binary
CMD ["java", "-jar", "/app/app.jar"]
