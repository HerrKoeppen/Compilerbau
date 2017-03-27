package lexer;

public enum TokenType {
	kZahl, gZahl, text, plus, minus, mal, geteilt, klammerAuf, klammerZu, 
	geschweifteKlammerAuf, geschweifteKlammerZu,
	whileSchleife, printWord,
	kleinerAls, groesserAls, gleich, kleinerGleich, groesserGleich, nichtGleich,
	zuweisen,
	trueKeyword, falseKeyword,
	strichpunkt,
	
	/**
	 * Nur als Knotentyp f�r Knoten des Syntaxbaums:
	 */
	negation
}
