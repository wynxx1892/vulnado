# Documentation: `Postgres.java`

## Overview
The `Postgres` class is designed to interact with a PostgreSQL database. It provides functionality for establishing a database connection, setting up the database schema, inserting seed data, and performing operations such as inserting users and comments. Additionally, it includes a utility method for generating MD5 hash values.

---

## Features
1. **Database Connection**:
   - Establishes a connection to a PostgreSQL database using environment variables for configuration.
   - Handles exceptions during connection setup and logs errors.

2. **Database Setup**:
   - Creates tables (`users` and `comments`) if they do not already exist.
   - Cleans up existing data in the tables.
   - Inserts seed data into the `users` and `comments` tables.

3. **MD5 Hash Generation**:
   - Provides a utility method to generate an MD5 hash for a given string.

4. **Data Insertion**:
   - Inserts user data into the `users` table.
   - Inserts comment data into the `comments` table.

---

## Code Structure

### 1. **Database Connection**
The `connection` method establishes a connection to the PostgreSQL database using the following environment variables:
- `PGHOST`: Hostname of the database server.
- `PGDATABASE`: Name of the database.
- `PGUSER`: Username for authentication.
- `PGPASSWORD`: Password for authentication.

#### Key Points:
- Uses `DriverManager.getConnection` to establish the connection.
- Logs errors and exits the program if the connection fails.

---

### 2. **Database Setup**
The `setup` method performs the following tasks:
- Creates the `users` table with the following schema:
  | Column Name | Data Type | Constraints |
  |-------------|-----------|-------------|
  | `userid`    | `VARCHAR(36)` | Primary Key |
  | `username`  | `VARCHAR(50)` | Unique, Not Null |
  | `password`  | `VARCHAR(50)` | Not Null |
  | `createdon` | `TIMESTAMP`   | Not Null |
  | `lastlogin` | `TIMESTAMP`   | - |

- Creates the `comments` table with the following schema:
  | Column Name | Data Type | Constraints |
  |-------------|-----------|-------------|
  | `id`        | `VARCHAR(36)` | Primary Key |
  | `username`  | `VARCHAR(36)` | - |
  | `body`      | `VARCHAR(500)`| - |
  | `createdon` | `TIMESTAMP`   | Not Null |

- Deletes all existing data from the `users` and `comments` tables.
- Inserts seed data into the tables.

#### Seed Data:
- **Users**:
  | Username | Password             |
  |----------|----------------------|
  | `admin`  | `!!SuperSecretAdmin!!` |
  | `alice`  | `AlicePassword!`      |
  | `bob`    | `BobPassword!`        |
  | `eve`    | `EVELknevl`           |
  | `rick`   | `!GetSchwifty!`       |

- **Comments**:
  | Username | Body               |
  |----------|--------------------|
  | `rick`   | `cool dog m8`      |
  | `alice`  | `OMG so cute!`     |

---

### 3. **MD5 Hash Generation**
The `md5` method generates an MD5 hash for a given string. It uses the `MessageDigest` class to compute the hash and converts the result into a hexadecimal string.

#### Key Points:
- Ensures the hash is always 32 characters long by padding with zeros if necessary.
- Throws a `RuntimeException` if the MD5 algorithm is not available.

---

### 4. **Data Insertion**
#### `insertUser` Method:
Inserts a new user into the `users` table.
- Parameters:
  - `username`: The username of the user.
  - `password`: The password of the user (hashed using MD5).
- Generates a unique `userid` using `UUID.randomUUID`.

#### `insertComment` Method:
Inserts a new comment into the `comments` table.
- Parameters:
  - `username`: The username associated with the comment.
  - `body`: The content of the comment.
- Generates a unique `id` using `UUID.randomUUID`.

---

## Insights
- **Security Concerns**:
  - Passwords are stored as MD5 hashes, which is considered insecure for modern applications. MD5 is vulnerable to collision attacks and should be replaced with a stronger hashing algorithm like bcrypt or Argon2.
  - The database connection credentials are retrieved from environment variables, which is a good practice for securing sensitive information.

- **Error Handling**:
  - The program logs errors but does not provide detailed recovery mechanisms. For production systems, consider implementing retry logic or more robust error handling.

- **Scalability**:
  - The current implementation clears all data during setup, which may not be suitable for production environments. Consider adding conditional logic to preserve existing data.

- **Hardcoded Seed Data**:
  - Seed data is hardcoded into the program, which limits flexibility. Using external configuration files or scripts for seeding data would improve maintainability.

- **Database Schema**:
  - The schema design is simple and functional but lacks foreign key constraints between `users` and `comments`. Adding such constraints would improve data integrity.

---
