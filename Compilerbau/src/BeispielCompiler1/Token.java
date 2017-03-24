package BeispielCompiler1;

public class Token {
	
	private TokenType tokenType; 		// Art des Tokens (z.B. zahl, klammerAuf usw.)
	private double zahl;				// Wird nur belegt, wenn tokenType == zahl
	private String text;	// Wird nur belegt, falls tokenType == bezeichner
	
	public Token(double zahl){
		this.zahl = zahl;
		tokenType = TokenType.zahl;
	}
	
	public Token(String text){
		this.text = text;
		tokenType = TokenType.text;
	}
	
	public Token(TokenType tokenType){
		this.tokenType = tokenType;
	}

	public TokenType getTokenType() {
		return tokenType;
	}

	public double getZahl() {
		return zahl;
	}

	public String getText() {
		return text;
	}
	
	/**
	 * Die toString()-Methode dient nur Debuggingzwecken
	 */
	@Override
	public String toString() {
		
		String s = "" + tokenType;
		
		switch (tokenType) {
		case zahl:
			s += "[" + zahl + "]";
			break;
		case text:
			s += "[" + text + "]";
			break;
		default:
			break;
		}
		
		return s;
		
	}
	
}
