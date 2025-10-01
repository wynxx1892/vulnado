# Documentation: `Comment.java`

## Overview
The `Comment` class is part of the `com.scalesec.vulnado` package and provides functionality for managing comments in a database. It includes methods for creating, fetching, and deleting comments, as well as committing them to the database. The class interacts with a PostgreSQL database using JDBC.

---

## Class Details

### **Class Name**: `Comment`

### **Attributes**
| Attribute Name | Type         | Description                                      |
|----------------|--------------|--------------------------------------------------|
| `id`           | `String`     | Unique identifier for the comment.              |
| `username`     | `String`     | Username of the person who created the comment. |
| `body`         | `String`     | Content of the comment.                         |
| `createdon`    | `Timestamp`  | Timestamp indicating when the comment was created. |

---

## Constructor

### **Constructor**: `Comment(String id, String username, String body, Timestamp createdon)`
Initializes a new `Comment` object with the provided attributes.

#### Parameters:
- `id`: Unique identifier for the comment.
- `username`: Username of the person who created the comment.
- `body`: Content of the comment.
- `createdon`: Timestamp indicating when the comment was created.

---

## Methods

### **Static Method**: `create(String username, String body)`
Creates a new `Comment` object and attempts to save it to the database.

#### Parameters:
- `username`: Username of the person creating the comment.
- `body`: Content of the comment.

#### Returns:
- `Comment`: The created `Comment` object if successfully saved.

#### Exceptions:
- Throws `BadRequest` if unable to save the comment.
- Throws `ServerError` if an unexpected error occurs.

---

### **Static Method**: `fetchAll()`
Fetches all comments from the database.

#### Returns:
- `List<Comment>`: A list of all comments retrieved from the database.

#### Exceptions:
- Prints stack trace and error message if an exception occurs.

---

### **Static Method**: `delete(String id)`
Deletes a comment from the database based on its unique identifier.

#### Parameters:
- `id`: The unique identifier of the comment to be deleted.

#### Returns:
- `Boolean`: `true` if the comment was successfully deleted, `false` otherwise.

#### Exceptions:
- Prints stack trace if an exception occurs.

---

### **Private Method**: `commit()`
Commits the current `Comment` object to the database.

#### Returns:
- `Boolean`: `true` if the comment was successfully committed, `false` otherwise.

#### Exceptions:
- Throws `SQLException` if an error occurs during the database operation.

---

## Insights

### **Database Interaction**
- The class uses JDBC to interact with a PostgreSQL database.
- SQL queries are used for CRUD operations (`INSERT`, `SELECT`, `DELETE`).

### **Error Handling**
- Exceptions are caught and either rethrown or logged using `printStackTrace`.
- Custom exceptions like `BadRequest` and `ServerError` are used for specific error scenarios.

### **UUID for Comment IDs**
- The `id` attribute is generated using `UUID.randomUUID()` to ensure uniqueness.

### **Potential Issues**
1. **SQL Injection Risk**: The `fetchAll` method constructs SQL queries directly, which could be vulnerable to SQL injection. Prepared statements should be used instead.
2. **Error Logging**: The error handling mechanism relies on `printStackTrace`, which is not ideal for production environments. A proper logging framework should be used.
3. **Connection Management**: The database connection is closed in the `try` block, but not in the `finally` block, which could lead to resource leaks in case of exceptions.

### **Improvements**
- Use a connection pool to manage database connections efficiently.
- Replace direct SQL query construction with prepared statements for all methods.
- Implement a logging framework for better error tracking and debugging.

---

## Dependencies
- **JDBC**: For database interaction.
- **Postgres.connection**: Custom utility for obtaining database connections.
- **UUID**: For generating unique identifiers.
- **Date**: For timestamp generation.

---

## File Metadata
| Key         | Value          |
|-------------|----------------|
| **File Name** | `Comment.java` |
