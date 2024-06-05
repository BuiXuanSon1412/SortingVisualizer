#!/bin/bash
# Delete class files
find "bin" -type f -name "*.class" -delete
# Compile Java source files
find src -name "*.java" -exec javac -d bin {} +
# Execute the Java application
java -cp bin Main
