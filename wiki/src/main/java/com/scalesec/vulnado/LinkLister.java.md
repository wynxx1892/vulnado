# Documentation: `LinkLister.java`

## Overview
The `LinkLister` class provides functionality to extract hyperlinks from a given URL. It includes methods to fetch links using the `Jsoup` library and incorporates basic validation to prevent the use of private IP addresses in one of its methods.

---

## Class: `LinkLister`

### Package
The class resides in the package `com.scalesec.vulnado`.

### Imports
The following libraries are utilized:
- **`org.jsoup`**: Used for parsing HTML and extracting elements.
- **`java.util`**: Provides utility classes such as `ArrayList` and `List`.
- **`java.io`**: Handles input/output operations, specifically `IOException`.
- **`java.net`**: Used for URL manipulation and validation.

---

## Methods

### 1. `getLinks(String url)`
#### Description
Fetches all hyperlinks (`<a>` tags) from the provided URL and returns them as a list of absolute URLs.

#### Parameters
- **`String url`**: The URL from which links are to be extracted.

#### Returns
- **`List<String>`**: A list of absolute URLs extracted from the HTML content of the provided URL.

#### Exceptions
- **`IOException`**: Thrown if there is an issue connecting to the URL or fetching its content.

#### Logic
1. Connects to the provided URL using `Jsoup.connect(url)`.
2. Parses the HTML document and selects all `<a>` elements.
3. Extracts the absolute URL (`absUrl("href")`) from each `<a>` tag and adds it to the result list.
4. Returns the list of links.

---

### 2. `getLinksV2(String url)`
#### Description
An enhanced version of `getLinks` that validates the URL to ensure it does not point to a private IP address before fetching links.

#### Parameters
- **`String url`**: The URL from which links are to be extracted.

#### Returns
- **`List<String>`**: A list of absolute URLs extracted from the HTML content of the provided URL.

#### Exceptions
- **`BadRequest`**: Thrown if the URL points to a private IP address or if any other exception occurs during processing.

#### Logic
1. Parses the URL using `java.net.URL`.
2. Extracts the host from the URL.
3. Checks if the host starts with private IP address prefixes (`172.`, `192.168`, or `10.`).
   - If true, throws a `BadRequest` exception with the message "Use of Private IP".
4. If the host is valid, calls `getLinks(url)` to fetch the links.
5. Catches any exceptions and wraps them in a `BadRequest` exception.

---

## Insights

### Security Considerations
- **Private IP Validation**: The `getLinksV2` method prevents the use of private IP addresses, which is a common security measure to avoid SSRF (Server-Side Request Forgery) attacks.
- **Exception Handling**: The method wraps exceptions in a custom `BadRequest` exception, providing a clear error message.

### Dependencies
- **Jsoup**: The library is used for HTML parsing and simplifies the process of extracting elements from web pages.
- **java.net.URL**: Used for URL validation and manipulation.

### Potential Enhancements
- **Regex Validation**: Instead of relying on `startsWith` for private IP validation, a regex-based approach could be more robust.
- **Timeout Handling**: Add timeout settings for `Jsoup.connect` to handle slow or unresponsive URLs.
- **Custom Error Messages**: Improve error messages in the `BadRequest` exception to provide more context.

### Limitations
- **No HTTPS Validation**: The methods do not check if the URL uses HTTPS, which could be a security concern.
- **Limited Private IP Detection**: The validation only checks for specific prefixes and may not cover all private IP ranges.

---

## Example Usage

### Fetching Links
```java
try {
    List<String> links = LinkLister.getLinks("https://example.com");
    links.forEach(System.out::println);
} catch (IOException e) {
    System.err.println("Error fetching links: " + e.getMessage());
}
```

### Fetching Links with Validation
```java
try {
    List<String> links = LinkLister.getLinksV2("https://example.com");
    links.forEach(System.out::println);
} catch (BadRequest e) {
    System.err.println("Invalid request: " + e.getMessage());
}
```

---

## Glossary

| Term                | Description                                                                 |
|---------------------|-----------------------------------------------------------------------------|
| **Private IP**      | IP addresses reserved for internal networks, such as `192.168.x.x`.        |
| **SSRF**            | Server-Side Request Forgery, a security vulnerability exploiting server requests. |
| **Jsoup**           | A Java library for working with real-world HTML.                           |
| **BadRequest**      | A custom exception indicating invalid input or processing errors.          |
