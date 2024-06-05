@echo off
REM Delete class files
del /S /Q bin\*.class
REM Compile Java source files
for /R src %%F in (*.java) do javac -d bin "%%F"
REM Execute the Java application
java -cp bin Main