# QuestAPI

### About Project
QuestAPI is a versatile and extensible quest/mission system API designed for various applications. While it includes implementations for platforms like Minecraft, the core architecture is platform-agnostic and can be integrated into any Java application, including Spring-based services, gaming applications, or educational platforms.

### Features
- 📚 Modular architecture (Core API, Implementation Modules, Localization)
- 🔌 Platform-agnostic core design
- 🎮 Easy integration with any Java-based system
- 🌍 Multi-language support
- ⚡ High performance
- 🛠️ Highly customizable quest/mission system
- 🔄 Extensible architecture supporting custom implementations

### Modules
- **Core**: Platform-agnostic core API containing base implementations and interfaces
- **Paper**: Reference implementation for Minecraft Paper/Spigot servers
- **Localization**: Independent localization system for multi-language support

### Use Cases
- 🎮 Gaming platforms
- 📚 Educational applications
- 🏢 Enterprise applications
- 🌐 Web services (Spring Boot)
- 📱 Mobile applications (Android)

### Requirements
- Java 21 or higher
- Gradle 8.5 or higher

### Getting Started
To use QuestAPI in your project, add it as a dependency to your build system. The modular architecture allows you to include only the components you need:

- Use the core module for platform-agnostic implementations
- Add specific implementation modules based on your platform
- Include the localization module if you need multi-language support

### Installation

#### Maven
Add the repository:
```xml
<repository>
    <id>oyunzor-snapshots</id>
    <url>https://repo.oyunzor.com/</url>
</repository>
```

Api module dependency:
```xml
<dependency>
    <groupId>com.deichor</groupId>
    <artifactId>questapi-api</artifactId>
    <version>0.1.2-SNAPSHOT</version>
</dependency>
```

Paper module dependency:
```xml
<dependency>
    <groupId>com.deichor</groupId>
    <artifactId>questapi-paper</artifactId>
    <version>0.1.2-SNAPSHOT</version>
</dependency>
```

#### Gradle
Add the repository:
```kotlin
repositories {
    maven {
        url = uri("https://repo.oyunzor.com/")
    }
}
```

Api module dependency:
```kotlin
dependencies {
    implementation("com.deichor:questapi-api:0.1.1-SNAPSHOT")
}
```

Paper module dependency:
```kotlin
dependencies {
    implementation("com.deichor:questapi-paper:0.1.1-SNAPSHOT")
}
```

## License
This project is licensed under the [Apache License 2.0](LICENSE).

---
Built for Developers, by Developers ❤️