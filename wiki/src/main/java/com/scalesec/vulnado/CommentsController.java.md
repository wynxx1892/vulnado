# Documentation: `CommentsController.java`

## Overview
The `CommentsController` class is a REST controller designed to handle HTTP requests related to comments. It provides endpoints for retrieving, creating, and deleting comments. The controller uses Spring Boot annotations for configuration and integrates authentication mechanisms via request headers.

---

## Class: `CommentsController`

### Annotations
- **`@RestController`**: Indicates that this class is a REST controller.
- **`@EnableAutoConfiguration`**: Enables Spring Boot's auto-configuration feature.
- **`@CrossOrigin(origins = "*")`**: Allows cross-origin requests from any domain.

### Fields
| Field Name | Type   | Description                          |
|------------|--------|--------------------------------------|
| `secret`   | String | A private field used for authentication. |

---

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
     - **Body**: `CommentRequest` object containing:
       - `username` (String): The username of the commenter.
       - `body` (String): The content of the comment.
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
A data structure representing the request body for creating a comment.

### Fields
| Field Name | Type   | Description                          |
|------------|--------|--------------------------------------|
| `username` | String | The username of the commenter.       |
| `body`     | String | The content of the comment.          |

### Implements
- **`Serializable`**: Ensures that instances of this class can be serialized.

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

1. **Authentication**: The controller uses a custom authentication mechanism via the `x-auth-token` header and a `secret` field. This approach may need enhancement for scalability and security, such as integrating OAuth or JWT.
2. **Cross-Origin Resource Sharing (CORS)**: The `@CrossOrigin(origins = "*")` annotation allows requests from any domain, which could pose security risks. Consider restricting origins to trusted domains.
3. **Error Handling**: Custom exceptions (`BadRequest` and `ServerError`) are mapped to specific HTTP status codes, ensuring clear communication of errors to clients.
4. **Data Structure**: The `CommentRequest` class is a simple POJO (Plain Old Java Object) used for deserializing request bodies. It is lightweight and adheres to the `Serializable` interface for potential persistence or transmission.
5. **Scalability**: The current implementation assumes synchronous operations. For high traffic, consider asynchronous processing or caching mechanisms for fetching comments.

---
