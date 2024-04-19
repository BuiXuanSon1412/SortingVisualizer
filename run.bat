@echo off
REM Delete class files
del /S /Q target\*.java
REM Compile Java source files
for /R src %%F in (*.java) do javac -d target "%%F"
REM Execute the Java application
java -cp target Main