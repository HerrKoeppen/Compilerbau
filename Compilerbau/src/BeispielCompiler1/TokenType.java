package lexer;

public enum TokenType {
	zahl, text, plus, minus, mal, geteilt, klammerAuf, klammerZu, 
	
	/**
	 * Nur als Knotentyp f�r Knoten des Syntaxbaums:
	 */
	negation
}
