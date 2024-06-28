# Simple Clans Plugin for Minecraft Paper 1.21

## Overview

This project is a Minecraft plugin for Paper 1.21 that introduces a simple clans system. Players can create and manage clans within the game using the provided commands. To use this plugin, you need to configure your database properties in the `config.properties` file.

## Features

- Create and manage clans in-game.
- Add players to your clan.

## Commands

- `/clans` - Displays the list of available clans.
- `/clan create <CLAN_NAME>` - Creates a new clan with the specified name.
- `/clan add <PLAYER_NAME>` - Adds the specified player to your clan.

## Installation

1. **Download the Plugin**:
   - Download the latest version of the plugin jar file from the releases page.

2. **Place the Plugin**:
   - Place the downloaded jar file in the `plugins` folder of your Paper 1.21 server.

3. **Configuration**:
   - Create a `config.properties` file in the `src/main/resources` directory of your project.
   - Add your database properties to the `config.properties` file.

   Example `config.properties`:
   ```properties
   database.url=jdbc:mysql://localhost:3306/minecraft
   database.username=root
   database.password=your_password


