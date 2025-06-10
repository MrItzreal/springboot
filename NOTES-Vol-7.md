# Test Isolation with Mockito

When unit testing a component like a service class, the goal is **test isolation**. This means testing the logic of that single class in isolation, without relying on the real implementations of its dependencies (like database repositories, other services, or mappers).

**Mockito** is a powerful mocking framework that allows you to create "mock" or "dummy" versions of these dependencies. This makes your unit tests:

- **Fast:** They don't need to connect to a database or perform other slow operations.
- **Reliable:** They won't fail because of an issue in another class.
- **Focused:** They test only the business logic of the class under test.

---

## Setting Up Tests with Mockito Annotations

Mockito provides annotations to simplify the setup of your test environment.

- **`@Mock`**:

  - Creates a mock (a dummy implementation) of a dependency. This mock has no real logic; you will define its behavior for each test.
  - _Example:_
    ```java
    // @Mock
    // private StudentRepository repository;
    ```

- **`@InjectMocks`**:

  - Creates a real instance of the class you want to test and automatically injects the dependencies that you have annotated with `@Mock` into it.
  - _Example:_
    ```java
    // @InjectMocks
    // private StudentService studentService;
    ```

- **`MockitoAnnotations.openMocks(this)`**:
  - This command initializes the fields annotated with `@Mock` and `@InjectMocks`. It's typically called in a `@BeforeEach` setup method to ensure a fresh set of mocks for every test.
  - _Example:_
    ```java
    // @BeforeEach
    // void setUp() {
    //   MockitoAnnotations.openMocks(this);
    // }
    ```

---

## Stubbing and Verifying with Mockito

### A. Stubbing: Defining Mock Behavior (`when...thenReturn`)

**Stubbing** is the process of telling your mocks how to behave when their methods are called.

- **Syntax:** `Mockito.when(mockObject.someMethod(arguments)).thenReturn(valueToReturn);`
- **Translation:** "When `someMethod` on the `mockObject` is called with these `arguments`, then you must return this `valueToReturn`."
- **Purpose:** This allows you to control the environment of your test, making it predictable. You are defining the "Given" part of your test.

### B. Verifying: Checking Interactions (`verify`)

While assertions (`assertEquals`) check the **result** of a method call, `verify` checks the **behavior**—that is, if a specific method on a mock was actually called.

- **Syntax:** `Mockito.verify(mockObject, times(n)).someMethod(arguments);`
- **Translation:** "Verify that `someMethod` on the `mockObject` was called exactly `n` times with these `arguments`."
- **Purpose:** This is essential for testing methods that don't return a value (i.e., `void` methods), as you can't assert a result but you can verify that the correct action was performed.
  - _Note:_ `times(1)` is the default, so you can omit it for a single call verification.

### C. Argument Matchers (`any`)

Sometimes you don't need to check for a specific instance of an object being passed to a method. Argument matchers provide flexibility.

- **Syntax:** `any(ClassName.class)`
- **Translation:** Matches any object of the given class type.
- **Purpose:** Useful when it's difficult or unnecessary to construct the exact object instance that a method will receive during a test.
- **Example:**
  ```java
  // "When toStudentResponseDto is called with *any* Student object, then return..."
  // Mockito.when(studentMapper.toStudentResponseDto(any(Student.class)))
  //       .thenReturn(predefinedResponse);
  ```

---
