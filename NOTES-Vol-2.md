# Spring Boot Learning Notes-Vol-2

## 1. Enhancing Development Workflow: `spring-boot-devtools`

- **Purpose:** Improves the development experience by reducing manual restarts and providing development-time assistance.
- **Key Features:**
  - **Automatic Restart:**
    - Monitors changes to files on the classpath (primarily compiled `.java` code).
    - Automatically restarts the Spring application when changes are detected.
    - Uses two classloaders (a "base" classloader for stable dependencies and a "restart" classloader for application code) for faster restarts.
    - Most impactful for changes in Java logic (controllers, services, etc.).
  - **LiveReload:**
    - Embeds a LiveReload server that can trigger automatic browser refreshes when resources (HTML, CSS, JS, templates) change. Requires a browser extension.
  - **Sensible Development-Time Defaults:**
    - Applies configurations suitable for development, like disabling caching for template engines (e.g., Thymeleaf) so template changes are visible immediately.
- **Usage Notes:**
  - To observe its effect on Java code changes:
    1.  Ensure `spring-boot-devtools` is a dependency in `pom.xml`.
    2.  Ensure your IDE (e.g., VS Code) is configured for auto-build on save for Java files.
    3.  Make a change in a `.java` file (e.g., modify logic in a controller method, add a `System.out.println`).
    4.  Save the file; observe the application console for restart logs.
  - Changes to static content or templates might sometimes appear to refresh without `devtools` if template caching is already disabled by default in development mode, but `devtools` ensures a more robust and broader auto-reload capability, especially for backend code.

## 2. Dependency Injection (DI) in Spring

### A. Core Concepts: Constructor Injection & Open/Closed Principle (OCP)

- **Constructor Injection:**
  - **Recommended approach** for injecting mandatory dependencies into a class.
  - Dependencies are provided as parameters to the class's constructor.
  - **Benefits:**
    - **Immutability:** Dependencies can be declared `final`.
    - **Clear Dependencies:** Explicitly shows what a class needs to function.
    - **Testability:** Easy to pass mock/stub implementations during testing.
    - **Ensures Object Validity:** An object is created with all its required dependencies.
- **Open/Closed Principle (OCP):**
  - Software entities should be **open for extension** but **closed for modification**.
  - **How Constructor Injection & Interfaces support OCP:**
    - **Open for Extension:** New implementations of an interface (e.g., `PaymentService`) can be added without changing classes that depend on the interface (e.g., `OrderService`).
    - **Closed for Modification:** The dependent class (`OrderService`) doesn't need to change its code to use a new implementation of its dependency; it just needs to be _given_ a different implementation (via DI).

### B. Manual DI vs. Spring's IoC Container

- **Manual DI:**
  - Manually creating instances of dependencies and "injecting" them by passing them to the constructor of the dependent object.
  - Example: `var orderService = new OrderService(new PayPalPaymentService());`
- **Spring's IoC (Inversion of Control) Container:**
  - Spring manages the creation, configuration, and lifecycle of objects (called "beans").
  - It automatically handles the wiring (injection) of dependencies between beans.
  - This inverts the control: instead of your code creating dependencies, Spring creates and provides them.

### C. Key Spring Annotations & Concepts for DI

- **`@SpringBootApplication`:**
  - A meta-annotation enabling Spring Boot's auto-configuration, component scanning, and ability to define configurations.
  - Includes `@ComponentScan`, which tells Spring where to look for beans.
- **Stereotype Annotations (for marking beans):**
  - `@Component`: Generic stereotype for any Spring-managed component.
  - `@Service`: Specialization of `@Component` for service layer classes.
  - `@Repository`: For persistence layer beans.
  - `@Controller`, `@RestController`: For presentation layer beans.
- **`@Autowired`:**
  - Used for automatic dependency injection.
  - When placed on a constructor, Spring will look for a bean matching the constructor parameter type(s) and inject it.
  - Optional on constructors in recent Spring versions if the class has only one constructor.
- **`ApplicationContext`:**
  - The central interface representing the Spring IoC container.
  - `SpringApplication.run(...)` returns an `ApplicationContext`.
- **`context.getBean(ClassType.class)` or `context.getBean("beanName", ClassType.class)`:**
  - Used to retrieve a managed bean instance from the Spring container.

### D. Handling Multiple Implementations of an Interface

- When multiple beans implement the same interface (e.g., `StripePaymentService` and `PayPalPaymentService` both implement `PaymentService`), Spring needs to know which one to inject.
- **Strategies:**
  - **`@Primary`:**
    - Place this annotation on one of the implementation classes.
    - Marks that bean as the default one to be injected when an autowired dependency of the interface type is encountered.
  - **`@Qualifier("beanName")`:**
    - Used at the injection point (e.g., on a constructor parameter alongside `@Autowired`) to specify the exact name of the bean to be injected.
    - The bean name can be explicitly set (e.g., `@Service("payPalSvc")`) or defaults to the class name with the first letter lowercase (e.g., `payPalPaymentService`).

### E. Benefits of Spring DI

- **Reduced Boilerplate Code:** No manual creation and wiring of objects.
- **Lifecycle Management:** Spring manages the entire lifecycle of beans.
- **Enhanced Flexibility & Decoupling:** Easier to swap implementations (e.g., using profiles, `@Primary`, `@Qualifier`) with minimal code changes, often just through configuration or annotations.
- **Testability:** Simplifies testing by making it easy to inject mock dependencies.
- **Integration:** Seamless integration with other Spring features (AOP, transactions, security, etc.).

## Fun Fact about`@Autowired` Annotation on Constructors:

- **Single Constructor:**
  - If a Spring bean class has only one constructor, the use of `@Autowired` on that constructor is **optional**.
  - Spring is smart enough to know that this single constructor should be used for dependency injection.
- **Multiple Constructors:**
  - If a Spring bean class has more than one constructor, you **must** use the `@Autowired` annotation on **one** of them.
  - This explicitly tells Spring which constructor it should use to instantiate the bean and inject its dependencies, resolving any ambiguity.

## Choosing Between `@Component` and `@Service` Annotations

- **`@Component`:**
  - A generic stereotype annotation indicating that a class is a Spring-managed component (a bean).
  - It's the base annotation for other stereotype annotations.
- **`@Service`:**
  - A **specialization** of `@Component`.
  - It's intended to be used for classes in the **service layer**, which typically encapsulate the application's **business logic**.
  - Functionally, for basic dependency injection, `@Service` behaves like `@Component`. However, using `@Service` provides better semantic clarity about the role of the class.
  - It also allows for more specific targeting by Spring features or tools (e.g., aspect-oriented programming pointcuts might be defined to specifically target `@Service` beans).
  - **Recommendation:** Use `@Service` for classes that implement business logic or service facades, and `@Component` for more generic, utility-like beans that don't fit into other specific stereotypes like `@Repository` or `@Controller`.

# Switching Active Spring Boot Profiles

This project demonstrates how to switch between different Spring Boot profiles using two methods:

## 1. Setting `spring.profiles.active` in `application.properties`

- Open the `src/main/resources/application.properties` file.
- Add the line `spring.profiles.active=your_profile_name`, replacing `your_profile_name` with the desired profile (e.g., `dev`, `prod`).
- When the application starts, it will load properties from `application-{your_profile_name}.properties` (if it exists) and activate the specified profile.

## 2. Setting `SPRING_PROFILES_ACTIVE` as an Environment Variable

- Before running the application, set the `SPRING_PROFILES_ACTIVE` environment variable to the desired profile name.
  - **Example (Linux/macOS):** `export SPRING_PROFILES_ACTIVE=dev`
  - **Example (Windows):** `set SPRING_PROFILES_ACTIVE=dev`
- Run the application from the same terminal session where you set the environment variable.
- The application will activate the specified profile.

  **Note:** Environment variables take precedence over settings in `application.properties`. To deactivate a profile set via environment variable, unset the variable (e.g., `unset SPRING_PROFILES_ACTIVE` on Linux/macOS).

  # `@JsonProperty` Annotation

The `@JsonProperty` annotation in Spring Boot is used to customize the name of a field when serializing (converting Java object to JSON) or deserializing (converting JSON to Java object) data.

## When to Use `@JsonProperty`

- **External API Compatibility:** When interacting with external APIs that use different naming conventions for fields than your Java code (e.g., `customer_name` instead of `customerName`).
- **Legacy Data Structures:** When working with databases or data formats that use a different naming convention.
- **Specific Serialization Requirements:** When you want to expose a different name for a field in your JSON output for security or other reasons.

## Example

```java
public class Order {
  @JsonProperty("customer_name")
  private String customerName;
  private String productName;
  private int quantity;
  
  // getters and setters (Would go below)
}
```

In this example, the `customerName` field in the `Order` class will be serialized and deserialized as `customer_name` in the JSON data.

## When to Avoid `@JsonProperty`

- When you can use consistent naming conventions between your Java code and JSON representation.
- When it introduces unnecessary complexity and confusion.

## Best Practices

- Prefer consistent naming conventions whenever possible.
- Use a consistent naming strategy (e.g., `spring.jackson.property-naming-strategy=SNAKE_CASE`) in your `application.properties` or `application.yml` file to automatically translate between naming conventions.
- Document `@JsonProperty` usage with comments explaining why it's necessary.
- Minimize its use to only when absolutely necessary.
