# Documentation: `Postgres.java`

## Overview
The `Postgres` class is a Java program designed to interact with a PostgreSQL database. It provides functionality for database connection, schema setup, data insertion, and hashing passwords using the MD5 algorithm. The program includes methods for initializing the database schema, inserting user and comment data, and generating MD5 hashes for secure password storage.

---

## Features
### 1. **Database Connection**
   - Establishes a connection to a PostgreSQL database using environment variables:
     - `PGHOST`: Hostname of the PostgreSQL server.
     - `PGDATABASE`: Name of the database.
     - `PGUSER`: Username for authentication.
     - `PGPASSWORD`: Password for authentication.
   - Uses `DriverManager` to create the connection.

### 2. **Database Schema Setup**
   - Creates two tables if they do not already exist:
     - **`users`**:
       | Column Name | Data Type | Constraints |
       |-------------|-----------|-------------|
       | `userid`    | `VARCHAR(36)` | Primary Key |
       | `username`  | `VARCHAR(50)` | Unique, Not Null |
       | `password`  | `VARCHAR(50)` | Not Null |
       | `createdon` | `TIMESTAMP`   | Not Null |
       | `lastlogin` | `TIMESTAMP`   | Optional |
     - **`comments`**:
       | Column Name | Data Type | Constraints |
       |-------------|-----------|-------------|
       | `id`        | `VARCHAR(36)` | Primary Key |
       | `username`  | `VARCHAR(36)` | Foreign Key (linked to `users.username`) |
       | `body`      | `VARCHAR(500)`| Optional |
       | `createdon` | `TIMESTAMP`   | Not Null |
   - Deletes all existing data in the tables during setup.

### 3. **Seed Data**
   - Inserts predefined user and comment data into the database:
     - Users:
       | Username | Password             |
       |----------|----------------------|
       | `admin`  | `!!SuperSecretAdmin!!` |
       | `alice`  | `AlicePassword!`      |
       | `bob`    | `BobPassword!`        |
       | `eve`    | `EVELknevl`           |
       | `rick`   | `!GetSchwifty!`       |
     - Comments:
       | Username | Comment         |
       |----------|-----------------|
       | `rick`   | `cool dog m8`   |
       | `alice`  | `OMG so cute!`  |

### 4. **MD5 Hashing**
   - Provides a method to generate an MD5 hash for a given string.
   - Ensures secure storage of passwords by converting them into a hexadecimal hash.

### 5. **Data Insertion**
   - **Insert User**:
     - Adds a new user to the `users` table with a hashed password.
     - Automatically generates a unique `userid` using `UUID`.
   - **Insert Comment**:
     - Adds a new comment to the `comments` table.
     - Automatically generates a unique `id` using `UUID`.

---

## Code Structure
### 1. **Data Structures**
   - The program defines the schema for two tables: `users` and `comments`.

### 2. **Logic**
   - **Connection Management**:
     - Establishes and manages the database connection.
   - **Schema Setup**:
     - Creates tables and inserts seed data.
   - **Data Manipulation**:
     - Inserts user and comment data into the database.
   - **Password Hashing**:
     - Generates MD5 hashes for secure password storage.

---

## Insights
- **Security**:
  - Passwords are hashed using MD5 before being stored in the database. However, MD5 is considered outdated and vulnerable to attacks. A more secure hashing algorithm like `bcrypt` or `SHA-256` should be used.
  
- **Error Handling**:
  - The program uses `try-catch` blocks to handle exceptions but does not implement robust error recovery mechanisms. For production use, logging frameworks and better exception handling should be considered.

- **Environment Variables**:
  - The program relies on environment variables for database configuration, which is a good practice for managing sensitive information.

- **Scalability**:
  - The schema design is simple and may not scale well for large applications. For example, the `password` column uses `VARCHAR(50)`, which may not be sufficient for hashed passwords in modern algorithms.

- **Hardcoded Seed Data**:
  - The seed data is hardcoded, which may not be ideal for production environments. Consider external configuration files or scripts for better flexibility.

- **UUID Usage**:
  - The program uses `UUID` for generating unique identifiers, which ensures uniqueness across distributed systems.

---

## Dependencies
- **PostgreSQL JDBC Driver**:
  - Required for database connectivity (`org.postgresql.Driver`).
- **Java Standard Libraries**:
  - `java.sql`: For database operations.
  - `java.security`: For MD5 hashing.
  - `java.util.UUID`: For generating unique identifiers.

---

## Usage
1. Set the required environment variables:
   - `PGHOST`, `PGDATABASE`, `PGUSER`, `PGPASSWORD`.
2. Run the program to set up the database schema and insert seed data.
3. Use the `insertUser` and `insertComment` methods to add new data to the database.

---
