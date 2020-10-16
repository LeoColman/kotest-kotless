# Kotest Kotless

![Build](https://github.com/LeoColman/kotest-kotless/workflows/Build/badge.svg)
[![GitHub](https://img.shields.io/github/license/LeoColman/kotest-kotless.svg)](https://github.com/LeoColman/SimpleCpfValidator/blob/master/LICENSE) [![Maven Central](https://img.shields.io/maven-central/v/br.com.colman.kotest.kotless/kotest-kotless.svg)](https://search.maven.org/search?q=g:br.com.colman.kotest.kotless)

Utilities to ease testing the [Kotless](https://github.com/jetbrains/kotless) framework with [Kotest](https://github.com/kotest/kotest).

## Using with Gradle
```kotlin
testImplementation("br.com.colman.kotest.kotless:kotest-kotless:{version}")
```

## Kotless Listener

To start and stop the **Kotless Local App** (Currently only `kotless-lang`), add the `KotlessListener` to your listeners. From there you can access your kotless application normally:

```kotlin

listeners(KotlsListener(port = 8080))

test("My kotless test") {
    // Code that uses your HTTP service
    "http://localhost:8080/foo".httpGet().responseString().third.get() shouldBe """{"foo":"bar"}"""

}

```

## Contributing

Feel free to open a pull request, or an issue for anything that you believe should be added to this utility.

## Changelog
The changelog can be seen at the [releases page](https://github.com/LeoColman/kotest-kotless/releases)