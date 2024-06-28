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
- `/clan kick <PLAYER_NAME>` - Deletes the specified player from your clan.
- `/clan leave` - Deletes you from current clan.
- `/clan disband` - Deletes clan and kick all members.

## Build
There is no releases yet, because you need to specify your database properties in files

1. **Clone the Pproject**:
   - Download the latest version of the plugin jar file from the releases page.
   ```bash
   git clone https://github.com/NedNedov228/SuperClans.git
   cd SuperClans

2. **Configuration**:
   - Create a `config.properties` file in the `src/main/resources` directory of your project.
   - Add your database properties to the `config.properties` file.

   Example of `config.properties`:
   ```properties
   database.url=jdbc:mysql://localhost:3306/minecraft
   database.username=root
   database.password=your_password

3. **Build**:

   ```bash
   ./mwnv package

## Installation

**Place the Plugin**:
   - Place the downloaded jar file in the `plugins` folder of your Paper 1.21 server.


