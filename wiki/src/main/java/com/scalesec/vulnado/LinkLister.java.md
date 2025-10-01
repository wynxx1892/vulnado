# Documentation: `LinkLister` Class

## Overview
The `LinkLister` class provides functionality to extract hyperlinks from a given URL. It uses the `Jsoup` library to parse HTML content and retrieve links. Additionally, it includes a method to validate URLs and restrict access to private IP addresses.

---

## Class: `LinkLister`

### Methods

#### 1. `getLinks(String url)`
**Description**:  
Fetches all hyperlinks (`<a>` tags) from the HTML content of the specified URL.

**Parameters**:  
- `url` (String): The URL of the webpage to parse.

**Returns**:  
- `List<String>`: A list of absolute URLs extracted from the webpage.

**Throws**:  
- `IOException`: If there is an error connecting to the URL or parsing the content.

**Logic**:
1. Connects to the provided URL using `Jsoup.connect(url)`.
2. Parses the HTML document.
3. Selects all `<a>` elements using `doc.select("a")`.
4. Extracts the absolute URL of each link using `link.absUrl("href")`.
5. Returns the list of extracted URLs.

---

#### 2. `getLinksV2(String url)`
**Description**:  
Fetches all hyperlinks from the specified URL, but with additional validation to prevent access to private IP addresses.

**Parameters**:  
- `url` (String): The URL of the webpage to parse.

**Returns**:  
- `List<String>`: A list of absolute URLs extracted from the webpage.

**Throws**:  
- `BadRequest`: If the URL points to a private IP address or if any other exception occurs during processing.

**Logic**:
1. Parses the provided URL using `new URL(url)`.
2. Extracts the host part of the URL using `aUrl.getHost()`.
3. Checks if the host starts with private IP address ranges:
   - `172.`
   - `192.168`
   - `10.`
4. If the host matches any private IP range, throws a `BadRequest` exception with the message "Use of Private IP".
5. If the host is valid, calls `getLinks(url)` to fetch the links.
6. Catches any exceptions and rethrows them as `BadRequest` with the exception message.

---

## Insights

### Dependencies
- **Jsoup Library**: Used for HTML parsing and extracting elements from the document.
- **Java Networking**: The `URL` class is used to parse and validate the host of the provided URL.

### Security Considerations
- The `getLinksV2` method includes a security feature to prevent access to private IP addresses. This is useful in scenarios where the application should not interact with internal network resources.

### Error Handling
- The `getLinks` method throws `IOException` for connection or parsing errors.
- The `getLinksV2` method wraps exceptions in a custom `BadRequest` exception, providing a more user-friendly error message.

### Potential Enhancements
- **Validation**: Add support for validating URLs against additional criteria (e.g., specific domains or protocols).
- **Error Messages**: Provide more detailed error messages for different types of exceptions.
- **Concurrency**: Consider adding support for asynchronous or parallel processing of multiple URLs.

---

## Example Usage

```java
public class Main {
    public static void main(String[] args) {
        try {
            // Example 1: Fetch links from a public URL
            List<String> links = LinkLister.getLinks("https://example.com");
            System.out.println("Links: " + links);

            // Example 2: Fetch links with private IP validation
            List<String> validatedLinks = LinkLister.getLinksV2("https://example.com");
            System.out.println("Validated Links: " + validatedLinks);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
```

---

## Notes
- The `BadRequest` exception class is assumed to be defined elsewhere in the codebase.
- The `getLinksV2` method relies on the `getLinks` method for actual link extraction after validation.
