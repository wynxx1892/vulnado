# Documentation: `User` Class

## Overview

The `User` class is part of the `com.scalesec.vulnado` package and provides functionality for user management, including token generation, authentication, and database fetching. It interacts with a PostgreSQL database to retrieve user information and uses JSON Web Tokens (JWT) for authentication.

---

## Class Details

### Class: `User`

#### Fields
| Field Name      | Type       | Description                                      |
|------------------|------------|--------------------------------------------------|
| `id`            | `String`   | Unique identifier for the user.                  |
| `username`      | `String`   | Username of the user.                            |
| `hashedPassword`| `String`   | Hashed password of the user.                     |

#### Constructor
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
  - `secret`: A secret key used to sign the JWT.
- **Returns**: A `String` representing the generated JWT.
- **Description**: Generates a JWT for the user using the provided secret key. The token includes the `username` as the subject and is signed using the HMAC SHA algorithm.

---

### `assertAuth(String secret, String token)`
```java
public static void assertAuth(String secret, String token)
```
- **Parameters**:
  - `secret`: The secret key used to verify the JWT.
  - `token`: The JWT to be verified.
- **Throws**: `Unauthorized` exception if the token is invalid.
- **Description**: Verifies the provided JWT using the secret key. If the token is invalid, an exception is thrown.

---

### `fetch(String un)`
```java
public static User fetch(String un)
```
- **Parameters**:
  - `un`: The username to fetch from the database.
- **Returns**: A `User` object if the user is found, otherwise `null`.
- **Description**: Fetches a user from the PostgreSQL database based on the provided username. The query retrieves the first matching user and initializes a `User` object with the retrieved data.

---

## Insights

1. **Security Concerns**:
   - The `fetch` method directly concatenates the `username` into the SQL query, making it vulnerable to SQL Injection attacks. Use prepared statements to mitigate this risk.
   - The `assertAuth` method catches all exceptions and throws a generic `Unauthorized` exception. This could mask the root cause of the issue and should be handled more explicitly.

2. **Token Management**:
   - The `token` method uses the `username` as the subject of the JWT. Ensure that sensitive information is not included in the token payload.

3. **Database Connection**:
   - The `fetch` method does not use a connection pool, which could lead to performance issues under high load. Consider using a connection pool for better resource management.

4. **Error Handling**:
   - The `fetch` method logs exceptions but does not rethrow them, which could lead to silent failures. Proper error handling should be implemented.

5. **Code Structure**:
   - The class mixes data structure (fields) and logic (methods). Consider separating concerns by using a dedicated service class for database and authentication logic.

6. **Dependencies**:
   - The class relies on the `io.jsonwebtoken` library for JWT operations and a PostgreSQL database for user data. Ensure these dependencies are properly managed and updated.

---

## Potential Improvements

- Use prepared statements to prevent SQL Injection.
- Implement connection pooling for database operations.
- Enhance error handling to provide more meaningful feedback.
- Consider separating the data model (`User`) from the service logic (e.g., token generation, database fetching).
