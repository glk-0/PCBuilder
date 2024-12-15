# uOttawa - 2024-2025 - SEG2505A - Project - Group 39

Project Name: **PCBuilder**

## Project Members

| First Name  | LAST NAME           | GitHub Username  |
|-------------|---------------------|------------------|
| Ghali       | Kinany Alaoui       | glk-0            |
| Soukaina    | Bennasser           | soukainabenn     |
| Salma       | Ayouch              | slimouu          |

## Introduction

PCBuilder is an application designed to manage the components and requests necessary for assembling custom PCs. It allows users to access various features based on their role (Administrator, StoreKeeper, Assembler, and Requester) and includes inventory management and authentication functionalities.

## Clarifications on Requirements

### Explicit Requirements Reformulated
1. **User Management**: The Administrator can add, delete, and modify users (Requesters).
2. **Inventory Management**: The StoreKeeper can add, modify, delete, and view stock components with mandatory attributes.
3. **Authentication**: All users must authenticate to access their respective features.
4. **Database Reset**: The Administrator has several options to reset or clear the database.

### Proposed Implicit Requirements
- Password security, email validation, and error management for each user action.

### Assumptions
- Authentication uses unique email identifiers for each user.
- The database is initialized upon the first launch of the application.
- Orders are set to "pending" by default.

## Modeling

### Class Diagram
Here is the UML diagram for the project:

![image](https://github.com/user-attachments/assets/48199a72-58a9-4938-a778-dacbbbdce6c2)

### Activity Diagram
<img width="1101" alt="Screenshot 2024-11-30 at 5 41 14 PM" src="https://github.com/user-attachments/assets/6f78aca7-02a6-49dd-84d7-48da1a99160f">

### Sequence Diagram
Here is the sequence diagram for the project:
![Image 2024-12-01 at 11 07 AM (1)](https://github.com/user-attachments/assets/6e5420f2-85c3-4f6e-8db9-727c6ad319a4)

### State Diagram:
![etat](https://github.com/user-attachments/assets/a5fbe59c-5ef9-4f9d-8301-4265bf066836)

## Unit Test Elements

Unit tests to validate key functionalities:
- **Authentication**: Login tests for each role.
- **User Management**: Add, delete, and modify Requester accounts.
- **Inventory Management**: Create, modify, and delete components.

### Test Values:
## Users

| Role           | Login ID                        | Password   |
|----------------|----------------------------------|------------|
| Administrator | admin@uottawa.ca                 | 123        |
| StoreKeeper    | storeKeeper@uottawa.ca           | 123        |
| Assembler      | assembler@uottawa.ca             | 123        |

## Default Components
| **Type**    | **Sub-type**         | **Title**                | **Quantity** | **Description**                                  |
|-------------|----------------------|--------------------------|--------------|--------------------------------------------------|
| Hardware    | PC case              | Case1                    | 0            | High-quality gaming case with RGB lighting      |
| Hardware    | PC case              | Case2                    | 10           | High-quality gaming case with RGB lighting      |
| Hardware    | PC case              | Case3                    | 10           | High-quality gaming case with RGB lighting      |
| Hardware    | Motherboard          | Motherboard1             | 10           | High-quality motherboard                        |
| Hardware    | Motherboard          | Motherboard2             | 10           | High-quality motherboard                        |
| Hardware    | Motherboard          | Motherboard3             | 10           | High-quality motherboard                        |
| Hardware    | RAM                  | 16GB                     | 30           | 16GB DDR4 RAM for high performance              |
| Hardware    | RAM                  | 8GB                      | 30           | 8GB DDR4 RAM for high performance               |
| Hardware    | RAM                  | 4GB                      | 30           | 4GB DDR4 RAM for high performance               |
| Hardware    | Storage              | SSD 1TB                  | 20           | High-speed 1TB SSD for fast data access         |
| Hardware    | Storage              | SSD 512GB                | 20           | High-speed 512GB SSD for fast data access       |
| Hardware    | Storage              | SSD 256GB                | 20           | High-speed 256GB SSD for fast data access       |
| Hardware    | Monitor              | 8K Monitor               | 10           | 27-inch 8K UHD monitor with HDR support         |
| Hardware    | Monitor              | 4K Monitor               | 10           | 24-inch 4K UHD monitor with HDR support         |
| Hardware    | Monitor              | 2K Monitor               | 10           | 24-inch 2K UHD monitor with HDR support         |
| Hardware    | Input Device         | Wireless Keyboard and Mouse1 | 10        | Wireless keyboard and mouse combo               |
| Hardware    | Input Device         | Wireless Keyboard and Mouse2 | 10        | Wireless keyboard and mouse combo               |
| Hardware    | Input Device         | Wireless Keyboard and Mouse3 | 10        | Wireless keyboard and mouse combo               |
| Software    | Web Browser          | Firefox                  | 100          | Firefox browser for secure and fast browsing    |
| Software    | Web Browser          | Chrome                   | 100          | Chrome browser for secure and fast browsing     |
| Software    | Web Browser          | Microsoft Edge           | 100          | Microsoft Edge browser for secure and fast browsing |
| Software    | Office               | LibreOffice              | 20           | Free and open-source office suite for document editing |
| Software    | Office               | OfficePro                | 20           | Pro version office suite for document editing  |
| Software    | IDE                  | Visual Studio Code       | 30           | Lightweight IDE with extensive plugin support  |
| Software    | IDE                  | IntelliJ                 | 30           | Java IDE with advanced features                 |
| Software    | IDE                  | Android Studio           | 30           | Android application editor                      |

## Default Orders
| **Requester**        | **PC Case** | **Motherboard** | **RAM** | **Storage**                        | **Monitor**      | **Input Device**                 | **Web Browser** | **Office Suite** | **IDE**                                   | **Order ID** | **Status**              | **Description**            |
|----------------------|-------------|-----------------|---------|------------------------------------|------------------|----------------------------------|-----------------|-------------------|-------------------------------------------|-------------|-------------------------|----------------------------|
| requester1@uottawa.ca | Case1       | Motherboard1    | 16GB (2) | [SSD 1TB (1); SSD 512GB (1)]      | 8K Monitor (1)   | Wireless Keyboard and Mouse1 (1) | Firefox (1)      | LibreOffice (1)   | [Visual Studio Code (1); IntelliJ (1); Android Studio (1)] | 1           | Waiting to be accepted | New order for Requester1   |
| requester2@uottawa.ca | Case2       | Motherboard2    | 8GB (2)  | [SSD 256GB (1); SSD 512GB (1)]    | 4K Monitor (1)   | Wireless Keyboard and Mouse2 (1) | Chrome (1)       | OfficePro (1)     | [Android Studio (1); IntelliJ (1); Android Studio (1)]   | 2           | Waiting to be accepted | New order for Requester2   |

## Default Users

| ID | First Name | Last Name | Email                        | Password  | Role      |
|----|------------|-----------|------------------------------|-----------|-----------|
| 1  | Requester  | 1         | requester1@uottawa.ca         | 123       | Requester |
| 2  | Requester  | 2         | requester2@uottawa.ca         | 123       | Requester |

## Data Files

The data files **components.csv**, **requesters.csv**, and **orders.csv** are located in the following directory:

**`app/src/main/assets`**

## Limitations and Known Issues

- Default data is loaded upon the first launch of the application. If not, reset the database as an Administrator using the default files.
