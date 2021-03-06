Genesis App v1.0, Karim Sultan, October 2017
********************************************

Genesis autocreates:
	* Band Name
	* Genre
	* Album Name (which is also the title track)
	* Units and Unit Price
and:
	* A random, relevant customized CD cover (JPG) from Upsplash
	* Fonts from the OpenFont project
	* Lyrics
	* Random AI created music for the genre from Wolfram Tones
	* Text to Speech in one of several voices via IBM Watson TTS
	* Mixes it into a 15s sample clip (MP3)
and finally:
	* An SQL script to make and populate the database


To run the Genesis app, you will need Python 2.7 and Node.js.
(The main script is in python, the modules are in Javascript.  Why, you ask?
Because this was a voyage of education, and I wanted to learn both and 
how to mix them.)

Genesis requires certain python libraries to be installed. 
Use "pip install [library]" to set them up.  These libraries
include:

	* requests
	* json
	* Naked
	* PIL
	* Pydub
	* Watson_Developer_Cloud

From a command line:

[prompt]> python genesis.py [CDs]

where CDs = number of CDs to generate from 1 - 500.

CD album covers (JPG) go into assets/covers/
CD title track samples (MP3) go into assets/samples/

The SQL script to set up the entire database and populate it with the
autogenerated CDs is in:

assets/sql/make_seedy_db.sql

In the MySQL workbench, select "File | Run SQL Script..." and run it.

FINAL NOTE
==========
Yes, you can put in python genesis.py 500 and have it run for 500 iterations
and generate you a LOT of content.  But I suggest you use 1 - 5 for you
record counts when fooling around.

The reason is that this is a mashup and we are using other freely available
web resources to create the end result - so let's be good netizens and not
abuse their resources.

- Karim
