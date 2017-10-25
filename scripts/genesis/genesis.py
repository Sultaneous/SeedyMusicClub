# Genesis: CD Catalog Creator
# Karim Sultan
#
# 171001 - Created this script

# Library Imports
from __future__ import print_function
import os
import sys
import urllib
import requests
import json
import shortuuid
import time
from random import randint
from shutil import copyfile
from Naked.toolshed.shell import muterun_js
from PIL import Image
from PIL import ImageFont
from PIL import ImageDraw
from watson_developer_cloud import TextToSpeechV1
from os.path import join, dirname
from pydub import AudioSegment
from datetime import datetime

# Some path constants
path_covers = "assets/covers/"
path_samples = "assets/samples/"
path_sql = "assets/sql/"
path_modules = "assets/modules/"
path_temp = "assets/temp/"
path_fonts ="assets/fonts/"


# This is the genesis function
# Builds a CD!  (Band, title, title tack sample, cover art, lyrics!)

def genesis():
   try:
      # Initialize
      string_imagefile = None;
      string_coverfile = None;

      # First get a unique id we can use for asset generation
      shortuuid.set_alphabet("abcdefghijklmnopqrstuvwxyz")
      my_uuid = shortuuid.uuid()[:6]
      print("Record ID:  ", my_uuid)

      # Generate the band name
      response = muterun_js (path_modules + "generator_band.js")
      string_band = response.stdout.strip()
      print("Band:       ", string_band)

      # Generate the music genre
      response = muterun_js (path_modules + "generator_genre.js")
      string_genre = response.stdout.strip()
      print("Genre:      ", string_genre)

      # Generate the CD title (also used for title track)
      response = muterun_js (path_modules + "generator_title.js", \
       arguments=string_genre)
      string_title = response.stdout.strip()
      print("CD Title:   ", string_title)

      # Filter out meaningless keywords, used for imaage search
      response = muterun_js(path_modules + "filter_keywords.js", \
       arguments="\"" + string_title + "\"")
      string_keywords = response.stdout.strip()
      print("Keywords:   ", string_keywords)

      # Retrieve random, associated image from Unsplash for CD cover
      string_url = "https://source.unsplash.com/600x600/?" + string_keywords
      string_imagefile = path_covers +  "cover_" + my_uuid + ".jpg"
      urllib.urlretrieve (string_url, string_imagefile)

      # Generate the font
      response = muterun_js(path_modules + "generator_font.js")
      string_font = path_fonts + response.stdout.strip()
      print("Font:       ", string_font)

      # Stamp the CD cover with album name (title) on top, band name on bottom
      pil_image = Image.open (string_imagefile)
      pil_draw = ImageDraw.Draw (pil_image)

      # First we need to fit the title to within the width
      size_font = 48
      string_stamp = string_title
      pil_font = ImageFont.truetype(string_font, size_font)
      width, height = pil_draw.textsize (string_stamp, pil_font)

      # This approach, to split the string, works but won't return the proper
      # size (height, width) for the box drawing functions do to a PIL bug.
      # Implemented work around below: font sizing
      #if (width + 13 > pil_image.width):
         # Find the middle, find closest space after midpoint, and make it '\n
         #midpoint = len(string_stamp) / 2
         #index = string_stamp.find(" ", midpoint)
         #string_stamp = string_stamp[:index] + "\n" + string_stamp[index+1:]

      # This is a work around to get around a frustrating PIL bug.
      # Basically, keep reducing font size until title fits on one line.
      while  (width + 33 > pil_image.width):
         size_font -= 1
         pil_font = ImageFont.truetype(string_font, size_font)
         width, height = pil_draw.textsize (string_stamp, pil_font)
         print ("   [Reduced font size to " + str(size_font) + "]")

      # Next we need to put the shadowbox, textbox, and CD title down
      shadowbox = [(13,13), (width + 20, height + 20)]
      textbox = [(8,8), (width + 15, height + 15)]

      # randomize a textbox and a font colour
      colour_textbox = (randint(200,255), randint(200,255), randint(200,255))
      colour_text = (randint(0,128), randint(0,128), randint(0,128))
      pil_draw.rectangle (shadowbox, (10,10,10), (0,0,0))
      pil_draw.rectangle (textbox, colour_textbox, (0,0,0))
      pil_draw.multiline_text((10,10), string_stamp, colour_text, font=pil_font, align="left")

      # Now we can do the band name
      pil_font = ImageFont.truetype(string_font, 32)
      width, height = pil_draw.textsize (string_band, pil_font)
      x = 600-width-40
      y = 600-height-50
      shadowbox = [(x+3,y+3), (x+width + 15, y+height + 15)]
      textbox = [(x-2,y-2), (x+width + 10, y+height + 10)]
      pil_draw.rectangle (shadowbox, (10,10,10), (0,0,0))
      pil_draw.rectangle (textbox, colour_textbox, (0,0,0))
      pil_draw.text((x,y), string_band, colour_text, font=pil_font)

      pil_image.save(string_imagefile, "JPEG")
      print("JPG Cover:  ", string_imagefile)

      # Music is generated from tones.wolfram.com
      # First obtain our genre Wolfram tone NKM genre number
      dictionaryGenre = {
       'rock':'30',
       'country':'65',
       'hiphop':'45',
       'blues':'55',
       'electronic':'40',
       'rnb':'60',
       'jazz':'50',
       'latin':'30',
       'world':'80'}

      # Constant userid (for now)
      # If site changes, then update this user ID accordingly
      # See get_userid.py scritp for how we originally got this number
      # NOTE: NKM = New Kind (of) Music
      string_nkmuser = "user-a13d29f3-43bf-4b00-8e9b-e55639ecde19"
      print("NKM User:   ", string_nkmuser)

      # Use NKM Genre and NKM User ID to get the NKM id
      print("NKM Genre:  ", dictionaryGenre[string_genre])
      string_url = "https://www.wolframcloud.com/objects/" + string_nkmuser + \
       "/NKMNewID?genre=" + dictionaryGenre[string_genre]

      response = requests.get(string_url)
      string_nkmid = response.text.strip("\"")
      print("NKM ID:     ", string_nkmid)

      # Now use the NKM ID to get a 15 sec uniquely generated mp3 clip,
      # based on the band/cd genre
      string_url = "https://www.wolframcloud.com/objects/" + string_nkmuser + \
       "/NKMMusic?id=" + string_nkmid
      string_instrumentalfile = path_temp + "instrumental_" + my_uuid + ".mp3"
      urllib.urlretrieve (string_url, string_instrumentalfile)
      print("MP3 Premix: ", string_instrumentalfile)

      # Create lyrics
      response = muterun_js(path_modules + "generator_lyrics.js")
      string_lyrics = string_title + ". " + response.stdout.strip() + \
       " " + string_title + "."
      print("Lyrics:     ", "Successfully generated")

      # Select the  TTS voice
      response = muterun_js(path_modules + "generator_voice.js")
      string_voice = response.stdout.strip()
      print("Voice:      ", string_voice)

      # Text to Speech is done by IBM's Bluemix
      # Convert vocals to MP3 using Text to Speech
      string_vocalfile = path_temp + "vocal_" + my_uuid + ".mp3"

      # Another web service call, this time to IBM
      text_to_speech = TextToSpeechV1(
          username="0327b6b4-770c-45dc-b7e4-f5861bece38a",
          password="yokEUrmcl3fX",
          x_watson_learning_opt_out=True)

      with open(join(dirname(__file__), string_vocalfile), 'wb') as audio_file:
          audio_file.write(text_to_speech.synthesize(string_lyrics, accept='audio/mp3',
                           voice=string_voice))
      print("MP3 Lyrics: ", string_vocalfile)

      # Mix lyrics onto sample
      sound_instrumental = AudioSegment.from_mp3(string_instrumentalfile)
      sound_vocal = AudioSegment.from_mp3(string_vocalfile)
      string_samplefile = path_samples + "sample_" + my_uuid + ".mp3"

      mix = sound_instrumental.overlay(sound_vocal, position=3000, gain_during_overlay=-12)
      mix.export(string_samplefile, format="mp3")
      print("MP3 Sample: ", string_samplefile)

      # Create random quanity and price
      num_quantity = randint(10,50);
      num_price = randint(4,19) + 0.99
      print("Qty / $:    ", num_quantity, "units @ $", num_price, "each")

   except Exception as e:
      print()
      print("ERROR:" , e)
      print("Tidying up interim work assets...")

      if not string_imagefile is None:
         if os.path.isfile(string_imagefile):
            os.remove(string_imagefile);

      if not string_samplefile is None:
         if os.path.isfile(string_samplefile):
            os.remove(string_samplefile);

      return False

   try:
      # Create INSERT SQL statement
      # Removed date field as the table is designed to add date of creation
      # automatically when a record is entered, and there would be a date
      # lag based on the time between this statement date and when the SQL
      # script is executed
      sql_insert = "INSERT INTO cds\n" + \
      "(\n" + \
      "   title, band, genre, cover, sample, quantity, price\n" + \
      ")\n" + \
      "VALUES\n" + \
      "(\n" + \
      "   \"" + string_title + "\",\n" + \
      "   \"" + string_band + "\",\n" + \
      "   \"" + string_genre + "\",\n" + \
      "   \"" + string_imagefile.replace(path_covers, '') + "\",\n" + \
      "   \"" + string_samplefile.replace(path_samples, '') + "\",\n" + \
      "   \"" + str(num_quantity) + "\",\n" + \
      "   \"" + str(num_price) + "\",\n" + \
      ");\n\n"

      # Create (if necessary) and append SQL
      string_sqlfile = path_sql + "make_seedy_db.sql" 
      if not os.path.isfile(string_sqlfile):
         string_sqlmasterfile = path_sql + "template.data"
         copyfile (string_sqlmasterfile, string_sqlfile)

      with open(string_sqlfile, "a") as my_file:
         my_file.write(sql_insert)

      print("SQL Insert: ", "Appended to SQL script file")
      print(" ")
   except:
      print()
      print("FATAL ERROR:", e)
      print("Tidying up interim work assets...")

      if not string_imagefile is None:
         if os.path.isfile(string_imagefile):
            os.remove(string_imagefile);

      if not string_samplefile is None:
         if os.path.isfile(string_samplefile):
            os.remove(string_samplefile);

      print("Can not recover. Fix SQL manually.  Exiting.")
      print()
      sys.exit(100)
   return True

# Check the parameters
if len(sys.argv) < 2:
   print()
   print("Genesis - CD Creator")
   print()
   print("Syntax: python genesis.py (CDs | max=500)")
   print()
   print("Creates a CD:")
   print(" band, CD title, genre, units @ price,")
   print(" cover art, unique lyrics and MP3 sample for title track,")
   print(" and the SQL script to create a populated database.")
   print()
   sys.exit(1)

# Check parameter
argument = sys.argv[1]
if not argument.isdigit():
   print ("Number of CDs is invalid:",num_records)
   print()
   sys.exit(2)

# Check range
num_records = int(float(argument))
if num_records < 1:
   num_records = 1

if num_records > 500:
   num_records = 500

print("Genesis: Creating", num_records, "CD assets...")

# Make the requested number of records
for record in range(0, num_records):
   print("Record:     ", record + 1, "of", num_records)
   retry = 0;
   result = genesis();

   while not result:
      retry += 1
      print("==>")
      print("Record:     ", record +1, "[Retry", retry, "of 3]")
      result = genesis()

      if (retry == 3):
         print("Can't recover after 3 retries, terminating.");
         print()
         sys.exit(101)

# Success
print("Done!")
print("assets/covers/*              --> contains the JPG cover art")
print("assets/samples/*             --> contains the MP3 samples")
print("assets/sql/make_seedy_db.sql --> contains the SQL script")
print("Note: you can use the clean.sh bash script to reset assets.")
print()
