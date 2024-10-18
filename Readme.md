# Crispy Read Core

## Project Overview

This is a Spring Boot application and contains REST endpoints.

## Technologies Used

- Java 17+
- Spring Boot 3
- Spring Data JPA
- Spring Security (Basic Auth)
- PostgreSQL
- Maven Wrapper CLI
- OpenApi using Redocly

## Prerequisites

Before you begin, ensure you have met the following requirements:

- Java JDK 17 or higher installed
- Git installed

## Getting Started

#### 1. Clone the repository

```bash
git clone https://github.com/shailendra98k/crispy-read-core.git
```

#### 2. Create a .env file in root directory and add correct value

```
DB=************************
DB_USER=************************
DB_USER_PASSWORD=************************
USER=************************
USER_PASSWORD=************************
AUTH_TOKEN_COOKIE=************************
```

#### 3. Build the spring-boot project (Skip the unit test for now)

```bash
./mvnw clean compile install -Dmaven.test.skip=true
```

#### 3. Run the application on default PORT 8080

```bash
java -jar target/crispyread-core-0.0.1-SNAPSHOT.jar
```

## Build OpenApi Specs

#### 1. Install redocly npm package 

```bash
npm install
```

#### 2. Build openapi yaml and bundle into index.html

```bash
npm run build
```

#### 3. Run openapi spec on /api/index.html 
```
curl --location --request GET 'https://crispyread.com/api/index.html'
```
