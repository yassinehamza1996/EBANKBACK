# Définition de l'image de base
FROM openjdk:17-ea-10-jdk-alpine

# Définition du maiteneur de l'image
LABEL maintainer="Brody Gaudel https://github.com/BrodyGaudel"

# Définition des variables d'environnement
ENV MYSQL_USER=root
ENV MYSQL_PWD=brody2250
ENV MYSQL_HOST=localhost
ENV MYSQL_PORT=3306

# Copie de votre application jar dans le conteneur
COPY target/e-bank-0.0.1.jar e-bank-0.0.1.jar

# Commande pour lancer l'application lors de l'exécution du conteneur
ENTRYPOINT ["java","-jar","e-bank-0.0.1.jar"]
