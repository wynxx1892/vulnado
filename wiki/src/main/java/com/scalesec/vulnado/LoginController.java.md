# Documentation: LoginController.java

## Overview
The `LoginController` class is a REST controller designed to handle login requests in a Spring Boot application. It provides functionality for authenticating users based on their credentials and returning a token upon successful authentication. The controller uses a combination of Spring annotations and custom logic to manage user authentication.

---

## Class Structure

### 1. **LoginController**
The main controller class responsible for handling login requests.

#### Annotations:
- `@RestController`: Indicates that this class is a REST controller.
- `@EnableAutoConfiguration`: Enables Spring Boot's auto-configuration feature.
- `@CrossOrigin(origins = "*")`: Allows cross-origin requests from any domain.
- `@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")`: Maps HTTP POST requests to the `/login` endpoint.

#### Fields:
- `@Value("${app.secret}") private String secret`: Injects the application's secret key from the configuration file.

#### Method:
- `LoginResponse login(@RequestBody LoginRequest input)`:
  - **Input**: Accepts a `LoginRequest` object containing `username` and `password`.
  - **Logic**:
    - Fetches the user details using `User.fetch(input.username)`.
    - Compares the hashed password (`Postgres.md5(input.password)`) with the stored hashed password (`user.hashedPassword`).
    - If the credentials match, returns a `LoginResponse` containing the user's token and the application's secret.
    - If the credentials do not match, throws an `Unauthorized` exception.
  - **Output**: Returns a `LoginResponse` object or throws an exception.

---

### 2. **LoginRequest**
A data structure representing the input for the login request.

#### Fields:
- `public String username`: The username provided by the user.
- `public String password`: The password provided by the user.

#### Characteristics:
- Implements `Serializable` for object serialization.

---

### 3. **LoginResponse**
A data structure representing the response for a successful login.

#### Fields:
- `public String token`: The token generated for the authenticated user.

#### Constructor:
- `public LoginResponse(String msg)`: Initializes the `token` field with the provided message.

#### Characteristics:
- Implements `Serializable` for object serialization.

---

### 4. **Unauthorized**
A custom exception class for handling unauthorized access.

#### Annotations:
- `@ResponseStatus(HttpStatus.UNAUTHORIZED)`: Maps this exception to the HTTP 401 Unauthorized status.

#### Constructor:
- `public Unauthorized(String exception)`: Initializes the exception message.

---

## Insights

### Security Considerations:
1. **Password Hashing**:
   - The code uses `Postgres.md5()` for password hashing. Ensure that the hashing algorithm is secure and follows modern cryptographic standards (e.g., bcrypt, PBKDF2, or Argon2).
   - Avoid using MD5 for password hashing as it is considered insecure.

2. **Token Management**:
   - The `LoginResponse` returns a token. Ensure that the token is securely generated and follows best practices (e.g., JWT with proper signing and expiration).

3. **Cross-Origin Requests**:
   - The `@CrossOrigin(origins = "*")` annotation allows requests from any domain. This can be a security risk if not properly configured. Consider restricting origins to trusted domains.

4. **Exception Handling**:
   - The `Unauthorized` exception is mapped to HTTP 401. Ensure that sensitive information is not exposed in the exception message.

### Code Quality:
- The `User.fetch()` method is used to retrieve user details. Ensure that this method is implemented securely and efficiently, especially if interacting with a database.
- The `secret` field is injected from the configuration. Ensure that sensitive data like secrets are stored securely and not hardcoded.

### Scalability:
- The current implementation assumes synchronous processing. For high traffic scenarios, consider optimizing the authentication process (e.g., caching user data or using asynchronous processing).

---

## Endpoints

| **Endpoint** | **HTTP Method** | **Consumes**       | **Produces**       | **Description**                |
|--------------|-----------------|--------------------|--------------------|--------------------------------|
| `/login`     | POST            | `application/json` | `application/json` | Authenticates a user and returns a token. |

---

## Dependencies
- **Spring Boot**: Provides the framework for building the application.
- **Spring Web**: Enables RESTful web services.
- **Spring Boot Auto-Configuration**: Automatically configures the application based on dependencies.
- **Spring HTTP**: Provides HTTP status codes and response handling.

---

## Data Flow
1. **Request**:
   - A client sends a POST request to `/login` with a JSON payload containing `username` and `password`.
2. **Processing**:
   - The controller fetches the user details and validates the credentials.
3. **Response**:
   - If valid, a token is returned in the response.
   - If invalid, an HTTP 401 Unauthorized error is thrown.
