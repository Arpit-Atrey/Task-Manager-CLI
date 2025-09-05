#!/bin/bash
# TaskManager - Linux/Mac Installation Script

echo "Building TaskManager..."
mvn package -q
if [ $? -ne 0 ]; then
    echo "Build failed!"
    exit 1
fi

echo "Installing to system..."
INSTALL_DIR="$HOME/.local/bin"
TASK_DIR="$HOME/.local/share/TaskManager"

# Create directories
mkdir -p "$INSTALL_DIR"
mkdir -p "$TASK_DIR"

echo "Cleaning old versions..."
rm -f "$TASK_DIR"/TaskManager-*.jar

echo "Copying new version..."
cp target/TaskManager-1.0.0.jar "$TASK_DIR/"

echo "Creating task command..."
cat > "$INSTALL_DIR/task" << 'EOF'
#!/bin/bash
java -jar "$HOME/.local/share/TaskManager/TaskManager-1.0.0.jar" "$@"
EOF

chmod +x "$INSTALL_DIR/task"

echo "TaskManager installed successfully!"
echo "Make sure $HOME/.local/bin is in your PATH"
echo "Add this to your ~/.bashrc or ~/.zshrc:"
echo "export PATH=\"\$HOME/.local/bin:\$PATH\""
echo ""
echo "After updating PATH, you can use: task list"
