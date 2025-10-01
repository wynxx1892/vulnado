# Documentation: `User.java`

## Overview
The `User.java` file defines a class `User` that represents a user entity in the application. It includes methods for generating JSON Web Tokens (JWT), validating authentication tokens, and fetching user data from a PostgreSQL database. The class leverages libraries such as `io.jsonwebtoken` for JWT handling and `javax.crypto` for cryptographic operations.

---

## Class: `User`

### Fields
| Field Name       | Type       | Description                                                                 |
|------------------|------------|-----------------------------------------------------------------------------|
| `id`             | `String`   | Represents the unique identifier of the user.                              |
| `username`       | `String`   | Stores the username of the user.                                           |
| `hashedPassword` | `String`   | Stores the hashed password of the user for authentication purposes.         |

---

### Constructor
#### `User(String id, String username, String hashedPassword)`
Initializes a new `User` object with the provided `id`, `username`, and `hashedPassword`.

**Parameters:**
- `id`: The unique identifier of the user.
- `username`: The username of the user.
- `hashedPassword`: The hashed password of the user.

---

### Methods

#### `String token(String secret)`
Generates a JSON Web Token (JWT) for the user using the provided secret key.

**Parameters:**
- `secret`: A string used as the secret key for signing the JWT.

**Returns:**
- A signed JWT string containing the user's username as the subject.

**Implementation Details:**
- Uses `Keys.hmacShaKeyFor` to generate a `SecretKey` from the provided secret.
- Uses `Jwts.builder` to create and sign the JWT.

---

#### `static void assertAuth(String secret, String token)`
Validates the provided JWT token using the secret key. Throws an exception if the token is invalid.

**Parameters:**
- `secret`: A string used as the secret key for verifying the JWT.
- `token`: The JWT string to be validated.

**Throws:**
- `Unauthorized`: If the token is invalid or cannot be parsed.

**Implementation Details:**
- Uses `Keys.hmacShaKeyFor` to generate a `SecretKey` from the provided secret.
- Uses `Jwts.parser` to parse and validate the token.

---

#### `static User fetch(String un)`
Fetches a user from the database based on the provided username.

**Parameters:**
- `un`: The username of the user to be fetched.

**Returns:**
- A `User` object containing the user's details if found, otherwise `null`.

**Implementation Details:**
- Establishes a connection to the PostgreSQL database using `Postgres.connection`.
- Executes a SQL query to retrieve user details based on the username.
- Maps the result set to a `User` object.

**Error Handling:**
- Prints stack trace and error messages to the console in case of exceptions.
- Ensures the database connection is closed after execution.

---

## Insights

### Security Considerations
1. **Hardcoded SQL Query**: The `fetch` method uses string concatenation for SQL queries, which makes it vulnerable to SQL injection attacks. It is recommended to use prepared statements to mitigate this risk.
2. **JWT Secret Management**: The secret key for JWT signing and validation is passed as a string. Ensure that the secret is stored securely and not hardcoded in the application.
3. **Exception Handling**: The `assertAuth` method prints stack traces and throws a generic `Unauthorized` exception. Consider logging errors securely and providing more specific error messages.

### Database Interaction
- The `fetch` method directly interacts with a PostgreSQL database. Ensure that the database connection is properly configured and secured.
- The method assumes the existence of a `users` table with columns `userid`, `username`, and `password`.

### Cryptographic Operations
- The class uses `Keys.hmacShaKeyFor` for generating cryptographic keys and `SignatureAlgorithm.HS256` for signing JWTs. Ensure that the cryptographic library is up-to-date to avoid vulnerabilities.

### Dependencies
- **JWT Library**: The class uses `io.jsonwebtoken` for JWT handling. Ensure the library version is compatible with the application.
- **Database Connection**: The `Postgres.connection` method is assumed to provide a valid database connection. Ensure proper configuration and error handling for database connectivity.

---

### Recommendations
- Refactor the `fetch` method to use prepared statements for SQL queries.
- Implement proper logging mechanisms for error handling.
- Use environment variables or a secure vault for managing secrets like the JWT signing key.
