# Documentation: `Postgres.java`

## Overview
The `Postgres.java` file is a Java program designed to interact with a PostgreSQL database. It provides functionality for database connection, schema setup, data insertion, and hashing passwords using the MD5 algorithm. The program includes methods for initializing the database schema, inserting user and comment data, and generating MD5 hashes for secure password storage.

---

## Features
### 1. **Database Connection**
The program establishes a connection to a PostgreSQL database using environment variables for configuration:
- `PGHOST`: Hostname of the PostgreSQL server.
- `PGDATABASE`: Name of the database.
- `PGUSER`: Username for authentication.
- `PGPASSWORD`: Password for authentication.

### 2. **Database Schema Setup**
The `setup` method creates two tables:
- **`users` Table**:
  - `userid`: Unique identifier for the user (UUID).
  - `username`: Unique username.
  - `password`: MD5-hashed password.
  - `createdon`: Timestamp of user creation.
  - `lastlogin`: Timestamp of the last login.

- **`comments` Table**:
  - `id`: Unique identifier for the comment (UUID).
  - `username`: Username of the commenter.
  - `body`: Content of the comment.
  - `createdon`: Timestamp of comment creation.

The method also cleans up any existing data and inserts seed data for testing purposes.

### 3. **MD5 Hashing**
The `md5` method generates an MD5 hash for a given string. This is used to securely store passwords in the database.

### 4. **Data Insertion**
The program provides methods to insert data into the database:
- `insertUser`: Inserts a new user into the `users` table with a hashed password.
- `insertComment`: Inserts a new comment into the `comments` table.

---

## Code Structure

### 1. **Data Structures**
The program defines the schema for two tables (`users` and `comments`) in the PostgreSQL database. These tables are created dynamically during the `setup` method execution.

### 2. **Logic**
The program contains logic for:
- Establishing a database connection.
- Creating tables and cleaning up existing data.
- Inserting seed data into the database.
- Hashing passwords using MD5.
- Handling exceptions during database operations.

---

## Methods

### 1. `connection`
Establishes a connection to the PostgreSQL database using the `DriverManager` class. If the connection fails, the program prints the error and exits.

### 2. `setup`
Sets up the database schema and inserts seed data:
- Creates the `users` and `comments` tables if they do not exist.
- Deletes existing data from both tables.
- Inserts predefined seed data for testing.

### 3. `md5(String input)`
Generates an MD5 hash for the given input string:
- Converts the input string into a byte array.
- Computes the MD5 hash using `MessageDigest`.
- Converts the hash into a hexadecimal string.

### 4. `insertUser(String username, String password)`
Inserts a new user into the `users` table:
- Generates a UUID for the user ID.
- Hashes the password using the `md5` method.
- Executes an SQL `INSERT` statement.

### 5. `insertComment(String username, String body)`
Inserts a new comment into the `comments` table:
- Generates a UUID for the comment ID.
- Executes an SQL `INSERT` statement.

---

## Insights

### Security Considerations
- **Password Hashing**: The program uses MD5 for password hashing, which is considered insecure for modern applications due to vulnerabilities such as collision attacks. It is recommended to use stronger hashing algorithms like bcrypt or Argon2 for password storage.
- **Environment Variables**: Database credentials are retrieved from environment variables, which is a good practice for securing sensitive information.

### Error Handling
- The program prints stack traces for exceptions but does not implement robust error handling mechanisms. This could lead to sensitive information being exposed in production environments.

### Scalability
- The program is designed for basic database operations and does not include features like connection pooling or transaction management, which are essential for handling high loads.

### Seed Data
- The `setup` method inserts hardcoded seed data, which is useful for testing but should be removed or modified for production environments.

### SQL Injection
- The program uses `PreparedStatement` for SQL queries, which helps prevent SQL injection attacks.

---

## Environment Variables

| Variable       | Description                     |
|----------------|---------------------------------|
| `PGHOST`       | Hostname of the PostgreSQL server. |
| `PGDATABASE`   | Name of the database.           |
| `PGUSER`       | Username for authentication.    |
| `PGPASSWORD`   | Password for authentication.    |

---

## Dependencies

| Dependency                  | Purpose                                      |
|-----------------------------|----------------------------------------------|
| `java.sql.Connection`       | Manages the database connection.            |
| `java.sql.DriverManager`    | Establishes the database connection.         |
| `java.sql.PreparedStatement`| Executes parameterized SQL queries.         |
| `java.sql.Statement`        | Executes SQL statements.                    |
| `java.security.MessageDigest`| Generates MD5 hashes for password storage. |
| `java.util.UUID`            | Generates unique identifiers for users and comments. |
