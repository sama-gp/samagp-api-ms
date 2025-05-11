#FROM maven:3.8.5-openjdk-17 AS builder
#WORKDIR /app
#COPY . .
#RUN mvn clean package -DskipTests
#RUN ls -al /app
#
#FROM openjdk:17-jdk-slim
#WORKDIR /app
#COPY --from=builder /app/target/samagp-api-ms-*.jar samagp-api-ms.jar
#EXPOSE 8080
#CMD ["java", "-jar", "samagp-api-ms.jar"]

# Étape 1 : Construire l'application avec Maven
#FROM maven:3.9.9-eclipse-temurin-21 AS build
#WORKDIR /app
#COPY pom.xml .
#COPY src ./src
#RUN mvn clean package -DskipTests
#
## Étape 2 : Exécution
#FROM eclipse-temurin:21
## Création de l'utilisateur et groupe non-root
#RUN groupadd -r samagp && useradd -r -g samagp samagp
#WORKDIR /app
## Copie du JAR avec permissions appropriées
#COPY --from=build --chown=samagp:samagp /app/target/*.jar samagp-api-ms.jar
#
## Création du dossier logs avec les bonnes permissions
#RUN mkdir -p /app/logs && chown samagp:samagp /app/logs
#
## Vérification et préparation
#RUN chmod +x samagp-api-ms.jar && \
#    ls -la /app && \
#    pwd
#
## Configuration de l'environnement
#ENV SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/samagp
#ENV SPRING_DATASOURCE_USERNAME=samagp
#ENV SPRING_DATASOURCE_PASSWORD=samagp
## Port exposé
#EXPOSE 8080
#
#USER samagp:samagp
#ENTRYPOINT ["java", "-jar", "samagp-api-ms.jar"]

#
## Étape 1 : Construction
#FROM eclipse-temurin:17-jdk-jammy AS build
#
#WORKDIR /app
#COPY . .
#RUN apt-get update && apt-get install -y maven \
#    && mvn clean package -DskipTests \
#    && apt-get remove -y maven \
#    && apt-get autoremove -y \
#    && rm -rf /var/lib/apt/lists/*
#
## Étape 2 : Exécution
#FROM eclipse-temurin:17-jre-jammy
#
#RUN groupadd -r samagp && useradd -r -g samagp samagp
#
#WORKDIR /app
#COPY --from=build /app/target/samagp-api-ms-0.0.1-SNAPSHOT.jar ./samagp-api-ms.jar
#
#RUN mkdir -p /app/logs \
#    && chown -R samagp:samagp /app \
#    && chmod +x samagp-api-ms.jar
#
#ENV SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/samagp
#ENV SPRING_DATASOURCE_USERNAME=samagp
#ENV SPRING_DATASOURCE_PASSWORD=samagp
#
#EXPOSE 8080
#USER samagp
#
#HEALTHCHECK --interval=30s --timeout=3s \
#    CMD curl -f http://localhost:8080/actuator/health || exit 1
#WORKDIR /app
#
#CMD ["java", "-jar", "samagp-api-ms.jar"]

# Stage 1: Build the JAR file
FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run the JAR file
FROM openjdk:17-jdk-slim
# Création d'un utilisateur non-root
RUN useradd -m samagp
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
RUN chown -R samagp:samagp /app
USER samagp
# Variables d'environnement
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/samagp
ENV SPRING_DATASOURCE_USERNAME=postgres
ENV SPRING_DATASOURCE_PASSWORD=postgres

EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]