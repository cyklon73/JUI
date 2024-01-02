![[Java CI]](https://github.com/cyklon73/JUI/actions/workflows/check.yml/badge.svg)
![[Latest Version]](https://maven.mineking.dev/api/badge/latest/releases/de/cyklon/JUI?prefix=v&name=Latest%20Version&color=0374b5)

# JUI

JEvent provides a powerful and lightweight event system based on the syntax of the [Spigot Event System](https://www.spigotmc.org/wiki/using-the-event-api/).

# Installation

JEvent is hosted on a custom repository at [https://maven.mineking.dev](https://maven.mineking.dev/#/releases/de/cyklon/JEvent). Replace VERSION with the lastest version (without the `v` prefix).
Alternatively, you can download the artifacts from jitpack (not recommended).

### Gradle

```groovy
repositories {
  maven { url "https://maven.mineking.dev/releases" }
}

dependencies {
  implementation "de.cyklon:JUI:VERSION"
}
```

### Maven

```xml
<repositories>
  <repository>
    <id>mineking</id>
    <url>https://maven.mineking.dev/releases</url>
  </repository>
</repositories>

<dependencies>
  <dependency>
    <groupId>de.cyklon</groupId>
    <artifactId>JUI</artifactId>
    <version>VERSION</version>
  </dependency>
</dependencies>
```
