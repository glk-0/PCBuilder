# uOttawa - 2024-2025 - SEG2505A - Projet - Groupe 39

Nom du projet : **PCBuilder**

## Membres du projet

| Prénom      | NOM               | Identifiant GitHub |
|-------------|-------------------|--------------------|
| Ghali       | Kinany Alaoui    | glk-0              |
| Soukaina  | Bennasser         | soukainabenn       |
| Salma     | Ayouch            | slimouu            |

## Introduction

PCBuilder est une application conçue pour gérer les composants et les demandes nécessaires à l'assemblage de PC personnalisés. Elle permet aux utilisateurs d’accéder à diverses fonctionnalités en fonction de leur rôle (Administrateur, StoreKeeper, Assembler, et Requester) et inclut des fonctions de gestion de stock et d'authentification.

## Clarifications sur les exigences

### Exigences explicites reformulées
1. **Gestion des utilisateurs** : L’Administrateur peut ajouter, supprimer et modifier les utilisateurs (Requesters).
2. **Gestion de stock** : Le StoreKeeper peut ajouter, modifier, supprimer, et visualiser les composants du stock avec des attributs obligatoires.
3. **Authentification** : Tous les utilisateurs doivent s'authentifier pour accéder à leurs fonctionnalités respectives.
4. **Réinitialisation de la base de données** : L’Administrateur dispose de plusieurs options pour réinitialiser ou vider la base de données.

### Exigences implicites proposées
- Sécurité des mots de passe, validation d'email, gestion d'erreurs pour chaque action utilisateur.

### Hypothèses
- L’authentification utilise des identifiants uniques par e-mail pour chaque utilisateur.
- La base de données est initialisée lors du premier lancement de l’application.
- Les commandes sont à l'état en attente par défaut 

## Modélisation

### Diagramme de classe
Voici le diagramme UML pour le projet :

![image](https://github.com/user-attachments/assets/48199a72-58a9-4938-a778-dacbbbdce6c2)

### Diagramme d'activité
<img width="1101" alt="Screenshot 2024-11-30 at 5 41 14 PM" src="https://github.com/user-attachments/assets/6f78aca7-02a6-49dd-84d7-48da1a99160f">


### Diagramme de séquence
Voici le diagramme de séquence pour le projet :
![Image 2024-12-01 at 11 07 AM (1)](https://github.com/user-attachments/assets/6e5420f2-85c3-4f6e-8db9-727c6ad319a4)

### Diagramme d'etat:
![etat](https://github.com/user-attachments/assets/a5fbe59c-5ef9-4f9d-8301-4265bf066836)


## Eléments de tests unitaires

Tests unitaires pour valider les fonctionnalités principales :
- **Authentification** : Tests de connexion pour chaque rôle.
- **Gestion des utilisateurs** : Ajout, suppression, et modification de comptes Requester.
- **Gestion du stock** : Création, modification, et suppression de composants.


### Valeurs de test:
## Utilisateurs

| Rôle           | Identifiant de connexion         | Mot de passe       |
|----------------|----------------------------------|---------------------|
| Administrateur | admin@uottawa.ca                | 123        | 
| StoreKeeper    | storeKeeper@uottawa.ca          | 123      |
| Assembler      | assembler@uottawa.ca            | 123        |


## Composants par défaut
| **Type**  | **Sous-type**          | **Titre**                 | **Quantité** | **Description**                                |
|-----------|------------------------|---------------------------|--------------|------------------------------------------------|
| Hardware  | PC case                | Case1                     | 0            | High-quality gaming case with RGB lighting    |
| Hardware  | PC case                | Case2                     | 10           | High-quality gaming case with RGB lighting    |
| Hardware  | PC case                | Case3                     | 10           | High-quality gaming case with RGB lighting    |
| Hardware  | Motherboard            | Motherboard1              | 10           | High-quality motherboard                      |
| Hardware  | Motherboard            | Motherboard2              | 10           | High-quality motherboard                      |
| Hardware  | Motherboard            | Motherboard3              | 10           | High-quality motherboard                      |
| Hardware  | RAM                    | 16GB                      | 30           | 16GB DDR4 RAM for high performance            |
| Hardware  | RAM                    | 8GB                       | 30           | 8GB DDR4 RAM for high performance             |
| Hardware  | RAM                    | 4GB                       | 30           | 4GB DDR4 RAM for high performance             |
| Hardware  | Storage                | SSD 1TB                   | 20           | High-speed 1TB SSD for fast data access       |
| Hardware  | Storage                | SSD 512GB                 | 20           | High-speed 512GB SSD for fast data access     |
| Hardware  | Storage                | SSD 256GB                 | 20           | High-speed 256GB SSD for fast data access     |
| Hardware  | Monitor                | 8K Monitor                | 10           | 27-inch 8K UHD monitor with HDR support       |
| Hardware  | Monitor                | 4K Monitor                | 10           | 24-inch 4K UHD monitor with HDR support       |
| Hardware  | Monitor                | 2K Monitor                | 10           | 24-inch 2K UHD monitor with HDR support       |
| Hardware  | Input Device           | Wireless Keyboard and Mouse1 | 10        | Wireless keyboard and mouse combo             |
| Hardware  | Input Device           | Wireless Keyboard and Mouse2 | 10        | Wireless keyboard and mouse combo             |
| Hardware  | Input Device           | Wireless Keyboard and Mouse3 | 10        | Wireless keyboard and mouse combo             |
| Software  | Web Browser            | Firefox                   | 100          | Firefox browser for secure and fast browsing  |
| Software  | Web Browser            | Chrome                    | 100          | Chrome browser for secure and fast browsing   |
| Software  | Web Browser            | Microsoft Edge            | 100          | Microsoft Edge browser for secure and fast browsing |
| Software  | Office                 | LibreOffice               | 20           | Free and open-source office suite for document editing |
| Software  | Office                 | OfficePro                 | 20           | Pro version office suite for document editing |
| Software  | IDE                    | Visual Studio Code        | 30           | Lightweight IDE with extensive plugin support |
| Software  | IDE                    | IntelliJ                  | 30           | Java IDE with advanced features               |
| Software  | IDE                    | Android Studio            | 30           | Android application editor                    |

## Commandes par défaut
| **Requester**         | **PC Case** | **Motherboard** | **RAM** | **Storage**                        | **Monitor**     | **Input Device**                | **Web Browser** | **Office Suite** | **IDE**                                    | **Order ID** | **Status**              | **Description**            |
|------------------------|-------------|-----------------|---------|------------------------------------|-----------------|----------------------------------|-----------------|------------------|-------------------------------------------|-------------|-------------------------|----------------------------|
| requester1@uottawa.ca  | Case1       | Motherboard1    | 16GB (2) | [SSD 1TB (1); SSD 512GB (1)]      | 8K Monitor (1)  | Wireless Keyboard and Mouse1 (1) | Firefox (1)     | LibreOffice (1)  | [Visual Studio Code (1); IntelliJ (1); Android Studio (1)] | 1           | Waiting to be accepted | New order for Requester1   |
| requester2@uottawa.ca  | Case2       | Motherboard2    | 8GB (2)  | [SSD 256GB (1); SSD 512GB (1)]    | 4K Monitor (1)  | Wireless Keyboard and Mouse2 (1) | Chrome (1)      | OfficePro (1)    | [Android Studio (1); IntelliJ (1); Android Studio (1)]   | 2           | Waiting to be accepted | New order for Requester2   |


## Utilisateurs par défaut

| ID | Prénom  | Nom     | Email                          | Mot de passe | Rôle      |
|----|---------|---------|--------------------------------|--------------|-----------|
| 1  | Requester | 1   | requester1@uottawa.ca     | 123   | Requester |
| 2  | Requester    | 2  | requester2@uottawa.ca        | 123      | Requester |


## Fichiers de données 

Le fichier de données **components.csv**, **requesters.csv**, et **orders.csv** se trouve dans le répertoire suivant : 

**`app/src/main/assets`**
  
## Limites et problèmes connus

- les données par défauts sont chargées au premier lancement de l'application, si ce n'est pas le cas, reset Database en tant qu'administrateur avec les fichiers par défaut.

  


