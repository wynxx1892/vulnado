# Documentation: `CommentsController.java`

## Overview
The `CommentsController` class is a REST controller in a Spring Boot application that manages comments. It provides endpoints for retrieving, creating, and deleting comments. The controller includes authentication mechanisms and handles exceptions for bad requests and server errors.

---

## Class: `CommentsController`

### Annotations
- `@RestController`: Indicates that this class is a REST controller.
- `@EnableAutoConfiguration`: Enables Spring Boot's auto-configuration feature.
- `@CrossOrigin(origins = "*")`: Allows cross-origin requests from any origin.

### Fields
| Field Name | Type   | Description                          |
|------------|--------|--------------------------------------|
| `secret`   | String | A secret key used for authentication.|

---

### Endpoints

#### 1. **GET `/comments`**
   - **Description**: Retrieves a list of all comments.
   - **Method**: `RequestMethod.GET`
   - **Produces**: `application/json`
   - **Parameters**:
     - `@RequestHeader(value = "x-auth-token") String token`: Authentication token.
   - **Returns**: `List<Comment>` - A list of comments.
   - **Logic**:
     - Validates the authentication token using `User.assertAuth(secret, token)`.
     - Fetches all comments using `Comment.fetchall()`.

#### 2. **POST `/comments`**
   - **Description**: Creates a new comment.
   - **Method**: `RequestMethod.POST`
   - **Produces**: `application/json`
   - **Consumes**: `application/json`
   - **Parameters**:
     - `@RequestHeader(value = "x-auth-token") String token`: Authentication token.
     - `@RequestBody CommentRequest input`: The request body containing the comment details.
   - **Returns**: `Comment` - The created comment.
   - **Logic**:
     - Validates the authentication token using `User.assertAuth(secret, token)`.
     - Creates a new comment using `Comment.create(input.username, input.body)`.

#### 3. **DELETE `/comments/{id}`**
   - **Description**: Deletes a comment by its ID.
   - **Method**: `RequestMethod.DELETE`
   - **Produces**: `application/json`
   - **Parameters**:
     - `@RequestHeader(value = "x-auth-token") String token`: Authentication token.
     - `@PathVariable String id`: The ID of the comment to delete.
   - **Returns**: `Boolean` - `true` if the comment was successfully deleted, otherwise `false`.
   - **Logic**:
     - Validates the authentication token using `User.assertAuth(secret, token)`.
     - Deletes the comment using `Comment.delete(id)`.

---

## Class: `CommentRequest`

### Description
A data structure representing the request body for creating a comment.

### Fields
| Field Name | Type   | Description                     |
|------------|--------|---------------------------------|
| `username` | String | The username of the commenter. |
| `body`     | String | The content of the comment.    |

### Implements
- `Serializable`: Ensures the class can be serialized.

---

## Exception Handling

### Class: `BadRequest`
- **Annotation**: `@ResponseStatus(HttpStatus.BAD_REQUEST)`
- **Description**: Represents a bad request error.
- **Constructor**:
  - `BadRequest(String exception)`: Initializes the exception with a message.

### Class: `ServerError`
- **Annotation**: `@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)`
- **Description**: Represents an internal server error.
- **Constructor**:
  - `ServerError(String exception)`: Initializes the exception with a message.

---

## Insights

1. **Authentication**:
   - The controller uses a custom authentication mechanism (`User.assertAuth(secret, token)`) to validate requests. This approach requires the `secret` field to be securely managed.

2. **Cross-Origin Requests**:
   - The `@CrossOrigin(origins = "*")` annotation allows requests from any origin, which may pose security risks if not properly configured.

3. **Error Handling**:
   - The application defines custom exceptions (`BadRequest` and `ServerError`) with appropriate HTTP status codes, ensuring clear communication of errors to clients.

4. **Data Structure**:
   - The `CommentRequest` class is a simple data structure for handling input data when creating comments. It is designed to be serialized for easy transmission.

5. **Scalability**:
   - The use of `Comment.fetchall()` and `Comment.create()` suggests that the `Comment` class handles database operations. This design can be extended for additional features like pagination or filtering.

6. **Security Considerations**:
   - The reliance on a single `secret` for authentication may not be sufficient for complex applications. Consider implementing more robust authentication mechanisms, such as OAuth or JWT.

---
