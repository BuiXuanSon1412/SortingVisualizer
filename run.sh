#!/bin/bash
# Delete class files
find "target" -type f -name "*.class" -delete
# Compile Java source files
find src -name "*.java" -exec javac -d target {} +
# Execute the Java application
java -cp target Main
