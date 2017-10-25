// Genre Generator 
// 171021 - Karim Sultan 

// The genres we will accept
var genres = ["rock", "country", "hiphop", "blues", "electronic", "rnb", "jazz", "latin", "world"];

// Cumulative percentage, associated to genres, must equal 100
var weights = [22, 39, 54, 65, 75, 85, 94, 98, 100];

// Default genre
var genre = "rock";

// Roll pecetnage (1-100)
var roll = Math.floor(Math.random() * 100 + 1);

var exit = false;
var i = 0;
while (exit  == false)
{
	if  (roll < weights[i])
	{
		genre = genres[i];
		exit = true;
	}
	else
	{
		i++;
	}

	// Sanity check
	if (i>=genres.length)
		exit = true;
}

console.log(genre);


