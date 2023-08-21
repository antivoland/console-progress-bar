# Console progress bar

[![build](https://github.com/antivoland/console-progress-bar/workflows/build/badge.svg)](https://github.com/antivoland/console-progress-bar/actions/workflows/build.yaml)
[![publish](https://github.com/antivoland/console-progress-bar/workflows/publish/badge.svg)](https://github.com/antivoland/console-progress-bar/actions/workflows/publish.yaml)

The simplest console-based progress bar with non-blocking interface for Java.

![Animated preview](bar.gif)

## Installation

Use the following Maven dependency:

```xml
<dependency>
    <groupId>io.github.antivoland</groupId>
    <artifactId>console-progress-bar</artifactId>
    <version>1.1.0-SNAPSHOT</version>
</dependency>
```

Currently only snapshots are available, so you will need to add the repository to your project as follows:

```xml
<repositories>
    <repository>
        <id>ossrh</id>
        <url>https://s01.oss.sonatype.org/content/repositories/snapshots</url>
    </repository>
</repositories>
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