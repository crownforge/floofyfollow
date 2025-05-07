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

### Automatic Build (GitHub Actions)

This repository is configured with GitHub Actions to automatically build the plugin whenever changes are pushed. To get the latest build:

1. Go to the "Actions" tab in the GitHub repository
2. Click on the latest successful workflow run
3. Scroll down to the "Artifacts" section
4. Download the "FloofyFollow" artifact which contains the compiled JAR file

### Manual Build

If you prefer to build the plugin manually, you can use Maven:

```bash
# Clone the repository
git clone https://github.com/crownforge/floofyfollow.git
cd floofyfollow

# Build the plugin
mvn clean package
```

The compiled plugin will be in the `target` directory as `floofyfollow-1.0-SNAPSHOT.jar`.

## Installation

1. Stop your Minecraft server
2. Copy the `floofyfollow-1.0-SNAPSHOT.jar` file to your server's `plugins` directory
3. Start your server
4. The plugin will be automatically loaded

## Compatibility

- Works with Spigot and Paper
- Tested with Minecraft 1.19.4
- Java 8 or higher required

## License

This project is open source and available under the MIT License.
