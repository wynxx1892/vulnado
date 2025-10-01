# Documentation: `LinkLister.java`

## Overview
The `LinkLister` class provides functionality to extract hyperlinks from a given URL. It includes methods to fetch links from a webpage and validate the URL against private IP ranges to prevent misuse. The class leverages the `Jsoup` library for HTML parsing and includes basic error handling.

---

## Class: `LinkLister`

### Purpose
The `LinkLister` class is designed to:
1. Extract all hyperlinks (`<a>` tags) from a webpage.
2. Validate URLs to ensure they do not point to private IP addresses.

---

## Methods

### `getLinks(String url)`
#### Description
Fetches all hyperlinks from the given URL and returns them as a list of strings.

#### Parameters
- `String url`: The URL of the webpage to extract links from.

#### Returns
- `List<String>`: A list of absolute URLs extracted from the webpage.

#### Exceptions
- `IOException`: Thrown if there is an issue connecting to the URL or parsing the webpage.

#### Logic
1. Connects to the provided URL using `Jsoup.connect(url)`.
2. Parses the HTML document and selects all `<a>` elements.
3. Extracts the absolute URL (`absUrl("href")`) from each `<a>` tag.
4. Adds the URLs to a list and returns the list.

---

### `getLinksV2(String url)`
#### Description
Fetches all hyperlinks from the given URL, but first validates the URL to ensure it does not point to a private IP address.

#### Parameters
- `String url`: The URL of the webpage to extract links from.

#### Returns
- `List<String>`: A list of absolute URLs extracted from the webpage.

#### Exceptions
- `BadRequest`: Custom exception thrown if the URL points to a private IP address or if any other error occurs during processing.

#### Logic
1. Parses the URL using `java.net.URL`.
2. Extracts the host from the URL.
3. Checks if the host starts with private IP ranges (`172.`, `192.168`, or `10.`).
   - If the host matches a private IP range, throws a `BadRequest` exception with the message "Use of Private IP".
   - Otherwise, calls `getLinks(url)` to fetch the links.
4. Handles any other exceptions by throwing a `BadRequest` exception with the error message.

---

## Insights

### Dependencies
- **Jsoup Library**: Used for HTML parsing and extracting elements from the webpage.
- **java.net.URL**: Used for URL validation and host extraction.

### Security Considerations
- The `getLinksV2` method includes a validation step to prevent fetching links from private IP addresses. This is useful for avoiding potential security risks such as SSRF (Server-Side Request Forgery).

### Error Handling
- The `getLinks` method relies on `IOException` for error handling during URL connection and parsing.
- The `getLinksV2` method introduces a custom exception (`BadRequest`) to handle invalid URLs and other errors.

### Potential Enhancements
- **Custom Exception Handling**: The `BadRequest` exception could be further refined to include error codes or additional context.
- **Private IP Validation**: The validation logic could be extended to include other non-routable IP ranges (e.g., `127.0.0.1` for localhost).
- **Timeout Configuration**: Add timeout settings for `Jsoup.connect()` to handle slow or unresponsive URLs.

### Limitations
- The `getLinks` method does not validate the URL or its host, which could lead to security vulnerabilities if used without `getLinksV2`.
- The class does not handle relative URLs explicitly; it assumes all links are absolute.

---

## Example Usage

### Extracting Links
```java
try {
    List<String> links = LinkLister.getLinks("https://example.com");
    for (String link : links) {
        System.out.println(link);
    }
} catch (IOException e) {
    System.err.println("Error fetching links: " + e.getMessage());
}
```

### Validating and Extracting Links
```java
try {
    List<String> links = LinkLister.getLinksV2("https://example.com");
    for (String link : links) {
        System.out.println(link);
    }
} catch (BadRequest e) {
    System.err.println("Invalid URL: " + e.getMessage());
}
```

---

## Key Classes and Methods Used

| Class/Method         | Purpose                                                                 |
|-----------------------|-------------------------------------------------------------------------|
| `Jsoup.connect(url)`  | Connects to the URL and fetches the HTML document.                     |
| `Document.select()`   | Selects elements from the HTML document using CSS-like selectors.      |
| `Element.absUrl()`    | Extracts the absolute URL from an HTML element.                        |
| `java.net.URL`        | Parses the URL and provides access to its components (e.g., host).     |
| `BadRequest`          | Custom exception for handling invalid URLs or other errors.           |

---
