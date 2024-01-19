FROM openjdk:11
EXPOSE 8080
ADD target/qrcodegenerator-0.0.1-SNAPSHOT.jar qrcodegenerator.jar
ENTRYPOINT ["java", "-jar", "/qrcodegenerator.jar"]