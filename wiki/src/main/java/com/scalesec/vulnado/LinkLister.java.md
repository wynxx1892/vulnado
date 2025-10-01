# Documentation: `LinkLister.java`

## Overview
The `LinkLister` class provides functionality to extract hyperlinks from a given URL. It uses the `Jsoup` library for HTML parsing and includes methods to retrieve links with additional validation for private IP addresses.

---

## Class: `LinkLister`

### Purpose
The class is designed to fetch and process hyperlinks from web pages. It includes two methods:
1. `getLinks`: Extracts all hyperlinks from a given URL.
2. `getLinksV2`: Extracts hyperlinks while validating that the URL does not point to a private IP address.

---

## Methods

### `getLinks(String url)`
#### Description
Fetches all hyperlinks (`<a>` tags) from the provided URL and returns them as a list of absolute URLs.

#### Parameters
- `url` (String): The URL of the web page to parse.

#### Returns
- `List<String>`: A list of absolute URLs extracted from the web page.

#### Exceptions
- `IOException`: Thrown if there is an issue connecting to the URL or parsing the document.

#### Logic
1. Connects to the URL using `Jsoup.connect(url)`.
2. Parses the HTML document.
3. Selects all `<a>` elements using `doc.select("a")`.
4. Extracts the absolute URL of each `<a>` tag using `link.absUrl("href")`.
5. Adds the URLs to a list and returns it.

---

### `getLinksV2(String url)`
#### Description
Fetches hyperlinks from the provided URL while ensuring the URL does not point to a private IP address.

#### Parameters
- `url` (String): The URL of the web page to parse.

#### Returns
- `List<String>`: A list of absolute URLs extracted from the web page.

#### Exceptions
- `BadRequest`: Custom exception thrown if the URL points to a private IP address or if any other error occurs.

#### Logic
1. Parses the URL using `new URL(url)`.
2. Extracts the host using `aUrl.getHost()`.
3. Checks if the host starts with private IP address prefixes (`172.`, `192.168`, or `10.`).
   - If true, throws a `BadRequest` exception with the message "Use of Private IP".
4. If the host is valid, calls `getLinks(url)` to fetch the links.
5. Catches any exceptions and wraps them in a `BadRequest` exception.

---

## Insights

### Dependencies
- **Jsoup Library**: Used for HTML parsing and extracting elements.
- **Java Networking**: Utilizes `java.net.URL` for URL validation and host extraction.

### Validation in `getLinksV2`
The method ensures security by rejecting URLs pointing to private IP addresses. This is useful in scenarios where accessing internal networks is restricted.

### Exception Handling
- `getLinks` relies on `IOException` for error handling.
- `getLinksV2` introduces a custom exception (`BadRequest`) to provide more specific error messages.

### Potential Enhancements
1. **Custom Exception Definition**: The `BadRequest` exception is referenced but not defined in the provided code. Ensure it is implemented elsewhere in the project.
2. **Private IP Validation**: The validation logic could be extended to include additional private IP ranges or edge cases.
3. **Error Logging**: Add logging for better debugging and monitoring.

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
    System.err.println("Invalid URL: " + e.getMessage());
}
```

---

## Notes
- Ensure the `BadRequest` exception is properly implemented and imported.
- The `getLinksV2` method is more secure and should be preferred in environments where private IP access is restricted.
