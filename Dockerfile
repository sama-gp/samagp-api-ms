from openjdk:17-jdk-slim
workdir /app
copy target/*.jar sama-gp-annonce-ms.jar
expose 8080
entrypoint ["java", "-jar", "sama-gp-annonce-ms.jar"]