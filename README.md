# Application e-Bank

L'application e-Bank est une application bancaire basée sur une architecture monolithique. Elle offre des fonctionnalités de gestion des clients, des comptes bancaires et des opérations (crédit, débit, transfert). Elle est développée en utilisant Java 17, Spring Boot et Maven.

## Module : Gestion des clients
Service de gestion des clients : Ce microservice est responsable de la gestion des informations relatives aux clients. Il permet de créer, mettre à jour, récupérer et supprimer des données de clients.

## Module : Gestion des comptes
Service de gestion des comptes bancaires : Ce microservice gère les comptes bancaires des clients. Il permet de créer de nouveaux comptes, de consulter les soldes, d'effectuer des dépôts et des retraits. 

## Module : Gestion des opérations
Service de gestion des opérations : Ce microservice gère les différentes opérations bancaires, telles que les crédits, les débits et les transferts entre comptes.

![Diagramme de classe](https://github.com/BrodyGaudel/E-BANK-BACKEND/assets/57298219/97ebe354-a0f9-4e73-b16c-5e99d9ec519a)


## FRONTEND
La partie frontend développé en avec ANGULAR est disponible ici : https://github.com/BrodyGaudel/E-BANK-FRONTEND

## Prérequis
Avant de démarrer l'application e-Bank, assurez-vous d'avoir les éléments suivants installés :
1. Java 17
2. Maven
3. MySQL

## Configuration
1. cloner le projet
2. allez sous le projet pour modifier le fichier application.properties pour qu'il corresponde à votre configuration de MySQL
3. Sous chaque projet, executer la commande **mvn clean install**
4. Démarer l'application avec la commande **mvn spring-boot:run**
5. Visulaser l'API REST avec swagger ui via le lien http://localhost:8888/e-bank/api/swagger-ui.
   
## Contribution
Les contributions à cette application sont les bienvenues. Si vous souhaitez contribuer, veuillez suivre les étapes suivantes :

1. Fork ce référentiel.
2. Créez une branche pour votre fonctionnalité ou votre correctif.
3. Effectuez les modifications nécessaires.
4. Soumettez une demande d'extraction. Nous apprécions vos commentaires et vos suggestions pour améliorer cette application.

## Auteur
Brody Gaudel MOUNANGA BOUKA

N'hésitez pas à me contacter si vous avez des questions ou des commentaires sur cette application e-Bank.

