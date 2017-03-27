package lexer;

public enum TokenType {
	kZahl, gZahl, liste, text, zeichen, plus, minus, mal, geteilt, klammerAuf, klammerZu, 
	geschweifteKlammerAuf, geschweifteKlammerZu,
	whileSchleife, printWord,
	kleinerAls, groesserAls, gleich, kleinerGleich, groesserGleich, nichtGleich,
	zuweisen,
	bool,
	strichpunkt,
        klaus,
        hole,
        falls, sonst, ansonsten,
	
	/**
	 * Nur als Knotentyp f�r Knoten des Syntaxbaums:
	 */
	negation
}
