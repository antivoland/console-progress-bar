# Console progress bar

The simplest console-based progress bar with non-blocking interface for Java.

![Animated preview](bar.gif)

## Maven

```xml
<dependency>
    <groupId>io.github.antivoland</groupId>
    <artifactId>console-progress-bar</artifactId>
    <version>1.1.0-SNAPSHOT</version>
</dependency>
```

## Usage

```java
try (ConsoleProgressBar bar = new ConsoleProgressBar(someMax)) {
    some loop {
        // do some business
        bar.step(); // step by 1
        bar.stepBy(someDelta); // step by someDelta
    }
}
```