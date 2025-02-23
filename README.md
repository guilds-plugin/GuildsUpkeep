# GuildsUpkeep

GuildsUpkeep is a Minecraft plugin designed to work with the Guilds plugin. It provides an upkeep system that periodically pulls money from all guilds as a form of maintenance. If a guild does not have enough money in their bank balance, it will be declared bankrupt and deleted.

## Features

- Configurable upkeep interval (default is 1 day).
- Configurable amount to pull from each guild during upkeep.
- Tracks the last time upkeep was performed.
- Deletes guilds that cannot afford the upkeep and broadcasts a message to the server.

## Configuration

The configuration file (`config.yml`) allows you to set the upkeep interval, amount, and track the last upkeep time.

```yaml
upkeep:
  interval: 86400 # in seconds (default 1 day)
  amount: 1000 # amount to pull each time
  last_upkeep: 0 # timestamp of the last upkeep
```

## Installation
1) Download the GuildsUpkeep plugin jar file.
2) Place the jar file in the `plugins` directory of your Minecraft server.
3) Start the server to generate the default configuration file.
4) Edit the `config.yml` file in the `plugins/GuildsUpkeep` directory to customize the upkeep settings.
5) Restart the server to apply the changes.

## Usage
Once installed and configured, the plugin will automatically perform upkeep at the specified interval. It will pull the configured amount from each guild's bank balance and handle bankrupt guilds accordingly.

## Dependencies
- [Guilds](https://github.com/guilds-plugin/Guilds) plugin version 3.5.7.0 or higher.
- Spigot API version 1.21.4-R0.1-SNAPSHOT or higher.