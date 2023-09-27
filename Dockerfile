FROM khipu/openjdk17-alpine:latest
EXPOSE 9999
WORKDIR /opt/app
ADD target/Notifications-0.0.1-SNAPSHOT.jar ./app.jar
CMD ["java", "-jar", "app.jar"]