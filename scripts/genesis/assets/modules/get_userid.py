# Script to scrape the user ID from the wolfram tones site.
# NOTE: It was discovered wolfram uses the same ID across computers.
# At least for now.  So run this once manually to get the user id
# and then feed that into the automated script
# Karim
import urllib
import requests

# Retrieving this file provides the userid, burried within
url = "http://tones.wolfram.com/js/bundle.js"
response = requests.get(url)
stringBlob = response.text

# jump to the property in the massive amount of text
index = stringBlob.find("user-");
stringBlob = stringBlob[index:]

# find ending character, finish extracting
index = stringBlob.find("/");
stringBlob = stringBlob[0:index]
print ("User ID: ", stringBlob)


