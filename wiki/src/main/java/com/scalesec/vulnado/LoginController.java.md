# Documentation: LoginController.java

## Overview
The `LoginController` class is a REST controller designed to handle user login requests in a Spring Boot application. It validates user credentials against stored data and generates a token for successful authentication. The controller also includes mechanisms for handling unauthorized access.

---

## File Metadata
- **File Name**: `LoginController.java`

---

## Class Details

### `LoginController`
The main controller class responsible for handling login requests.

#### Annotations:
- `@RestController`: Indicates that this class is a REST controller.
- `@EnableAutoConfiguration`: Enables Spring Boot's auto-configuration feature.
- `@CrossOrigin(origins = "*")`: Allows cross-origin requests from any domain.
- `@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")`: Maps HTTP POST requests to the `/login` endpoint, specifying JSON as the input and output format.

#### Fields:
- `@Value("app.secret") private String secret`: Injects the application secret from the configuration file.

#### Method:
- **`login(LoginRequest input)`**:
  - **Input**: Accepts a `LoginRequest` object containing `username` and `password`.
  - **Logic**:
    - Fetches the user details using `User.fetch(input.username)`.
    - Compares the hashed password (`Postgres.md5(input.password)`) with the stored hashed password (`user.hashedPassword`).
    - If the credentials match, returns a `LoginResponse` containing the token generated using `user.token` and `secret`.
    - If the credentials do not match, throws an `Unauthorized` exception.
  - **Output**: Returns a `LoginResponse` object or throws an exception.

---

### Supporting Classes

#### `LoginRequest`
A data structure representing the login request payload.

| Field Name | Type     | Description                  |
|------------|----------|------------------------------|
| `username` | `String` | The username of the user.    |
| `password` | `String` | The password of the user.    |

#### `LoginResponse`
A data structure representing the login response payload.

| Field Name | Type     | Description                  |
|------------|----------|------------------------------|
| `token`    | `String` | The authentication token.    |

- **Constructor**:
  - `LoginResponse(String msg)`: Initializes the `token` field with the provided message.

#### `Unauthorized`
A custom exception class for handling unauthorized access.

| Field Name | Type     | Description                  |
|------------|----------|------------------------------|
| `exception`| `String` | The exception message.       |

- **Constructor**:
  - `Unauthorized(String exception)`: Initializes the exception message.

---

## Insights

1. **Security Considerations**:
   - The password comparison uses `Postgres.md5`, which may not be secure for modern applications. Consider using a stronger hashing algorithm like `bcrypt` or `PBKDF2`.
   - The `@CrossOrigin(origins = "*")` annotation allows requests from all origins, which could expose the application to Cross-Origin Resource Sharing (CORS) vulnerabilities. Restrict origins to trusted domains.

2. **Error Handling**:
   - The `Unauthorized` exception is thrown for invalid credentials, but it does not provide detailed feedback. This is a good practice to prevent information leakage.

3. **Token Generation**:
   - The token generation logic is not explicitly defined in the code. Ensure that the token is securely generated and follows industry standards (e.g., JWT).

4. **Scalability**:
   - The `User.fetch` method is used to retrieve user details. Ensure that this method is optimized for performance, especially in applications with a large user base.

5. **Serialization**:
   - Both `LoginRequest` and `LoginResponse` implement `Serializable`, which is useful for object serialization in distributed systems.

6. **Annotations**:
   - The use of Spring annotations simplifies the configuration and setup of the controller.

---

## Potential Improvements
- Replace `Postgres.md5` with a modern hashing algorithm for password storage and comparison.
- Restrict CORS origins to trusted domains.
- Implement detailed logging for failed login attempts to monitor potential security threats.
- Define token generation logic explicitly and ensure compliance with security standards.
