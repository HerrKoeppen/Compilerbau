package BeispielCompiler2;

public enum TokenType {
	zahl, text, plus, minus, mal, geteilt, klammerAuf, klammerZu, 
	geschweifteKlammerAuf, geschweifteKlammerZu,
	whileKeyword, printKeyword,
	kleiner, groesser, identisch, kleinergleich, groessergleich, ungleich,
	zuweisung,
	trueKeyword, falseKeyword,
	strichpunkt,
	
	/**
	 * Nur als Knotentyp f�r Knoten des Syntaxbaums:
	 */
	negation
}
