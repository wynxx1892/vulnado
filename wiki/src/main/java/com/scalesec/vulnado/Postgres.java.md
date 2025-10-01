# Documentation: `Postgres.java`

## Overview
The `Postgres.java` file is a Java program designed to interact with a PostgreSQL database. It provides functionality for database connection, schema setup, data insertion, and hashing passwords using the MD5 algorithm. The program includes methods for initializing the database schema, inserting seed data, and calculating MD5 hashes for secure password storage.

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
- **`users`**: Stores user information.
  - `userid`: Unique identifier (UUID).
  - `username`: Unique username.
  - `password`: MD5 hashed password.
  - `createdon`: Timestamp of user creation.
  - `lastlogin`: Timestamp of the last login.
- **`comments`**: Stores user comments.
  - `id`: Unique identifier (UUID).
  - `username`: Username of the commenter.
  - `body`: Comment text.
  - `createdon`: Timestamp of comment creation.

Additionally, the method cleans up any existing data and inserts seed data for testing purposes.

### 3. **MD5 Hashing**
The `md5` method calculates the MD5 hash of a given string. This is used to securely store passwords in the database.

### 4. **Data Insertion**
The program provides methods to insert data into the database:
- `insertUser`: Inserts a new user into the `users` table.
- `insertComment`: Inserts a new comment into the `comments` table.

---

## Code Structure

### 1. **Imports**
The program uses the following Java libraries:
- `java.sql.Connection`, `java.sql.DriverManager`, `java.sql.PreparedStatement`, `java.sql.Statement`: For database operations.
- `java.math.BigInteger`: For converting byte arrays to hexadecimal strings.
- `java.security.MessageDigest`, `java.security.NoSuchAlgorithmException`: For MD5 hashing.
- `java.util.UUID`: For generating unique identifiers.

### 2. **Global Variables**
- `connection`: A static `Connection` object used to interact with the database.

### 3. **Methods**
| **Method Name**       | **Description**                                                                 |
|------------------------|---------------------------------------------------------------------------------|
| `connection()`         | Establishes a connection to the PostgreSQL database using environment variables.|
| `setup()`              | Sets up the database schema, cleans up existing data, and inserts seed data.   |
| `md5(String input)`    | Calculates the MD5 hash of a given string.                                     |
| `insertUser(String username, String password)` | Inserts a new user into the `users` table.             |
| `insertComment(String username, String body)` | Inserts a new comment into the `comments` table.       |

---

## Insights

### 1. **Security Concerns**
- **Password Hashing**: The program uses MD5 for hashing passwords, which is considered insecure for modern applications due to vulnerabilities like collision attacks. A stronger hashing algorithm such as bcrypt or Argon2 is recommended.
- **Environment Variables**: Sensitive database credentials are retrieved from environment variables, which is a good practice for security.

### 2. **Error Handling**
- The program uses `try-catch` blocks to handle exceptions during database operations. However, it does not implement robust error recovery mechanisms or logging frameworks.

### 3. **Hardcoded Seed Data**
- The `setup` method inserts hardcoded seed data into the database. This is useful for testing but should be removed or replaced with dynamic data in production environments.

### 4. **Scalability**
- The program is designed for basic database operations and does not include features like connection pooling, which could improve performance in high-load scenarios.

### 5. **UUID Usage**
- The program uses UUIDs for unique identifiers, ensuring that IDs are globally unique and reducing the risk of collisions.

---

## Example Usage

### Connecting to the Database
```java
Connection conn = Postgres.connection();
```

### Setting Up the Database
```java
Postgres.setup();
```

### Inserting a User
```java
Postgres.insertUser("john_doe", "SecurePassword123");
```

### Inserting a Comment
```java
Postgres.insertComment("john_doe", "This is a sample comment.");
```

---

## Dependencies
- PostgreSQL JDBC Driver (`org.postgresql.Driver`): Required for database connectivity.

---

## Environment Variables
| **Variable**   | **Description**                     |
|-----------------|-------------------------------------|
| `PGHOST`       | Hostname of the PostgreSQL server.  |
| `PGDATABASE`   | Name of the database.               |
| `PGUSER`       | Username for authentication.        |
| `PGPASSWORD`   | Password for authentication.        |

---

## Limitations
- The program does not validate user input, which could lead to SQL injection vulnerabilities.
- MD5 hashing is outdated and insecure for password storage.
- Lack of detailed logging and error recovery mechanisms.
