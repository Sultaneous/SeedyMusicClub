:: Cleans asset directories of temp and generated content
@echo off



echo "Cleaning output files..."


:: Remove sql files (but not template.data)

echo "Removing generated SQL script(s)..."

del assets\sql\*.sql

:: Remove other working directory contents

echo "Removing generated cover files..."

del assets\covers\*.jpg


echo "Removing generated sample files..."

del assets\samples\*.mp3



echo "Removing generated temporary files..."

del assets\temp\*.jpg
del assets\temp\*.mp3


echo "Done!"

pause

:: NOTE: DO NOT DELETE contents of remaining directories!

