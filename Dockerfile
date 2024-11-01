FROM openjdk:17-oracle

WORKDIR /app

COPY target/wallet-0.0.1-SNAPSHOT.jar app.jar

ENV SERVER_HOST=localhost
ENV SERVER_PORT=8080
ENV DB_HOST=localhost
ENV DB_PORT=5432


CMD ["java", "-jar", "app.jar"]
