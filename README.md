# SystemSafe

SystemSafe is a hassle-free JUnit 5 extension that prevents unintended side effects from interactions with
`java.lang.System` in tests.

## Installation

Coming soon to a repository near you:

### Maven
```xml
<dependency>
    <groupId>com.github.twofour</groupId>
    <artifactId>system-safe</artifactId>
    <version>1.0.0</version>
    <scope>test</scope>
</dependency>
```

### Gradle
```groovy
testImplementation 'com.github.twofour:system-safe:1.0.0'
```

## Usage

You can add the following line to `src/test/resources/junit-platform.properties`:

```properties
junit.jupiter.extensions.autodetection.enabled=true
```

Or you can add the extension to individual test classes:

```java
@ExtendWith(SystemSafeExtension.class)
class MyTest {
```

You should now be able to get and set System properties in your tests as if they were being run in isolation. For 
example:

```java
@Execution(CONCURRENT)
@ExtendWith(SystemSafeExtension.class)
class FruitTest {

    @Test
    void testApple() {
        System.setProperty("fruit", "apple");
        assertThat(System.getProperty("fruit")).isEqualTo("apple");
    }

    @Test
    void testBanana() {
        System.setProperty("fruit", "banana");
        assertThat(System.getProperty("fruit")).isEqualTo("banana");
    }
}
```

Under normal circumstances, the above test would have a race condition when run in parallel. Running with System Safe
gives each test its own sandboxed set of properties to play with.
