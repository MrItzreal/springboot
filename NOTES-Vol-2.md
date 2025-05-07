# Spring Boot Learning Notes-Vol-2

## 1. Enhancing Development Workflow: `spring-boot-devtools`

* **Purpose:** Improves the development experience by reducing manual restarts and providing development-time assistance.
* **Key Features:**
    * **Automatic Restart:**
        * Monitors changes to files on the classpath (primarily compiled `.java` code).
        * Automatically restarts the Spring application when changes are detected.
        * Uses two classloaders (a "base" classloader for stable dependencies and a "restart" classloader for application code) for faster restarts.
        * Most impactful for changes in Java logic (controllers, services, etc.).
    * **LiveReload:**
        * Embeds a LiveReload server that can trigger automatic browser refreshes when resources (HTML, CSS, JS, templates) change. Requires a browser extension.
    * **Sensible Development-Time Defaults:**
        * Applies configurations suitable for development, like disabling caching for template engines (e.g., Thymeleaf) so template changes are visible immediately.
* **Usage Notes:**
    * To observe its effect on Java code changes:
        1.  Ensure `spring-boot-devtools` is a dependency in `pom.xml`.
        2.  Ensure your IDE (e.g., VS Code) is configured for auto-build on save for Java files.
        3.  Make a change in a `.java` file (e.g., modify logic in a controller method, add a `System.out.println`).
        4.  Save the file; observe the application console for restart logs.
    * Changes to static content or templates might sometimes appear to refresh without `devtools` if template caching is already disabled by default in development mode, but `devtools` ensures a more robust and broader auto-reload capability, especially for backend code.

## 2. Dependency Injection (DI) in Spring

### A. Core Concepts: Constructor Injection & Open/Closed Principle (OCP)

* **Constructor Injection:**
    * **Recommended approach** for injecting mandatory dependencies into a class.
    * Dependencies are provided as parameters to the class's constructor.
    * **Benefits:**
        * **Immutability:** Dependencies can be declared `final`.
        * **Clear Dependencies:** Explicitly shows what a class needs to function.
        * **Testability:** Easy to pass mock/stub implementations during testing.
        * **Ensures Object Validity:** An object is created with all its required dependencies.
* **Open/Closed Principle (OCP):**
    * Software entities should be **open for extension** but **closed for modification**.
    * **How Constructor Injection & Interfaces support OCP:**
        * **Open for Extension:** New implementations of an interface (e.g., `PaymentService`) can be added without changing classes that depend on the interface (e.g., `OrderService`).
        * **Closed for Modification:** The dependent class (`OrderService`) doesn't need to change its code to use a new implementation of its dependency; it just needs to be *given* a different implementation (via DI).

### B. Manual DI vs. Spring's IoC Container

* **Manual DI:**
    * Manually creating instances of dependencies and "injecting" them by passing them to the constructor of the dependent object.
    * Example: `var orderService = new OrderService(new PayPalPaymentService());`
* **Spring's IoC (Inversion of Control) Container:**
    * Spring manages the creation, configuration, and lifecycle of objects (called "beans").
    * It automatically handles the wiring (injection) of dependencies between beans.
    * This inverts the control: instead of your code creating dependencies, Spring creates and provides them.

### C. Key Spring Annotations & Concepts for DI

* **`@SpringBootApplication`:**
    * A meta-annotation enabling Spring Boot's auto-configuration, component scanning, and ability to define configurations.
    * Includes `@ComponentScan`, which tells Spring where to look for beans.
* **Stereotype Annotations (for marking beans):**
    * `@Component`: Generic stereotype for any Spring-managed component.
    * `@Service`: Specialization of `@Component` for service layer classes.
    * `@Repository`: For persistence layer beans.
    * `@Controller`, `@RestController`: For presentation layer beans.
* **`@Autowired`:**
    * Used for automatic dependency injection.
    * When placed on a constructor, Spring will look for a bean matching the constructor parameter type(s) and inject it.
    * Optional on constructors in recent Spring versions if the class has only one constructor.
* **`ApplicationContext`:**
    * The central interface representing the Spring IoC container.
    * `SpringApplication.run(...)` returns an `ApplicationContext`.
* **`context.getBean(ClassType.class)` or `context.getBean("beanName", ClassType.class)`:**
    * Used to retrieve a managed bean instance from the Spring container.

### D. Handling Multiple Implementations of an Interface

* When multiple beans implement the same interface (e.g., `StripePaymentService` and `PayPalPaymentService` both implement `PaymentService`), Spring needs to know which one to inject.
* **Strategies:**
    * **`@Primary`:**
        * Place this annotation on one of the implementation classes.
        * Marks that bean as the default one to be injected when an autowired dependency of the interface type is encountered.
    * **`@Qualifier("beanName")`:**
        * Used at the injection point (e.g., on a constructor parameter alongside `@Autowired`) to specify the exact name of the bean to be injected.
        * The bean name can be explicitly set (e.g., `@Service("payPalSvc")`) or defaults to the class name with the first letter lowercase (e.g., `payPalPaymentService`).

### E. Benefits of Spring DI

* **Reduced Boilerplate Code:** No manual creation and wiring of objects.
* **Lifecycle Management:** Spring manages the entire lifecycle of beans.
* **Enhanced Flexibility & Decoupling:** Easier to swap implementations (e.g., using profiles, `@Primary`, `@Qualifier`) with minimal code changes, often just through configuration or annotations.
* **Testability:** Simplifies testing by making it easy to inject mock dependencies.
* **Integration:** Seamless integration with other Spring features (AOP, transactions, security, etc.).

---
