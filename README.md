# Personal Task Manager

📚 A learning project: Building a command-line task management application with Java and Maven.

*This project was created to learn Java programming concepts, Maven build automation, Git workflows, and software development best practices.*

## Learning Objectives

This project demonstrates:

- 🚀 **Object-Oriented Programming** - Classes, encapsulation, inheritance
- 🛠️ **Maven Build System** - Professional Java project structure and dependency management
- 💾 **File I/O** - Data persistence with automatic save/load functionality
- 🔄 **Git Workflow** - Version control, branching, and collaborative development practices
- 💻 **Command-Line Interfaces** - Both interactive and direct command modes
- 🌍 **Cross-Platform Development** - Code that works on Windows, Linux, and Mac
- ⚙️ **Error Handling** - User-friendly error messages and validation
- 📁 **Project Organization** - Professional directory structure and documentation

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

This is primarily a learning project, but suggestions and improvements are welcome! Feel free to:

- 📝 **Open issues** for bugs or feature suggestions
- 🔧 **Submit pull requests** with improvements or fixes
- 💡 **Share feedback** on code structure and best practices
- 🚀 **Use this as inspiration** for your own learning projects

## Learning Journey

Key concepts learned while building this project:
- Setting up Maven projects from scratch
- Implementing proper Java class design patterns
- Managing file persistence and data serialization
- Building cross-platform command-line applications
- Creating professional project documentation
- Using Git for version control and project management

## License

This project is licensed under the MIT License - see the LICENSE file for details.
