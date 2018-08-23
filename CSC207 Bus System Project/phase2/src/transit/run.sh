#!/bin/bash
# Run the UI

cd ui/
rm sources.txt
rm *.class
find -name "*.java" > sources.txt
javac @sources.txt
cd -

java ui.TitleScreen
