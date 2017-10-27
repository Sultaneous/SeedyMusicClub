#!/bin/sh

echo "Cleaning output files..."

# Remove sql files (but not template.data)
echo "Removing generated SQL script(s)..."
rm assets/sql/*sql > /dev/null 2>&1

# Remove other working directory contents
echo "Removing generated cover files..."
rm assets/covers/* > /dev/null 2>&1

echo "Removing generated sample files..."
rm assets/samples/* > /dev/null 2>&1

echo "Removing generated temporary files..."
rm assets/temp/* > /dev/null 2>&1

echo "Done!"

# NOTE: DO NOT DELETE contents of remaining directories!
