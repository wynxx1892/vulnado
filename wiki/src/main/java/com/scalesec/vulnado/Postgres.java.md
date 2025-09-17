# Documentation: `Postgres.java`

## Overview

The `Postgres` class is a Java program designed to interact with a PostgreSQL database. It provides functionality for establishing a database connection, setting up database schemas, inserting seed data, and hashing passwords using the MD5 algorithm. The class also includes methods for inserting user and comment data into the database.

---

## Table of Contents

1. [Class Details](#class-details)
2. [Environment Variables](#environment-variables)
3. [Database Schema](#database-schema)
4. [Methods](#methods)
5. [Insights](#insights)

---

## Class Details

- **Class Name**: `Postgres`
- **Purpose**: 
  - Establish a connection to a PostgreSQL database.
  - Set up database schemas and seed data.
  - Provide utility methods for inserting users and comments.
  - Hash strings using the MD5 algorithm.

---

## Environment Variables

The program relies on the following environment variables for database connection:

| Environment Variable | Description                          |
|-----------------------|--------------------------------------|
| `PGHOST`             | Hostname of the PostgreSQL server.  |
| `PGDATABASE`         | Name of the database.               |
| `PGUSER`             | Username for database authentication. |
| `PGPASSWORD`         | Password for database authentication. |

---

## Database Schema

The program creates two tables in the database:

### 1. `users` Table
| Column Name | Data Type   | Constraints                     |
|-------------|-------------|----------------------------------|
| `userid`    | `VARCHAR(36)` | Primary Key.                   |
| `username`  | `VARCHAR(50)` | Unique, Not Null.              |
| `password`  | `VARCHAR(50)` | Not Null.                      |
| `createdon` | `TIMESTAMP`   | Not Null.                      |
| `lastlogin` | `TIMESTAMP`   | -                              |

### 2. `comments` Table
| Column Name | Data Type     | Constraints                     |
|-------------|---------------|----------------------------------|
| `id`        | `VARCHAR(36)` | Primary Key.                    |
| `username`  | `VARCHAR(36)` | Foreign Key (references `users`).|
| `body`      | `VARCHAR(500)`| -                               |
| `createdon` | `TIMESTAMP`   | Not Null.                       |

---

## Methods

### 1. `public static Connection connection()`
- **Purpose**: Establishes a connection to the PostgreSQL database.
- **Logic**:
  - Loads the PostgreSQL driver.
  - Constructs the database URL using environment variables.
  - Returns a `Connection` object.
- **Error Handling**: Prints stack trace and exits the program if an exception occurs.

---

### 2. `public static void setup()`
- **Purpose**: Sets up the database schema and seeds initial data.
- **Logic**:
  - Creates the `users` and `comments` tables if they do not exist.
  - Deletes any existing data in the tables.
  - Inserts predefined seed data for users and comments.
- **Seed Data**:
  - **Users**:
    - `admin` with password `!!SuperSecretAdmin!!`
    - `alice` with password `AlicePassword!`
    - `bob` with password `BobPassword!`
    - `eve` with password `EVELknevl`
    - `rick` with password `!GetSchwifty!`
  - **Comments**:
    - `rick`: "cool dog m8"
    - `alice`: "OMG so cute!"
- **Error Handling**: Prints stack trace and exits the program if an exception occurs.

---

### 3. `public static String md5(String input)`
- **Purpose**: Generates an MD5 hash for a given input string.
- **Logic**:
  - Uses `MessageDigest` to compute the MD5 hash.
  - Converts the hash to a hexadecimal string.
  - Pads the hash to ensure it is 32 characters long.
- **Error Handling**: Throws a `RuntimeException` if the MD5 algorithm is unavailable.

---

### 4. `private static void insertUser(String username, String password)`
- **Purpose**: Inserts a new user into the `users` table.
- **Logic**:
  - Generates a UUID for the `userid`.
  - Hashes the password using the `md5` method.
  - Executes an `INSERT` SQL statement.
- **Error Handling**: Prints stack trace if an exception occurs.

---

### 5. `private static void insertComment(String username, String body)`
- **Purpose**: Inserts a new comment into the `comments` table.
- **Logic**:
  - Generates a UUID for the `id`.
  - Executes an `INSERT` SQL statement.
- **Error Handling**: Prints stack trace if an exception occurs.

---

## Insights

- **Security Concerns**:
  - The program uses the MD5 hashing algorithm for passwords, which is considered insecure due to vulnerabilities to collision attacks. A stronger hashing algorithm like `bcrypt` or `SHA-256` should be used.
  - Storing passwords as plain MD5 hashes without a salt increases the risk of dictionary and rainbow table attacks.

- **Error Handling**:
  - The program exits abruptly (`System.exit(1)`) on certain exceptions, which may not be ideal for production environments. Consider using proper exception handling mechanisms.

- **Database Connection**:
  - The database connection is established using environment variables, which is a good practice for managing sensitive credentials.

- **Scalability**:
  - The `setup` method deletes all existing data in the `users` and `comments` tables before inserting seed data. This behavior may not be suitable for production environments.

- **Code Structure**:
  - The `connection` method is static and shared across the class. This design may lead to issues in multi-threaded environments. Consider using connection pooling for better scalability.

- **UUID Usage**:
  - The program uses UUIDs for primary keys, which ensures uniqueness across distributed systems.
