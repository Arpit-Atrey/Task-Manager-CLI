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

# Auto-update PATH in shell config
SHELL_CONFIG=""
if [ -f "$HOME/.bashrc" ]; then
    SHELL_CONFIG="$HOME/.bashrc"
elif [ -f "$HOME/.zshrc" ]; then
    SHELL_CONFIG="$HOME/.zshrc"
fi

if [ -n "$SHELL_CONFIG" ]; then
    # Check if PATH entry already exists
    if ! grep -q "$HOME/.local/bin" "$SHELL_CONFIG"; then
        echo "" >> "$SHELL_CONFIG"
        echo "# Added by TaskManager installer" >> "$SHELL_CONFIG"
        echo "export PATH=\"\$HOME/.local/bin:\$PATH\"" >> "$SHELL_CONFIG"
        echo "PATH updated in $SHELL_CONFIG"
    else
        echo "PATH already configured in $SHELL_CONFIG"
    fi
    
    echo ""
    echo "Installation complete! Restart your terminal or run:"
    echo "source $SHELL_CONFIG"
    echo ""
    echo "Then you can use: task list"
else
    echo "Please add $HOME/.local/bin to your PATH manually"
    echo "Add this line to your shell config (~/.bashrc or ~/.zshrc):"
    echo "export PATH=\"\$HOME/.local/bin:\$PATH\""
fi
