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
- The secret key is converted into a `SecretKey` using `Keys.hmacShaKeyFor`.

---

#### `static void assertAuth(String secret, String token)`
Validates a given JWT token using the provided secret key.

**Parameters:**
- `secret`: A string used as the secret key for verifying the JWT.
- `token`: The JWT string to be validated.

**Throws:**
- `Unauthorized`: If the token validation fails.

**Implementation Details:**
- Parses the token using `JwtParser` from the `io.jsonwebtoken` library.
- If validation fails, an exception is thrown with the error message.

---

#### `static User fetch(String un)`
Fetches a user from the database based on the provided username.

**Parameters:**
- `un`: The username of the user to be fetched.

**Returns:**
- A `User` object containing the user's details if found, otherwise `null`.

**Implementation Details:**
- Connects to a PostgreSQL database using a `Postgres.connection` method (assumed to exist).
- Executes a SQL query to retrieve user details based on the username.
- Constructs a `User` object using the retrieved data.

**Database Query:**
```sql
SELECT * FROM users WHERE username = '<username>' LIMIT 1;
```

---

## Insights

### Security Concerns
1. **SQL Injection Vulnerability**:
   - The `fetch` method directly concatenates the `username` into the SQL query string, making it vulnerable to SQL injection attacks. Use prepared statements to mitigate this risk.

2. **Weak Error Handling**:
   - The `assertAuth` and `fetch` methods print stack traces and error messages to the console, which could expose sensitive information. Consider logging errors securely and providing generic error messages to the user.

3. **Hardcoded Secret Key**:
   - The `token` and `assertAuth` methods rely on a secret key passed as a parameter. Ensure the secret key is securely stored and managed (e.g., using environment variables or a secure vault).

4. **Password Storage**:
   - The `hashedPassword` attribute suggests that passwords are hashed, but the hashing algorithm is not specified. Use a strong hashing algorithm like `bcrypt` or `PBKDF2` for password storage.

---

### Code Quality
1. **Lack of Input Validation**:
   - The `fetch` method does not validate the `username` input, which could lead to unexpected behavior or security vulnerabilities.

2. **Resource Management**:
   - The database connection (`cxn`) is closed in the `try` block, but if an exception occurs before the `close` statement, the connection may remain open. Use a `finally` block or try-with-resources to ensure proper resource management.

3. **Error Propagation**:
   - The `fetch` method returns `null` if an error occurs, which may lead to null pointer exceptions in the calling code. Consider throwing a custom exception or returning an optional value.

---

### Dependencies
The class relies on the following libraries:
- `io.jsonwebtoken`: For JWT creation and validation.
- `javax.crypto`: For cryptographic operations.
- `java.sql`: For database interaction.

---

### Recommendations
- Refactor the `fetch` method to use prepared statements for secure database queries.
- Implement proper error handling and logging mechanisms.
- Use a secure method for managing the secret key.
- Validate all user inputs to prevent unexpected behavior or security issues.
