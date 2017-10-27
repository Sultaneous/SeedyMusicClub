// Voice Selector
// 171021 - Karim Sultan

// The genres we will accept
var voices = [
 "en-US_MichaelVoice",
 "en-US_AllisonVoice",
 "en-US_LisaVoice",
 "en-GB_KateVoice",
 "de-DE_DieterVoice"];

// Cumulative percentage, associated to genres, must equal 100
var weights = [48, 68, 86, 97, 100];

// Default voice
var voice = voices[0];

// Roll percentage (1-100)
var roll = Math.floor(Math.random() * 100 + 1);

var exit = false;
var i = 0;
while (exit  == false)
{
	if  (roll < weights[i])
	{
		voice = voices[i];
		exit = true;
	}
	else
	{
		i++;
	}

	// Sanity check
	if (i>=voices.length)
		exit = true;
}

// Output the selected voice
console.log(voice);


