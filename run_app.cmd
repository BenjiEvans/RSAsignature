@echo off 
CALL build.cmd
set app=%1
IF "%app%"=="authenticate" (
    java -classpath ./build Main 
)Else IF "%app%"=="key_gen" (
    java -classpath ./build KeyGen
)
