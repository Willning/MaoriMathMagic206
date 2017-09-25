#!/bin/bash

javac -cp .:src src/gui/Main.java -d bin &&
java -ea -cp bin gui.Main