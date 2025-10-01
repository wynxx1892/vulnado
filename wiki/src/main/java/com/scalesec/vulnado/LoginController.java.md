# Documentation: LoginController.java

## Overview
The `LoginController` class is a REST controller designed to handle user login requests. It validates user credentials against stored data and generates a token for successful authentication. The controller uses Spring Boot annotations and integrates with a database for user authentication.

---

## File Metadata
- **File Name**: `LoginController.java`

---

## Class Details

### 1. **LoginController**
The main class responsible for handling login requests.

#### Annotations:
- `@RestController`: Indicates that this class is a REST controller.
- `@EnableAutoConfiguration`: Enables Spring Boot's auto-configuration feature.
- `@CrossOrigin(origins = "*")`: Allows cross-origin requests from any domain.
- `@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")`: Maps HTTP POST requests to the `/login` endpoint, specifying JSON as the input and output format.

#### Fields:
- `@Value("app.secret") private String secret`: Injects the application secret from the configuration file.

#### Method:
- **`login(@RequestBody LoginRequest input)`**:
  - **Input**: Accepts a `LoginRequest` object containing `username` and `password`.
  - **Logic**:
    - Fetches the user details using `User.fetch(input.username)`.
    - Compares the hashed password (`Postgres.md5(input.password)`) with the stored hashed password (`user.hashedPassword`).
    - If the credentials match, returns a `LoginResponse` containing the token generated using the `secret`.
    - If the credentials do not match, throws an `Unauthorized` exception.
  - **Output**: Returns a `LoginResponse` object with a token or throws an exception.

---

### 2. **LoginRequest**
A data structure representing the login request payload.

#### Fields:
- `String username`: The username provided by the user.
- `String password`: The password provided by the user.

#### Characteristics:
- Implements `Serializable` for object serialization.

---

### 3. **LoginResponse**
A data structure representing the login response payload.

#### Fields:
- `String token`: The authentication token generated upon successful login.

#### Constructor:
- `LoginResponse(String msg)`: Initializes the `token` field with the provided message.

#### Characteristics:
- Implements `Serializable` for object serialization.

---

### 4. **Unauthorized**
A custom exception class for handling unauthorized access.

#### Annotations:
- `@ResponseStatus(HttpStatus.UNAUTHORIZED)`: Maps this exception to the HTTP 401 Unauthorized status.

#### Constructor:
- `Unauthorized(String exception)`: Initializes the exception message.

---

## Insights

### Security Considerations:
- **Password Hashing**: The code uses `Postgres.md5` for password hashing. Ensure that the hashing algorithm is secure and up-to-date. Consider using stronger algorithms like bcrypt or Argon2.
- **Token Generation**: The token generation logic is not explicitly shown. Ensure that the token is securely generated and follows best practices (e.g., JWT with proper signing and expiration).
- **Cross-Origin Requests**: Allowing all origins (`@CrossOrigin(origins = "*")`) can be a security risk. Restrict origins to trusted domains.

### Error Handling:
- The `Unauthorized` exception is thrown for invalid credentials, which is mapped to HTTP 401. Ensure proper logging and monitoring of failed login attempts to detect potential brute-force attacks.

### Scalability:
- The `User.fetch` method is assumed to query the database. Ensure that the database queries are optimized for performance, especially for large user bases.

### Serialization:
- Both `LoginRequest` and `LoginResponse` implement `Serializable`. This is useful for object transmission but ensure compatibility with the serialization mechanism used.

### Missing Details:
- The `User` class and its methods (`fetch`, `hashedPassword`, `token`) are not defined in this snippet. Ensure that these methods are implemented securely and efficiently.

---

## Endpoints

| **Endpoint** | **HTTP Method** | **Consumes** | **Produces** | **Description** |
|--------------|-----------------|--------------|--------------|-----------------|
| `/login`     | POST            | application/json | application/json | Authenticates a user and returns a token. |

---

## Dependencies
- **Spring Boot**: For REST controller and auto-configuration.
- **Spring Web**: For HTTP request handling.
- **Spring Beans**: For dependency injection (`@Value`).
- **Java Serialization**: For serializing request and response objects.


