# FloofyFollow

A Minecraft Spigot/Paper plugin that allows foxes to follow players when fed sweet berries.

## Features

- Feed a fox a sweet berry to make it follow you for 2 minutes
- Visual particle effects when the fox starts following you
- Chat messages to inform you when the fox starts and stops following
- 5-minute cooldown per fox to prevent spam

## How It Works

1. Right-click on a fox while holding sweet berries
2. The fox will consume one sweet berry
3. The fox will follow you for 2 minutes
4. After 2 minutes, the fox will stop following you
5. You can't feed the same fox again for 5 minutes

## Building the Plugin

### Automatic Releases and Package Publishing

This repository is configured with GitHub Actions to automatically:

1. **Continuous Integration**: Every push to the repository is automatically built and tested
2. **Official Releases**: When a tag is pushed (e.g., `v1.0.0`), a GitHub Release is automatically created
3. **Maven Package**: The plugin is published to GitHub Packages as a Maven package

### Using the Plugin

#### Direct Download

To download the latest release JAR:
1. Go to the "Releases" section in the GitHub repository
2. Find the latest release
3. Download the JAR file attached to the release

#### Maven Dependency

To use the plugin as a dependency in your Maven project:

1. Add GitHub Packages as a repository in your pom.xml:
   ```xml
   <repositories>
     <repository>
       <id>github</id>
       <url>https://maven.pkg.github.com/crownforge/floofyfollow</url>
     </repository>
   </repositories>
   ```

2. Add the plugin as a dependency:
   ```xml
   <dependency>
     <groupId>org.crownforge</groupId>
     <artifactId>floofyfollow</artifactId>
     <version>1.0.0</version>
   </dependency>
   ```

3. Configure authentication for GitHub Packages in your `~/.m2/settings.xml`:
   ```xml
   <settings>
     <servers>
       <server>
         <id>github</id>
         <username>YOUR_GITHUB_USERNAME</username>
         <password>YOUR_GITHUB_TOKEN</password>
       </server>
     </servers>
   </settings>
   ```

### Creating a New Release

To create a new release:

1. Make your changes and merge them to the main branch
2. Create and push a tag with semantic versioning:
   ```bash
   git tag v1.0.0
   git push origin v1.0.0
   ```
3. GitHub Actions will automatically:
   - Build the plugin
   - Create a GitHub Release with the JAR file
   - Publish the package to GitHub Packages

### Manual Build

If you prefer to build the plugin manually, you can use Maven:

```bash
# Clone the repository
git clone https://github.com/crownforge/floofyfollow.git
cd floofyfollow

# Build the plugin
mvn clean package
```

The compiled plugin will be in the `target` directory as `floofyfollow-1.0.0.jar`.

## Installation

1. Stop your Minecraft server
2. Copy the `floofyfollow-1.0.0.jar` file to your server's `plugins` directory
3. Start your server
4. The plugin will be automatically loaded

## Compatibility

- Works with Spigot and Paper
- Tested with Minecraft 1.19.4
- Java 8 or higher required

## License

This project is open source and available under the MIT License.
