# Spring Boot Learning Notes-Vol-1:

## 1. Understanding the Spring Ecosystem

- **Spring Framework:**
  - The foundational, "parent" framework for building Java applications, especially enterprise-level.
  - Provides core features like Inversion of Control (IoC) / Dependency Injection (DI) and Aspect-Oriented Programming (AOP).
- **Spring Boot:**
  - An extension of the Spring Framework, not a replacement.
  - **Goal:** Simplify Spring application development and get applications up and running quickly.
  - Achieves this through:
    - **"Opinionated" defaults:** Reduces boilerplate configuration.
    - **Auto-configuration:** Automatically configures beans based on classpath and properties.
    - **Standalone applications:** Can run applications with embedded servers (Tomcat, Jetty, Undertow) without needing to deploy WAR files.
    - **Production-ready features:** Includes metrics, health checks, externalized configuration.
- **Other Spring Projects (Briefly Mentioned):**
  - **Spring Data:** Simplifies data access with various database technologies (JPA, MongoDB, etc.).
  - **Spring Security:** Powerful framework for authentication and authorization.
  - **Spring Batch:** Framework for robust batch processing applications.

## 2. Build Tool: Apache Maven

- **Role:**
  - **Build Automation Tool:** Manages the project build lifecycle (compiling, testing, packaging).
  - **Dependency Management Tool:** Automatically downloads and manages project libraries (dependencies).
- **`pom.xml` (Project Object Model):**
  - The central configuration file for Maven projects.
  - Defines project metadata, dependencies, build plugins, etc.

## 3. Creating Spring Boot Projects

- **Spring Initializr (`start.spring.io`):**
  - A web-based tool to generate a basic Spring Boot project structure.
  - Allows selection of Spring Boot version, language (Java), build tool (Maven), and initial dependencies.
- **Through an IDE (e.g., VS Code):**
  - IDEs like VS Code (with extensions like the "Spring Boot Extension Pack") can directly initialize Spring Boot projects, often using Spring Initializr behind the scenes.

## 4. IDE Considerations (VS Code vs. IntelliJ IDEA)

- **VS Code:**
  - A viable and good option for Spring Boot development, especially when learning and already comfortable with it.
  - Lightweight, fast, with good Java and Spring Boot support via extensions.
- **IntelliJ IDEA:**
  - Often recommended by instructors/experienced developers due to its deep and advanced Spring integration (especially the Ultimate Edition).
  - Powerful Java refactoring and analysis tools.
  - Can be explored later once more comfortable with Spring Boot fundamentals.

## 5. Code Quality: Null Annotations and Analysis

- **Null Annotation Types (e.g., `@NonNull`, `@Nullable`):**
  - Annotations used to declare whether a variable, parameter, or return type is expected or allowed to be `null`.
  - Spring Framework uses these extensively.
- **Null Analysis:**
  - IDE feature (prompted when null annotations are detected).
  - Statically analyzes code using these annotations to detect potential `NullPointerExceptions` at compile-time or in the IDE.
  - **Recommendation:** Generally good to enable this feature for improved code safety and clarity.

## 6. Project Configuration Files & Analogies

- **`pom.xml` vs. `package.json` (JavaScript):**
  - **Similarities:** Both are central configuration files for defining project metadata, dependencies, and scripts/build commands.
  - **Differences:**
    - Ecosystem: Java/Maven vs. JavaScript/npm.
    - Format: XML vs. JSON.
    - Build Lifecycle: Maven has a structured lifecycle; npm scripts are more free-form.
- **`src/main/resources/application.properties` vs. `.env` files:**
  - **Similarities:** Both are used for externalizing application configuration.
  - **`application.properties` (or `.yml`):**
    - Spring Boot's primary file for application configuration (server port, database details, custom settings).
    - Located in `src/main/resources`.
    - Spring Boot automatically loads it.
    - Supports **profiles** (e.g., `application-dev.properties`, `application-prod.properties`) for environment-specific configurations.
    - While it _can_ hold secrets, for production, OS environment variables or dedicated secret management tools are preferred for sensitive data.
  - **`.env` files:**
    - Common in other ecosystems (Node.js, Python) for environment-specific variables, especially secrets.
    - Usually gitignored.

## 7. Dependency Sources: Repositories

- **Maven Central Repository:**
  - The primary public repository for Java libraries (JARs) and Maven plugins.
  - Analogous to the **NPM registry** in the JavaScript ecosystem.
  - Maven downloads declared dependencies from here.

## 8. Working with Dependencies in `pom.xml`

- **Adding a Dependency:**
  - Example for `spring-boot-starter-web`:
    ```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    ```
- **IDE Synchronization:**
  - When `pom.xml` is modified (e.g., by adding a dependency), the IDE (like VS Code) will prompt to "synchronize the Java classpath/configuration."
  - **Action:** Always accept/confirm this. It allows Maven to download new dependencies and update the IDE's understanding of the project.

## 9. Best Practice: Managing Dependency Versions

- **Let Spring Boot Manage Versions:**
  - It's highly recommended to omit explicit `<version>` tags for dependencies that are managed by Spring Boot.
- **How Spring Boot Manages Them:**
  - **`spring-boot-starter-parent`:** If your project inherits from this parent POM, it provides a `<dependencyManagement>` section with curated versions for many common libraries.
    ```xml
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>YOUR_SPRING_BOOT_VERSION</version> <relativePath/>
    </parent>
    ```
  - **`spring-boot-dependencies` BOM (Bill of Materials):** An alternative if you can't use the parent POM. You import this BOM in your `<dependencyManagement>` section.
- **Benefits:**
  - **Compatibility:** Ensures dependencies work well together with your Spring Boot version.
  - **Simplified `pom.xml`:** Cleaner declarations.
  - **Easier Upgrades:** Updating the parent/BOM version often updates transitive dependencies correctly.
- **Declaration without explicit version (when managed):**
  ```xml
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
  </dependency>
  ```

---

# Spring Boot Annotations Overview

Annotations are labels or tags that we add in the code to give instructions or additional context to the compiler. We can apply them to: classes, methods, fields, parameters etc.

They significantly simplify the development process by reducing boilerplate, streamlining configuration, and enabling core features like dependency injection and auto-configuration.

This document provides a concise overview of some of the most commonly used Spring Boot annotations.

## Core Annotations

These annotations are fundamental to defining and managing beans within the Spring IoC (Inversion of Control) container.

- `@Component`: Marks a class as a Spring-managed component, also known as a bean. Spring will automatically detect and register beans annotated with `@Component`.
- `@Service`: A specialized form of `@Component` that indicates a class provides business logic or services within the application. It's a semantic distinction that can improve code organization.
- `@Repository`: Another specialization of `@Component`, used to mark classes responsible for data access operations (DAOs - Data Access Objects). This annotation often provides additional exception translation for persistence-related exceptions.
- `@Controller`: Defines a class as a controller in the Model-View-Controller (MVC) architectural pattern, responsible for handling incoming web requests and returning responses.
- `@Configuration`: Indicates that a class provides bean definitions. Methods within a `@Configuration` class annotated with `@Bean` will produce beans managed by the Spring container.
- `@Bean`: Marks a method within a `@Configuration` class that creates and configures a bean. The name of the bean will default to the method name.
- `@Autowired`: Enables dependency injection. It is used to automatically inject dependencies (other beans) into a class, constructor, method, or field.
- `@Qualifier`: Used in conjunction with `@Autowired` to resolve ambiguity when multiple beans of the same type are available in the Spring context. You can specify the name of the bean to be injected using `@Qualifier("beanName")`.
- `@Value`: Injects values from external configuration sources such as properties files (`application.properties`, `application.yml`) or environment variables into fields. You can use SpEL (Spring Expression Language) within the `@Value` annotation (e.g., `@Value("${my.property}")`).
- `@Data`: This is a Lombok annotation (requires the Lombok library to be included in your project). It automatically generates boilerplate code for getters, setters, `equals()`, `hashCode()`, and `toString()` methods for all fields in the class, significantly reducing code verbosity.

## Spring Boot Annotations

These annotations are specific to Spring Boot and provide convenient ways to bootstrap and configure your application.

- `@SpringBootApplication`: A convenience annotation that combines three other important annotations:
  - `@Configuration`: Marks the class as a source of bean definitions.
  - `@EnableAutoConfiguration`: Enables Spring Boot's auto-configuration mechanism, which automatically configures your Spring application based on the dependencies you have added.
  - `@ComponentScan`: Tells Spring where to look for other components, controllers, services, etc. (typically the package of the main application class and its sub-packages).
- `@EnableAutoConfiguration`: Enables Spring Boot's automatic configuration of the Spring Application Context, attempting to automatically configure your application based on the dependencies you've added.
- `@ConditionalOnClass`: Allows you to conditionally configure a bean only if a specific class is present on the classpath. This is useful for optional dependencies.
- `@ConditionalOnMissingBean`: Configures a bean only if a bean of a certain type is not already present in the Spring Application Context. This allows for default implementations that can be easily overridden.

## Web Annotations

These annotations are commonly used when building web applications and RESTful APIs with Spring Boot.

- `@RestController`: A convenience annotation that combines `@Controller` and `@ResponseBody`. Classes annotated with `@RestController` handle incoming web requests and directly return data in formats like JSON or XML, making it suitable for building RESTful APIs.
- `@RequestMapping`: Used to map HTTP requests to specific handler methods in controllers. You can specify the URL path, HTTP method (GET, POST, etc.), and other request attributes.
- `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`: These are specialized forms of `@RequestMapping` that provide more concise syntax for mapping HTTP GET, POST, PUT, and DELETE requests, respectively. For example, `@GetMapping("/users")` is equivalent to `@RequestMapping(method = RequestMethod.GET, path = "/users")`.
- `@PathVariable`: Used to bind URL path segments to method parameters in controller handler methods. For example, `@GetMapping("/users/{id}")` and a method parameter `@PathVariable Long id` would extract the value of the `{id}` segment from the URL.
- `@ResponseBody`: Indicates that the return value of a controller method should be directly written to the HTTP response body (e.g., as JSON or XML). This is commonly used in RESTful APIs.

## Other Annotations

Here are a few other useful annotations you might encounter:

- `@Cacheable`: Enables caching for the result of a method. Subsequent calls with the same arguments will return the cached result, improving performance. This typically requires a configured caching provider.
- `@Document`: When working with NoSQL databases like MongoDB, this annotation (often from Spring Data MongoDB) maps a Java class to a document in a MongoDB collection.
- `@ExceptionHandler`: Used within a `@Controller` or `@RestController` class (or a `@ControllerAdvice` for global exception handling) to define methods that handle specific exceptions thrown by the application. This allows for centralized error handling.

In conclusion, mastering these annotations is fundamental to developing efficient, well-structured, and maintainable Spring Boot applications. They abstract away much of the underlying configuration, allowing developers to concentrate on the core business logic of their applications.

# Spring Bean Scopes

Bean scope defines the lifecycle and visibility of a bean instance within the Spring IoC (Inversion of Control) container. It determines how many instances of a bean are created and how they are shared.

### A. Singleton (Default Scope)

- **Explanation:** Only **one single instance** of the bean is created per Spring IoC container. Every request for this bean receives a reference to this same shared instance.
- **Lifecycle:** Fully managed by Spring, from creation to destruction.
- **Use Cases:** Stateless services, repositories, configuration classes, shared utility components.

### B. Prototype

- **Explanation:** A **new instance** of the bean is created every time it is requested from the Spring container or injected into another bean.
- **Lifecycle:** Spring creates and configures the bean but does not manage its complete destruction lifecycle (destruction callbacks are not automatically called without custom configuration). The client code is responsible after creation.
- **Use Cases:** Stateful beans where each component or user needs an independent instance (e.g., a user-specific builder object, a new connection object).

### C. Request (Web-Aware Scope)

- **Explanation:** A **new instance** of the bean is created for each individual **HTTP request**. The instance is destroyed once the request is completed.
- **Context:** Only applicable in a web-aware Spring `ApplicationContext` (e.g., Spring MVC).
- **Use Cases:** Holding request-specific data like request parameters, user information for the current request.

### D. Session (Web-Aware Scope)

- **Explanation:** A **new instance** of the bean is created for each **HTTP Session**. The same instance is shared across all requests within that same session. The instance is destroyed when the HTTP session is invalidated.
- **Context:** Only applicable in a web-aware Spring `ApplicationContext`.
- **Use Cases:** User-specific data that persists across multiple requests in a session (e.g., user login status, shopping cart).

### E. Application (Web-Aware Scope)

- **Explanation:** Only **one instance** of the bean is created per `ServletContext` (i.e., for the entire web application). It acts like a global singleton within the web application's context.
- **Context:** Only applicable in a web-aware Spring `ApplicationContext`.
- **Use Cases:** Application-wide shared resources, global configurations, or counters.

### F. WebSocket (Web-Aware Scope)

- **Explanation:** A **new instance** of the bean is created for each **WebSocket session**. The instance lives as long as the WebSocket session is active.
- **Context:** Only applicable in a web-aware Spring `ApplicationContext` with WebSocket support.
- **Use Cases:** Managing state specific to an individual WebSocket connection (e.g., user-specific chat state).

---
