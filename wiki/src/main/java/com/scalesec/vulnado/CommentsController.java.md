# Documentation: `CommentsController.java`

## Overview
The `CommentsController` class is a REST controller implemented using Spring Boot. It provides endpoints for managing comments, including fetching, creating, and deleting comments. The controller includes authentication mechanisms and handles exceptions for bad requests and server errors.

---

## Class: `CommentsController`

### Annotations
- **`@RestController`**: Indicates that this class is a REST controller.
- **`@EnableAutoConfiguration`**: Enables Spring Boot's auto-configuration feature.
- **`@CrossOrigin(origins = "*")`**: Allows cross-origin requests from any origin.

### Fields
| Field Name | Type   | Description                          |
|------------|--------|--------------------------------------|
| `secret`   | String | A private field used for authentication. |

### Endpoints

#### 1. **GET `/comments`**
Fetches all comments.

- **Annotations**:
  - `@RequestMapping(value = "/comments", method = RequestMethod.GET, produces = "application/json")`
  - `@RequestHeader(value = "x-auth-token")`: Requires an authentication token in the request header.

- **Parameters**:
  - `String token`: The authentication token.

- **Returns**:
  - `List<Comment>`: A list of all comments.

- **Logic**:
  - Validates the authentication token using `User.assertAuth(secret, token)`.
  - Fetches all comments using `Comment.fetchall()`.

---

#### 2. **POST `/comments`**
Creates a new comment.

- **Annotations**:
  - `@RequestMapping(value = "/comments", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")`
  - `@RequestHeader(value = "x-auth-token")`: Requires an authentication token in the request header.
  - `@RequestBody`: Accepts a JSON payload for the comment.

- **Parameters**:
  - `String token`: The authentication token.
  - `CommentRequest input`: The request body containing the comment details.

- **Returns**:
  - `Comment`: The created comment.

- **Logic**:
  - Validates the authentication token using `User.assertAuth(secret, token)`.
  - Creates a new comment using `Comment.create(input.username, input.body)`.

---

#### 3. **DELETE `/comments/{id}`**
Deletes a comment by its ID.

- **Annotations**:
  - `@RequestMapping(value = "/comments/{id}", method = RequestMethod.DELETE, produces = "application/json")`
  - `@RequestHeader(value = "x-auth-token")`: Requires an authentication token in the request header.
  - `@PathVariable`: Extracts the comment ID from the URL.

- **Parameters**:
  - `String token`: The authentication token.
  - `String id`: The ID of the comment to delete.

- **Returns**:
  - `Boolean`: `true` if the comment was successfully deleted, `false` otherwise.

- **Logic**:
  - Validates the authentication token using `User.assertAuth(secret, token)`.
  - Deletes the comment using `Comment.delete(id)`.

---

## Class: `CommentRequest`

### Description
A data structure representing the request body for creating a comment.

### Fields
| Field Name | Type   | Description                  |
|------------|--------|------------------------------|
| `username` | String | The username of the commenter. |
| `body`     | String | The content of the comment.   |

### Implements
- **`Serializable`**: Ensures that instances of this class can be serialized.

---

## Exception Handling

### Class: `BadRequest`
- **Annotation**: `@ResponseStatus(HttpStatus.BAD_REQUEST)`
- **Description**: Represents a bad request error.
- **Constructor**:
  - `BadRequest(String exception)`: Initializes the exception with a custom message.

---

### Class: `ServerError`
- **Annotation**: `@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)`
- **Description**: Represents an internal server error.
- **Constructor**:
  - `ServerError(String exception)`: Initializes the exception with a custom message.

---

## Insights

1. **Authentication**:
   - The controller uses a custom authentication mechanism (`User.assertAuth(secret, token)`) to validate requests. This approach may need to be replaced with a more robust solution like OAuth2 or JWT for production-grade security.

2. **Cross-Origin Resource Sharing (CORS)**:
   - The `@CrossOrigin(origins = "*")` annotation allows requests from any origin. While convenient for development, this configuration should be restricted in production to prevent security vulnerabilities.

3. **Error Handling**:
   - Custom exceptions (`BadRequest` and `ServerError`) are used to handle specific error scenarios. These exceptions are mapped to HTTP status codes for better client-side error handling.

4. **Scalability**:
   - The current implementation fetches all comments using `Comment.fetchall()`. For large datasets, pagination or filtering should be implemented to improve performance.

5. **Data Validation**:
   - The `CommentRequest` class does not include validation annotations (e.g., `@NotNull`, `@Size`). Adding validation would ensure data integrity and prevent invalid inputs.

6. **Code Organization**:
   - The controller mixes logic and data structure definitions (`CommentRequest`) in the same file. Separating these into different files would improve maintainability.
