# Identity Reconciliation

---
<!-- TOC -->
* [Identity Reconciliation](#identity-reconciliation)
  * [Overview](#overview)
  * [Tech Stack](#tech-stack)
  * [API Documentation](#api-documentation)
    * [Identify Customer](#identify-customer)
      * [Endpoint:](#endpoint)
      * [Request Body (JSON)](#request-body-json)
      * [Response (200 OK)](#response-200-ok)
  * [Expected Scenarios & Corresponding Behaviour](#expected-scenarios--corresponding-behaviour)
  * [Setup & Installation](#setup--installation)
<!-- TOC -->

---
## Overview

This is a Spring Boot based API to identify and keep track of a customer's identity across multiple purchases.

---
## Tech Stack
- **Backend:** Java (Spring Boot)
- **Database:** SQL-based in-build database (H2 database)
- **API Format:** REST (JSON)
- **Build Tool:** Maven

---

## API Documentation

### Identify Customer
#### Endpoint:
`POST /api/identify`

#### Request Body (JSON)
```json
{
    "email": "mcfly@hillvalley.edu",
    "phoneNumber": "123456"
}
```

#### Response (200 OK)
```json
{
    "contact": {
        "primaryContactId": 1,
        "emails": ["lorraine@hillvalley.edu", "mcfly@hillvalley.edu"],
        "phoneNumbers": ["123456"],
        "secondaryContactIds": [23]
    }
}
```
---
## Expected Scenarios & Corresponding Behaviour
- If no matching contacts exist, a new primary contact is created.
- If a match is found, it links contacts together, marking new ones as `secondary`.
- If an existing primary contact is found, it remains primary.

---
## Setup & Installation

1. Clone the Repository

```bash 
git clone https://github.com/your-username/bitespeed-backend-task.git
cd bitespeed-backend-task
```

2. Configure Database

Update application.properties with your database credentials:
```text
spring.datasource.url=jdbc:mysql://localhost:3306/bitespeed
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```
3. Build & Run the Project


```bash
mvn clean install
mvn spring-boot:run
```

The API will be available at `http://localhost:8080/api/identify`

--- 

## Examples

Request:
```json
{
    "email": "mcfly@hillvalley.edu",
    "phoneNumber": "123456"
}
```
will give the following response
```json
{
    "contact":{
        "primaryContatctId": 1,
        "emails": ["lorraine@hillvalley.edu","mcfly@hillvalley.edu"],
        "phoneNumbers": ["123456"],
        "secondaryContactIds": [23]
    }
}
```

Detailed Description : [Bitespeed Backend Task](https://drive.google.com/file/d/1m57CORq21t0T4EObYu2NqSWBVIP4uwxO/view) 

📩 Contact

For any queries, reach out at friskycouder@gmail.com
