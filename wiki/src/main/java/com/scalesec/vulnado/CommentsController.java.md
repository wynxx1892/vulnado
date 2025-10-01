# Documentation: `CommentsController.java`

## Overview
The `CommentsController` class is a REST controller designed to handle HTTP requests related to comments. It provides endpoints for retrieving, creating, and deleting comments. The controller uses Spring Boot annotations for configuration and integrates authentication mechanisms via request headers.

---

## Class: `CommentsController`

### Annotations
- **`@RestController`**: Marks the class as a REST controller, enabling it to handle HTTP requests and return JSON responses.
- **`@EnableAutoConfiguration`**: Enables Spring Boot's auto-configuration feature.
- **`@CrossOrigin(origins = "*")`**: Allows cross-origin requests from any domain.

### Fields
| Field Name | Type   | Description                          |
|------------|--------|--------------------------------------|
| `secret`   | String | A private application secret used for authentication. |

### Endpoints

#### 1. **GET `/comments`**
   - **Description**: Retrieves a list of all comments.
   - **Method**: `GET`
   - **Produces**: `application/json`
   - **Parameters**:
     - **Header**: `x-auth-token` (String) - Token used for authentication.
   - **Response**: Returns a list of `Comment` objects.
   - **Logic**:
     - Validates the `x-auth-token` using the `User.assertAuth` method.
     - Fetches all comments using `Comment.fetchall`.

#### 2. **POST `/comments`**
   - **Description**: Creates a new comment.
   - **Method**: `POST`
   - **Produces**: `application/json`
   - **Consumes**: `application/json`
   - **Parameters**:
     - **Header**: `x-auth-token` (String) - Token used for authentication.
     - **Body**: `CommentRequest` (JSON) - Contains `username` and `body` fields for the new comment.
   - **Response**: Returns the created `Comment` object.
   - **Logic**:
     - Validates the `x-auth-token` using the `User.assertAuth` method.
     - Creates a new comment using `Comment.create`.

#### 3. **DELETE `/comments/{id}`**
   - **Description**: Deletes a comment by its ID.
   - **Method**: `DELETE`
   - **Produces**: `application/json`
   - **Parameters**:
     - **Header**: `x-auth-token` (String) - Token used for authentication.
     - **Path Variable**: `id` (String) - The ID of the comment to delete.
   - **Response**: Returns a boolean indicating success or failure.
   - **Logic**:
     - Validates the `x-auth-token` using the `User.assertAuth` method.
     - Deletes the comment using `Comment.delete`.

---

## Class: `CommentRequest`

### Description
A data structure representing the request body for creating a new comment.

### Fields
| Field Name | Type   | Description                     |
|------------|--------|---------------------------------|
| `username` | String | The username of the commenter. |
| `body`     | String | The content of the comment.    |

### Implements
- **`Serializable`**: Ensures the class can be serialized for transmission.

---

## Exception Handling

### Class: `BadRequest`
- **Annotation**: `@ResponseStatus(HttpStatus.BAD_REQUEST)`
- **Description**: Represents a `400 Bad Request` error.
- **Constructor**: Accepts a string message describing the exception.

### Class: `ServerError`
- **Annotation**: `@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)`
- **Description**: Represents a `500 Internal Server Error`.
- **Constructor**: Accepts a string message describing the exception.

---

## Insights

1. **Authentication**:
   - The controller relies on a custom authentication mechanism using the `x-auth-token` header and a secret key (`app.secret`).
   - The `User.assertAuth` method is used to validate the token.

2. **Cross-Origin Resource Sharing (CORS)**:
   - The `@CrossOrigin(origins = "*")` annotation allows requests from any domain, which may pose security risks if not properly managed.

3. **Error Handling**:
   - Custom exceptions (`BadRequest` and `ServerError`) are used to handle specific error scenarios, improving the clarity of error responses.

4. **Data Structure**:
   - The `CommentRequest` class is a simple data structure for handling input data during comment creation.

5. **Potential Improvements**:
   - The `secret` field could be better secured, e.g., by using environment variables or a secure vault.
   - The `@CrossOrigin` annotation should ideally restrict origins to trusted domains for enhanced security.

6. **Dependencies**:
   - The code relies on Spring Boot annotations and features for REST API development and auto-configuration.

---
