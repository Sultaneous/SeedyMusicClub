// Font Selector
// 171021 - Karim Sultan

// The list of available fonts
// These fonts came from an Open Font repo and are under openfont license (free to use)
var fonts = [
"AtlanticBentley.ttf",
"BladeRunner.ttf",
"Blazed.ttf",
"BlockStock.ttf",
"ChopinScript.ttf",
"Dopestyle.ttf",
"Hemogoblin.ttf",
"Hesitation.ttf",
"Hitchblock.ttf",
"KroyFont.ttf",
"MidnightOctober.ttf",
"MotionPicture.ttf",
"PhiladelphianGothic.ttf",
"Rodrigues.ttf",
"Sequel.ttf",
"Skybird.ttf",
"Spinner.ttf",
"SpookyHalloween.ttf",
"Swordtooth.ttf",
"TerrorBabble.ttf",
"Waltograph.ttf",
"Zinedka.ttf",
"AtlanticBentley.ttf",
"BladeRunner.ttf",
"Blazed.ttf",
"BlockStock.ttf",
"ChopinScript.ttf",
"Dopestyle.ttf",
"Hemogoblin.ttf",
"Hesitation.ttf",
"Hitchblock.ttf",
"KroyFont.ttf",
"MidnightOctober.ttf",
"MotionPicture.ttf",
"PhiladelphianGothic.ttf",
"Rodrigues.ttf",
"Sequel.ttf",
"Skybird.ttf",
"Spinner.ttf",
"SpookyHalloween.ttf",
"Swordtooth.ttf",
"TerrorBabble.ttf",
"Waltograph.ttf",
"Zinedka.ttf",
"20db.otf",
"3Dumb.ttf",
"AfterShock.ttf",
"AlphaEcho.ttf",
"Asset.ttf",
"Concert.ttf",
"Germania.ttf",
"NewWaltDisney.ttf",
"Oleo.ttf",
"Rye.ttf",
"Sarina.ttf"
];

// Default font
var font = fonts[0];

// Roll random font
var roll = Math.floor(Math.random() * fonts.length);
font = fonts[roll]

// Output the selected font
console.log(font);


