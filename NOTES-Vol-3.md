# Spring Boot Learning Notes-Vol-3

## 1. Java Records

Java Records (standard in Java 16) are a concise way to create classes that act as transparent and immutable carriers for data.

### A. Core Features & Benefits

- **Conciseness:** Drastically reduces boilerplate code. When you declare a record with its components, the compiler automatically generates:
  - `private final` fields for each component.
  - A canonical constructor (taking all components as arguments).
  - Public accessor methods for each component (e.g., `componentName()`).
  - `equals()`, `hashCode()`, and `toString()` methods based on all components.
- **Immutability:** Record instances are immutable by default because their fields are `private final`. Once created, the state of a record cannot be changed.
- **Data-Focused:** Designed primarily to hold data.

**Example: Traditional Class vs. Record**

_Traditional POJO (Plain Old Java Object):_

```java
// public class Order {
//   private String customerName;
//   private String productName;
//   private int quantity;
//   // Plus many lines for constructor, getters, setters, equals, hashCode, toString
// }
```

_Equivalent Record:_

```java

// import com.fasterxml.jackson.annotation.JsonProperty;

// public record OrderRecord(
//     @JsonProperty("c-name") String customerName,
//     @JsonProperty("p-name") String productName,
//     @JsonProperty("quantity") int quantity
// ) {}
// Annotations like @JsonProperty can be applied directly to record components.
```

### Summary

Records offer a concise and safe way to model data carriers. Their immutability is a key benefit. In Spring Boot, they shine as DTOs for APIs, for configuration properties, and any time an immutable data structure is needed. They complement, rather than replace, traditional mutable classes used for entities managed by ORMs.

## @PathVariable vs @RequestParam

Both are ways to get information from the URL into your Spring Boot controller method, but they look for that information in different parts of the URL.

#### @PathVariable

Gets data from the actual path of the URL. It's for when a piece of data is part of the URL's structure, like an ID or a name directly in the path:

```java
// Expects a URL like: /hello/someValueHere
@GetMapping("/hello/{user-name}")
public String pathVar(
@PathVariable("user-name") String userName) {
return "my value = " + userName;
}
```

#### @RequestParam

Gets data from the query parameters of the URL. These are the key-value pairs that come after the question mark (?) in a URL.

```java
// Expects a URL like: /hello?user-name=value1&user-lastname=value2
@GetMapping("/hello")
public String paramVar(
    @RequestParam("user-name") String userName,
    @RequestParam("user-lastname") String userLastName) {
  return "my value = " + userName + " " + userLastName;
}
```
