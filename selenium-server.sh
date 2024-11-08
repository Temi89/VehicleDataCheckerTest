#!/bin/bash

# Download Selenium Server if not already present
if [ ! -f selenium-server-4.26.0.jar ]; then
    echo "Downloading Selenium Server..."
    curl -L https://github.com/SeleniumHQ/selenium/releases/download/selenium-4.26.0/selenium-server-4.26.0.jar -o selenium-server-4.26.0.jar
fi

# Start Selenium Server
echo "Starting Selenium Server..."
java -jar selenium-server-4.26.0.jar standalone --host localhost --port 4444
