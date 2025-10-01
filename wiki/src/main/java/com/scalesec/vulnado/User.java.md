# Documentation: `User.java`

## Overview
The `User.java` file defines a class `User` that represents a user entity in the application. It includes methods for generating JSON Web Tokens (JWT), validating authentication tokens, and fetching user data from a PostgreSQL database. The class leverages libraries such as `io.jsonwebtoken` for JWT handling and `javax.crypto` for cryptographic operations.

---

## Class: `User`

### Attributes
| Attribute         | Type       | Description                                                                 |
|-------------------|------------|-----------------------------------------------------------------------------|
| `id`              | `String`   | Unique identifier for the user.                                            |
| `username`        | `String`   | Username of the user.                                                      |
| `hashedPassword`  | `String`   | Hashed password of the user for secure storage.                            |

### Constructor
```java
public User(String id, String username, String hashedPassword)
```
- **Parameters**:
  - `id`: The unique identifier for the user.
  - `username`: The username of the user.
  - `hashedPassword`: The hashed password of the user.
- **Description**: Initializes a `User` object with the provided `id`, `username`, and `hashedPassword`.

---

## Methods

### `token(String secret)`
```java
public String token(String secret)
```
- **Parameters**:
  - `secret`: A secret key used for signing the JWT.
- **Returns**: A signed JWT string.
- **Description**: Generates a JWT for the user using the `username` as the subject. The token is signed using the provided secret key and the HMAC SHA algorithm.

---

### `assertAuth(String secret, String token)`
```java
public static void assertAuth(String secret, String token)
```
- **Parameters**:
  - `secret`: The secret key used to validate the token.
  - `token`: The JWT to be validated.
- **Throws**: `Unauthorized` exception if the token is invalid.
- **Description**: Validates the provided JWT using the secret key. If the token is invalid, an exception is thrown.

---

### `fetch(String un)`
```java
public static User fetch(String un)
```
- **Parameters**:
  - `un`: The username to search for in the database.
- **Returns**: A `User` object if the user is found; otherwise, `null`.
- **Description**: Fetches a user from the PostgreSQL database based on the provided username. The query retrieves the user's `id`, `username`, and `hashedPassword`.

---

## Insights

### Security Considerations
1. **Hardcoded Secret Key**: The secret key is passed as a parameter, but its management and storage are not detailed. Ensure the secret key is securely stored and rotated periodically.
2. **SQL Injection Risk**: The `fetch` method constructs SQL queries using string concatenation, which is vulnerable to SQL injection. Use prepared statements to mitigate this risk.
3. **Exception Handling**: The `assertAuth` and `fetch` methods catch exceptions but do not provide detailed error handling or logging. Consider improving exception handling to avoid exposing sensitive information.

### Dependencies
- **JWT Handling**: The `io.jsonwebtoken` library is used for creating and parsing JWTs.
- **Database Connection**: The `Postgres.connection` method is assumed to provide a valid database connection. Ensure proper connection pooling and error handling.

### Potential Improvements
1. **Password Security**: The `hashedPassword` attribute should use a strong hashing algorithm like bcrypt or Argon2 for secure password storage.
2. **Database Query Optimization**: The `fetch` method uses `LIMIT 1` to retrieve a single user, which is efficient. However, ensure proper indexing on the `username` column for faster lookups.
3. **Token Expiry**: The generated JWT does not include an expiration claim. Add an expiration time to enhance security.

### Code Structure
- **Data Structure**: The `User` class primarily serves as a data structure to hold user-related information.
- **Logic**: The methods `token`, `assertAuth`, and `fetch` implement logic for token generation, validation, and database interaction.

---

## File Metadata
| Key         | Value         |
|-------------|---------------|
| `File Name` | `User.java`   |
