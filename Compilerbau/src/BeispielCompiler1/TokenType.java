package lexer;

public enum TokenType {
	zahl, text, plus, minus, mal, geteilt, klammerAuf, klammerZu, 
	
	/**
	 * Nur als Knotentyp für Knoten des Syntaxbaums:
	 */
	negation
}
