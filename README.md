# Personal Task Manager

🚀 A simple command-line tool to manage your tasks (application built with Java and Maven) efficiently.

## Features

- ✅ Add, complete, and delete tasks
- ✅ List all tasks with status indicators
- ✅ Interactive menu and direct command modes
- ✅ Automatic file persistence
- ✅ Cross-platform support (Windows, Linux, Mac)
- ✅ Professional Maven build system
- ✅ System-wide installation

## Requirements

- Java 17 or higher
- Maven 3.6 or higher (for building from source)

## Quick Start

### For Users (Pre-built JAR)

1. Download the latest `TaskManager-1.0.0.jar` from releases
2. Run: `java -jar TaskManager-1.0.0.jar list`

### For Developers (Build from Source)

```bash
git clone https://github.com/yourusername/TaskManager.git
cd TaskManager
mvn package
java -jar target/TaskManager-1.0.0.jar list
```

### System Installation (Windows)

```bash
git clone https://github.com/yourusername/TaskManager.git
cd TaskManager
.\install.bat
```

After installation, use `task` from anywhere:

## Usage

### Interactive Mode
```bash
task
```

### Direct Commands
```bash
task add "Buy groceries"
task list
task complete 1
task delete 2
task help
```

## Project Structure

```
TaskManager/
├── src/main/java/com/taskmanager/  # Java source code
├── src/main/resources/             # Configuration files
├── pom.xml                         # Maven configuration
├── install.bat                     # Windows installation script
└── README.md                       # This file
```

## Building

```bash
mvn compile      # Compile only
mvn package      # Build JAR
mvn clean        # Clean build files
```

## Contributing

Pull requests are welcome! For major changes, please open an issue first.

## License

This project is licensed under the MIT License - see the LICENSE file for details.
