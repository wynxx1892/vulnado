# Documentation: `LinkLister.java`

## Overview

The `LinkLister` class provides functionality to extract and retrieve all hyperlinks (`<a>` tags) from a given URL. It uses the `Jsoup` library for parsing and processing HTML content. Additionally, it includes a method to validate URLs and restrict access to private IP addresses.

---

## Class: `LinkLister`

### Purpose
The `LinkLister` class is designed to:
1. Fetch all hyperlinks from a given URL.
2. Validate the URL to ensure it does not point to private IP addresses.

---

## Methods

### `getLinks(String url)`
#### Description
Fetches all hyperlinks (`<a>` tags) from the HTML content of the provided URL.

#### Parameters
- `String url`: The URL of the webpage to parse.

#### Returns
- `List<String>`: A list of absolute URLs extracted from the `<a>` tags in the HTML content.

#### Exceptions
- `IOException`: Thrown if there is an issue connecting to the URL or fetching the HTML content.

#### Logic
1. Connects to the provided URL using `Jsoup.connect(url)`.
2. Parses the HTML content into a `Document` object.
3. Selects all `<a>` elements using `doc.select("a")`.
4. Extracts the absolute URL of each `<a>` tag using `link.absUrl("href")`.
5. Returns the list of extracted URLs.

---

### `getLinksV2(String url)`
#### Description
Fetches all hyperlinks from the provided URL, but with additional validation to ensure the URL does not point to private IP addresses.

#### Parameters
- `String url`: The URL of the webpage to parse.

#### Returns
- `List<String>`: A list of absolute URLs extracted from the `<a>` tags in the HTML content.

#### Exceptions
- `BadRequest`: Custom exception thrown if:
  - The URL points to a private IP address.
  - Any other exception occurs during processing.

#### Logic
1. Parses the provided URL into a `URL` object.
2. Extracts the hostname using `aUrl.getHost()`.
3. Checks if the hostname starts with private IP address ranges:
   - `172.`
   - `192.168`
   - `10.`
4. If the hostname matches any private IP range, throws a `BadRequest` exception with the message "Use of Private IP".
5. If the hostname is valid, calls `getLinks(url)` to fetch the hyperlinks.

---

## Insights

- **Dependency on Jsoup**: The class relies on the `Jsoup` library for HTML parsing and URL extraction. Ensure the library is included in the project dependencies.
- **Private IP Validation**: The `getLinksV2` method adds a layer of security by preventing access to private IP addresses. This is useful for avoiding potential security risks when processing user-provided URLs.
- **Error Handling**: The `getLinksV2` method wraps exceptions in a custom `BadRequest` exception, providing a clear and consistent error message.
- **Scalability**: The class is designed to handle basic URL parsing and validation. For large-scale or high-performance applications, additional optimizations (e.g., connection pooling, asynchronous processing) may be required.

---

## Potential Enhancements

- **Custom Exception Handling**: Define the `BadRequest` exception class to provide more context about the error.
- **Validation for Public URLs**: Extend the validation logic to include checks for malformed or invalid URLs.
- **Timeout Configuration**: Add timeout settings for the `Jsoup.connect()` method to handle slow or unresponsive URLs.
- **Logging**: Replace `System.out.println` with a proper logging framework for better debugging and monitoring.

---

## Dependencies

| Dependency | Purpose                          |
|------------|----------------------------------|
| `Jsoup`    | HTML parsing and URL extraction |
| `java.net` | URL validation and manipulation |

---

## Limitations

- The `getLinksV2` method only validates private IP addresses based on their prefixes. It does not account for other potential security risks, such as DNS rebinding attacks.
- The class does not handle HTTPS certificate validation or other advanced security checks.
