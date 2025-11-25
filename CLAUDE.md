# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a JADE (Java Agent Development Framework) multi-agent system project. JADE is a framework for building distributed multi-agent applications in Java that comply with FIPA (Foundation for Intelligent Physical Agents) standards.

## Project Structure

- **src/**: Java source code
  - `App.java`: Main entry point
  - `projectsma/ProjectAgent.java`: Custom JADE agent implementation extending `jade.core.Agent`
- **lib/**: Dependencies including `jade.jar`
- **bin/**: Compiled output directory
- **APDescription.txt**: JADE platform description file (generated at runtime)
- **MTPs-Main-Container.txt**: Message Transport Protocol information (generated at runtime)

## Build and Compilation

The project uses javac for compilation with the following configuration:
- Source path: `src/`
- Output path: `bin/`
- Referenced libraries: `lib/**/*.jar` (particularly `jade.jar`)

**Compile the project:**
```bash
javac -d bin -cp "lib/*" src/**/*.java
```

**Compile a single file:**
```bash
javac -d bin -cp "lib/*" src/projectsma/ProjectAgent.java
```

## Running the Application

**Run the main application:**
```bash
java -cp "bin;lib/*" App
```

**Run with JADE framework:**
The JADE framework requires specific startup arguments to initialize the agent platform:
```bash
java -cp "bin;lib/*" jade.Boot -gui
```

This starts the JADE main container with a GUI for agent management.

## Architecture Notes

**Agent-based Architecture:**
- `ProjectAgent` extends `jade.core.Agent` and overrides the `setup()` method
- The `setup()` method is called when the agent is created and should contain initialization logic
- Agents communicate through FIPA-compliant message passing

**JADE Platform:**
- Uses HTTP-based MTP (Message Transport Protocol) on port 7778
- Main container handles agent lifecycle and message routing
- Configuration is auto-generated in APDescription.txt and MTPs-Main-Container.txt

## Development Notes

- When creating new agents, extend `jade.core.Agent` and implement agent behavior in the `setup()` method or by adding behaviors
- JADE requires the complete classpath including jade.jar to be present for both compilation and execution
- The `.vscode/settings.json` is configured with JADE library paths for VS Code Java support
