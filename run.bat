@echo off
cd src
javac -d ..\bin algorithms/*.java util/*.java MainGUI.java
cd ../bin
java MainGUI