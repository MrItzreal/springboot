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

Both annotations are used in Spring MVC controllers to extract data from the incoming HTTP request's URL, but they target different parts of the URL.

### A. `@PathVariable`

- **Purpose:** Extracts values directly from the URI path itself. Used when part of the URL path acts as a parameter.
- **URL Structure:** The variable is embedded within the path, defined by curly braces `{}` in the `@GetMapping` (or other mapping) annotation.
  - Example URL: `/resource/{id}` or `/items/{category}/{itemId}`
- **Annotation Usage:**
  ```java
  // Maps requests like GET /hello/Izzy
  @GetMapping("/hello/{user-name}")
  public String greetUser(
      @PathVariable("user-name") String userName) {
    // Here, userName will be "Izzy"
    return "Hello, " + userName;
  }
  ```
- **Key Idea:** The value is a mandatory part of the path structure needed to identify the resource or endpoint. If the method parameter name matches the placeholder name, `("user-name")` can be omitted (`@PathVariable String userName`).

### B. `@RequestParam`

- **Purpose:** Extracts values from the query parameters of a URL (the key-value pairs that appear after the `?`).
- **URL Structure:** Parameters are appended to the path after a `?`, separated by `&`.
  - Example URL: `/search?query=spring&page=1` or `/products?category=electronics&sort=price_asc`
- **Annotation Usage:**
  ```java
  // Maps requests like GET /greet?name=John&greeting=Hi
  @GetMapping("/greet")
  public String greetWithParams(
      @RequestParam("name") String personName,
      @RequestParam(name = "greeting", required = false, defaultValue = "Hello") String greetingMessage) {
    // For /greet?name=Jane, personName is "Jane", greetingMessage is "Hello"
    // For /greet?name=Jane&greeting=Hola, personName is "Jane", greetingMessage is "Hola"
    return greetingMessage + ", " + personName + "!";
  }
  ```
- **Key Idea:** Used for optional parameters, filtering, sorting, pagination, etc.
  - `required = false`: Makes the parameter optional.
  - `defaultValue`: Provides a default value if the parameter is not present in the URL.

### C. `@PathVariable` vs. `@RequestParam` Summary

| Feature            | `@PathVariable`                                     | `@RequestParam`                                |
| :----------------- | :-------------------------------------------------- | :--------------------------------------------- |
| **Data Source**    | From the URI path (e.g., `/users/{id}`)             | From query parameters (e.g., `/search?q=term`) |
| **URL Looks Like** | `/entity/{entityId}`                                | `/entity/search?field=value&sort=asc`          |
| **Common Use**     | Identifying specific resources, mandatory segments. | Filtering, pagination, sorting, optional data. |

## 2. Setting Up a PostgreSQL Database with Docker for Development

Using Docker to run a database locally provides an isolated and easily reproducible development environment.

- **Command to Run PostgreSQL Container:**
  ```bash
  docker run --name some-postgres -e POSTGRES_PASSWORD=mysecretpassword -p 5432:5432 -d postgres
  ```
  - `docker run`: Starts a new container.
  - `--name some-postgres`: Assigns a custom name to the container for easier management.
  - `-e POSTGRES_PASSWORD=mysecretpassword`: Sets the password for the default `postgres` user within the container. **This is essential for connecting.**
  - `-p 5432:5432` (Optional but common): Maps port 5432 on your host machine to port 5432 in the container (PostgreSQL's default port). This allows local applications to connect via `localhost:5432`.
  - `-d`: Runs the container in detached mode (in the background).
  - `postgres`: Specifies the official PostgreSQL image from Docker Hub (will download if not present locally).
- **Outcome:** A PostgreSQL database server running in an isolated Docker container, accessible for your Spring Boot application.

## 3. Connecting to the Dockerized Database & Adding JPA

- **Verifying Connection (e.g., with VS Code SQLTools):**
  - It's good practice to use a database client/tool to connect to the newly created Dockerized database.
  - This helps verify that the container is running, accessible, and that your connection parameters (host: `localhost`, port: `5432`, user: `postgres`, password: `mysecretpassword`) are correct.
  - Allows for direct database interaction (running queries, creating tables manually if needed for testing).
- **Adding Spring Data JPA Dependency:**
  - To enable database interaction from Spring Boot using the Java Persistence API (JPA), add the `spring-boot-starter-data-jpa` dependency to your `pom.xml` (for Maven projects).
    ```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
    ```
  - **This starter includes:**
    - **Spring Data JPA:** Simplifies creating repositories for data access.
    - **JPA Provider (Hibernate by default):** Implements the ORM (Object-Relational Mapping) functionality.
    - **JDBC Drivers & Connection Pooling (HikariCP by default):** For efficient database connectivity.

---

## JPA Entities: Mapping Java Objects to Database Tables

An **Entity** in JPA (Java Persistence API) is a Java class whose instances can be stored in a relational database. It represents a table in the database, and instances of the entity correspond to rows in that table.

- **`@Entity` Annotation:**

  - Placed at the class level to declare a Java class as a JPA entity.
  - Example:

    ```java
    import jakarta.persistence.Entity;
    import jakarta.persistence.Id;

    @Entity
    public class Student {
        // ... fields and methods ...
    }
    ```

- **Primary Key (`@Id`):**
  - Every JPA entity **must** have a primary key, which uniquely identifies each record in the corresponding database table.
  - The `@Id` annotation is used on a field within the entity class to designate it as the primary key.
    ```java
    @Entity
    public class Student {
        @Id
        private Integer id; // This field is the primary key
        private String firstName;
        // ... other fields ...
    }
    ```
- **No-Argument Constructor:**
  - JPA providers (like Hibernate) typically require a no-argument (default) constructor to create instances of entities when retrieving them from the database.
- **Getters and Setters:**
  - Standard JavaBeans practice; used by JPA to access and set field values.

---

## Configuring Database Connection & JPA Behavior (`application.yml`)

Spring Boot allows easy configuration of database connections and JPA/Hibernate properties in the `application.yml` (or `application.properties`) file.

## Understanding `spring.jpa.hibernate.ddl-auto` Values

This property determines how Hibernate, when used with Spring Data JPA, manages your database schema based on your defined entities. Choosing the right value is crucial for your application's lifecycle and data integrity.

## Property Values Explained

Here's a breakdown of the available values for `spring.jpa.hibernate.ddl-auto` and their implications:

### `create`

- **Action**: Drops any existing database schema (tables, data) and **creates a new schema from scratch** based on your entities every time the application starts.
- **Use**: Good for **early development stages** or **quick, isolated tests**. Be aware: **All data is lost on each application restart.**

### `create-drop`

- **Action**: Creates the schema at application startup and **drops it when the application shuts down gracefully**.
- **Use**: Often utilized for **integration tests** where a clean database state is required for each test run. **All data is lost on application shutdown.**

### `update`

- **Action**: Attempts to **update the existing database schema** to match your current entity definitions (e.g., adding new columns or tables). It tries to preserve existing data but can be **unpredictable with complex schema changes**.
- **Use**: Can be convenient during development for minor changes. However, **use with extreme caution**. It is **not recommended for production environments** as it can lead to schema inconsistencies or accidental data loss.

### `validate`

- **Action**: **Validates the existing database schema** against your entity definitions. If there's a mismatch (e.g., a missing table or column), the application will **fail to start**. It makes **no changes** to the database schema.
- **Use**: Good for **checking consistency** against a database schema that is managed externally (e.g., by DBAs or migration scripts).

### `none`

- **Action**: Takes **no action** on the database schema. It assumes the schema is correctly set up and managed entirely by external means.
- **Use**: This is the **recommended setting for production environments**. Schema migrations should be handled robustly using dedicated tools like **Flyway** or **Liquibase**.

**Key Takeaway:** Choose the `ddl-auto` strategy that best fits your current environment and workflow. For production, always prefer `none` or `validate` and manage schema changes explicitly with migration tools.
