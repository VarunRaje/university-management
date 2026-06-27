FROM eclipse-temurin:25-jre-alpine

WORKDIR /opt/university-management

RUN addgroup -S university && adduser -S university -G university

COPY university-mgmt-service/build/libs/university-mgmt-service-*.jar app.jar

USER university

EXPOSE 8080

ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75", "-jar", "app.jar"]
