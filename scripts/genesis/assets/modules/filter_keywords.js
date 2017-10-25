// Keyword Filter
// 171021 - Karim Sultan

// Filters out meaningless words from keywords

// The list of filter words.  Enter  in any case; they are all converted to lower case afterwards
var filter = ["I", "am", "a", "the", "of",  "an", "and", "or", "your", "my",
   "lets", "for", "he", "she", "me", "about", "on", "off", "we", "were", "in", "out",
   "Im", "hes", "shes", "that", "this", "there", "here", "is", "are", "you",
   "him", "her", "his", "hers", "het", "to", "too", "fo", "into", "if", "then",
   "cannot", "not", "else", "while"];

// Prefilter
for (var i=0; i<filter.length; i++)
   filter[i] = filter[i].toLowerCase();

// Obtain unfiltered list of keywords
var keywords = process.argv[2];

// Sanity test
if (!keywords)
	keywords = "Music"

// Keywords format: "Cap One Two Three Four"
// Set it all to lowwercase
keywords = keywords.toLowerCase();

// Remove commas
keywords = keywords.replace(/\,/g,"");
keywords = keywords.replace(/\'/g,"");

// Split
words = keywords.split(" ");

// Cycle through array
var newwords = new Array();
for (var i=0; i<words.length; i++)
{
   if (!filter.includes(words[i]))
      newwords.push(words[i].charAt(0).toUpperCase() + words[i].slice(1));
}

// Rejoin output
keywords = newwords.toString();

// Sanity check again - for worse case scenario where all words were
// filtered out
if (!keywords)
	keywords = "Music";

// Output the filtered keywords
console.log(keywords);


