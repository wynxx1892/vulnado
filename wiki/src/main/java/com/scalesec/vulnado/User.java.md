# Documentation: `User.java`

## Overview
The `User` class is part of the `com.scalesec.vulnado` package and provides functionality for user management, including token generation, authentication, and database interaction. It includes methods for creating JSON Web Tokens (JWTs), validating tokens, and fetching user data from a PostgreSQL database.

---

## Class: `User`

### Attributes
| Attribute       | Type       | Description                                      |
|------------------|------------|--------------------------------------------------|
| `id`            | `String`   | Unique identifier for the user.                 |
| `username`      | `String`   | Username of the user.                           |
| `hashedPassword`| `String`   | Hashed password of the user.                    |

---

### Constructor
#### `User(String id, String username, String hashedPassword)`
Initializes a new `User` object with the provided `id`, `username`, and `hashedPassword`.

**Parameters:**
- `id`: The unique identifier for the user.
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
- Uses `io.jsonwebtoken` library to create the JWT.
- The secret key is converted into a `SecretKey` object using `Keys.hmacShaKeyFor`.

---

#### `static void assertAuth(String secret, String token)`
Validates a given JWT token using the provided secret key.

**Parameters:**
- `secret`: A string used as the secret key for verifying the JWT.
- `token`: The JWT string to be validated.

**Throws:**
- `Unauthorized`: If the token validation fails.

**Implementation Details:**
- Uses `JwtParser` from the `io.jsonwebtoken` library to parse and validate the token.
- Converts the secret key into a `SecretKey` object using `Keys.hmacShaKeyFor`.

---

#### `static User fetch(String un)`
Fetches a user from the database based on the provided username.

**Parameters:**
- `un`: The username of the user to fetch.

**Returns:**
- A `User` object containing the user's details if found, otherwise `null`.

**Implementation Details:**
- Establishes a connection to a PostgreSQL database using `Postgres.connection`.
- Executes a SQL query to retrieve user details (`id`, `username`, `password`) from the `users` table.
- Constructs a `User` object if a matching record is found.

**Error Handling:**
- Prints stack trace and error messages to standard error in case of exceptions.
- Ensures the database connection is closed after execution.

---

## Insights

### Security Concerns
1. **SQL Injection Risk**: The `fetch` method directly concatenates the `un` parameter into the SQL query string, making it vulnerable to SQL injection attacks. Use prepared statements to mitigate this risk.
2. **Hardcoded Secret Key**: The `token` and `assertAuth` methods rely on a secret key passed as a string. Ensure the secret key is securely stored and managed (e.g., using environment variables or a secure vault).
3. **Exception Handling**: The `assertAuth` method catches exceptions but rethrows them as `Unauthorized` without additional context. Consider logging more detailed information for debugging purposes.

### Best Practices
- **Password Storage**: Ensure the `hashedPassword` is securely hashed using a strong algorithm (e.g., bcrypt or Argon2) and salted.
- **Database Connection Management**: Use connection pooling to optimize database interactions and avoid potential connection leaks.
- **Token Expiry**: Add an expiration time to the JWT to enhance security.

### Dependencies
- **io.jsonwebtoken**: Used for JWT creation and validation.
- **javax.crypto**: Used for cryptographic operations.
- **Postgres.connection**: Assumed to be a utility for establishing database connections.

---

### Potential Enhancements
1. **Input Validation**: Validate user input (e.g., `username`) to prevent injection attacks and ensure data integrity.
2. **Error Handling**: Improve error handling by providing more descriptive error messages and logging mechanisms.
3. **Unit Tests**: Add unit tests for methods like `token`, `assertAuth`, and `fetch` to ensure correctness and reliability.
