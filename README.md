![build](https://github.com/2four/system-safe/actions/workflows/gradle.yml/badge.svg)
![coverage](https://img.shields.io/codecov/c/github/2four/system-safe?token=JUMAE6HREY)


# SystemSafe

SystemSafe is a hassle-free JUnit 5 extension that prevents unintended side effects from interactions with
`java.lang.System` in tests.

## Installation

Coming soon to a repository near you:

### Maven
```xml
<dependency>
    <groupId>com.github.hellproxy</groupId>
    <artifactId>system-safe</artifactId>
    <version>1.0.0</version>
    <scope>test</scope>
</dependency>
```

### Gradle
```groovy
testImplementation 'com.github.hellproxy:system-safe:1.0.0'
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

You should now be able to get and set System Properties in your tests as if they were being run in isolation. For 
example:

```java
@Execution(CONCURRENT)
class FruitTest {

    @Test
    void testApple() {
        System.setProperty("fruit", "apple");
        Thread.sleep(100);
        assertThat(System.getProperty("fruit")).isEqualTo("apple"); // will sometimes be "banana" (bad!)
    }

    @Test
    void testBanana() {
        System.setProperty("fruit", "banana");
        Thread.sleep(100);
        assertThat(System.getProperty("fruit")).isEqualTo("banana"); // will sometimes be "apple" (also bad!)
    }
}
```

Under normal circumstances, the above test would have a race condition when run in parallel. Running with
`SystemSafeExtension` prevents this by giving each test its own sandboxed set of properties to play with.
