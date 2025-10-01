# Documentation: `CommentsController.java`

## Overview

The `CommentsController` class is a REST controller in a Spring Boot application that manages operations related to comments. It provides endpoints for retrieving, creating, and deleting comments. The controller includes authentication mechanisms and handles exceptions for bad requests and server errors.

---

## Class: `CommentsController`

### Annotations
- `@RestController`: Indicates that this class is a REST controller.
- `@EnableAutoConfiguration`: Enables Spring Boot's auto-configuration feature.
- `@CrossOrigin(origins = "*")`: Allows cross-origin requests from any origin.

### Fields
- `@Value("${app.secret}") private String secret`: Injects the application secret from the configuration file for authentication purposes.

### Endpoints

#### 1. **GET `/comments`**
   - **Description**: Retrieves a list of all comments.
   - **Method**: `GET`
   - **Produces**: `application/json`
   - **Parameters**:
     - `@RequestHeader(value = "x-auth-token") String token`: The authentication token provided in the request header.
   - **Returns**: A `List<Comment>` containing all comments.
   - **Logic**:
     - Authenticates the user using the `User.assertAuth(secret, token)` method.
     - Fetches all comments using `Comment.fetchall()`.

#### 2. **POST `/comments`**
   - **Description**: Creates a new comment.
   - **Method**: `POST`
   - **Produces**: `application/json`
   - **Consumes**: `application/json`
   - **Parameters**:
     - `@RequestHeader(value = "x-auth-token") String token`: The authentication token provided in the request header.
     - `@RequestBody CommentRequest input`: The request body containing the comment details.
   - **Returns**: The created `Comment` object.
   - **Logic**:
     - Authenticates the user using the `User.assertAuth(secret, token)` method.
     - Creates a new comment using `Comment.create(input.username, input.body)`.

#### 3. **DELETE `/comments/{id}`**
   - **Description**: Deletes a comment by its ID.
   - **Method**: `DELETE`
   - **Produces**: `application/json`
   - **Parameters**:
     - `@RequestHeader(value = "x-auth-token") String token`: The authentication token provided in the request header.
     - `@PathVariable String id`: The ID of the comment to be deleted.
   - **Returns**: A `Boolean` indicating whether the deletion was successful.
   - **Logic**:
     - Authenticates the user using the `User.assertAuth(secret, token)` method.
     - Deletes the comment using `Comment.delete(id)`.

---

## Supporting Classes

### 1. **`CommentRequest`**
   - **Description**: A data structure representing the request body for creating a comment.
   - **Implements**: `Serializable`
   - **Fields**:
     - `public String username`: The username of the comment author.
     - `public String body`: The content of the comment.

### 2. **`BadRequest`**
   - **Description**: A custom exception for handling bad requests.
   - **Annotations**:
     - `@ResponseStatus(HttpStatus.BAD_REQUEST)`: Maps this exception to a `400 Bad Request` HTTP status.
   - **Constructor**:
     - `public BadRequest(String exception)`: Initializes the exception with a custom message.

### 3. **`ServerError`**
   - **Description**: A custom exception for handling server errors.
   - **Annotations**:
     - `@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)`: Maps this exception to a `500 Internal Server Error` HTTP status.
   - **Constructor**:
     - `public ServerError(String exception)`: Initializes the exception with a custom message.

---

## Insights

- **Authentication**: The controller relies on a token-based authentication mechanism. The `User.assertAuth(secret, token)` method is used to validate the token against the application secret.
- **Cross-Origin Resource Sharing (CORS)**: The `@CrossOrigin(origins = "*")` annotation allows requests from any origin, which may pose a security risk if not properly configured.
- **Error Handling**: Custom exceptions (`BadRequest` and `ServerError`) are used to handle specific error scenarios, ensuring meaningful HTTP responses.
- **Comment Management**: The `Comment` class is assumed to handle the actual data operations (e.g., fetching, creating, and deleting comments). However, its implementation is not provided in this snippet.
- **Scalability**: The controller is designed to handle basic CRUD operations for comments. Additional features like pagination, filtering, or sorting could be added for better scalability.
