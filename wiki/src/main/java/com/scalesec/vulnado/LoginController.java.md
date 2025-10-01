# Documentation: `LoginController.java`

## Overview
The `LoginController` class is a REST controller designed to handle user login requests. It validates user credentials against stored data and generates a token upon successful authentication. The controller uses Spring Boot annotations and integrates with a database for user authentication.

---

## File Metadata
- **File Name**: `LoginController.java`

---

## Class Details

### `LoginController`
The main class responsible for handling login requests.

#### Annotations:
- `@RestController`: Marks the class as a REST controller, enabling it to handle HTTP requests.
- `@EnableAutoConfiguration`: Enables Spring Boot's auto-configuration feature.
- `@CrossOrigin(origins = "*")`: Allows cross-origin requests from any domain.

#### Fields:
| Field Name | Type   | Description                          |
|------------|--------|--------------------------------------|
| `secret`   | String | A secret value used for token generation. |

#### Methods:
| Method Name | HTTP Method | Path   | Description                                                                 |
|-------------|-------------|--------|-----------------------------------------------------------------------------|
| `login`     | POST        | `/login` | Handles login requests, validates credentials, and returns a token if successful. |

#### Method Details:
- **`login(LoginRequest input)`**:
  - **Input**: Accepts a `LoginRequest` object containing `username` and `password`.
  - **Logic**:
    1. Fetches the user details using `User.fetch(input.username)`.
    2. Compares the hashed password (`Postgres.md5(input.password)`) with the stored hashed password (`user.hashedPassword`).
    3. If the credentials match, returns a `LoginResponse` containing the token.
    4. If the credentials do not match, throws an `Unauthorized` exception.
  - **Output**: Returns a `LoginResponse` object containing the token.

---

### Supporting Classes

#### `LoginRequest`
A data structure representing the login request payload.

| Field Name | Type   | Description                  |
|------------|--------|------------------------------|
| `username` | String | The username of the user.    |
| `password` | String | The password of the user.    |

#### `LoginResponse`
A data structure representing the login response payload.

| Field Name | Type   | Description                  |
|------------|--------|------------------------------|
| `token`    | String | The generated token for the user. |

#### `Unauthorized`
A custom exception class used to handle unauthorized access.

| Field Name | Type   | Description                  |
|------------|--------|------------------------------|
| `exception`| String | The exception message.       |

---

## Insights

1. **Security Concerns**:
   - The use of `Postgres.md5` for password hashing may not be secure. Modern applications should use stronger hashing algorithms like `bcrypt` or `PBKDF2`.
   - The `secret` field is used for token generation but is hardcoded. Consider using environment variables or a secure configuration management system.

2. **Cross-Origin Requests**:
   - The `@CrossOrigin(origins = "*")` annotation allows requests from any domain, which may pose a security risk. It is recommended to restrict origins to trusted domains.

3. **Error Handling**:
   - The `Unauthorized` exception is thrown for invalid credentials, but the response does not provide detailed error messages. This is good practice to prevent information leakage.

4. **Serialization**:
   - Both `LoginRequest` and `LoginResponse` implement `Serializable`, which is useful for object serialization but may not be necessary unless explicitly required.

5. **Database Interaction**:
   - The `User.fetch` method is used to retrieve user details. Ensure that this method is implemented securely to prevent SQL injection or other vulnerabilities.

6. **Token Management**:
   - The token generation logic is not detailed in the code. Ensure that tokens are securely generated and follow best practices (e.g., JWT with proper signing and expiration).

---

## Dependencies
The code relies on the following Spring Boot modules:
- `org.springframework.boot`
- `org.springframework.http`
- `org.springframework.web.bind.annotation`
- `org.springframework.boot.autoconfigure`
- `org.springframework.stereotype`
- `org.springframework.beans.factory.annotation`

Additionally, it uses Java's `Serializable` interface for data structures.
