package lexer;

public class Token {
	
	private TokenType tokenType; 		// Art des Tokens (z.B. zahl, klammerAuf usw.)
	private int gZahl;			// Wird nur belegt, wenn tokenType == ganze Zahl
	private float kZahl;                    // Wird nur belegt, wenn tokenType == Kommazahl
        private String string;                    // Wird nur belegt, falls tokenType == bezeichner
	
	public Token(int gZahl)
        {
		this.gZahl = gZahl;
		tokenType = TokenType.gZahl;
	}
	
	public Token(String string)
        {
		this.string = string;
		tokenType = TokenType.string;
	}
        
        public Token(float kZahl)
        {
            this.kZahl = kZahl;
            tokenType = TokenType.kZahl;
        }
	
	public Token(TokenType tokenType)
        {
		this.tokenType = tokenType;
	}

	public TokenType getTokenType() 
        {
		return tokenType;
	}

	public int getZahl() 
        {
		return gZahl;
	}

	public String getText() 
        {
		return string;
	}
	
	/**
	 * Die toString()-Methode dient nur Debuggingzwecken
	 */
	@Override
	public String toString() {
		
		String s = "" + tokenType;
		
		switch (tokenType) 
                {
		case gZahl:
			s += "[" + gZahl + "]";
			break;
                case kZahl:
                        s += "[" + kZahl + "]";
		case string:
			s += "[" + string + "]";
			break;
		default:
			break;
		}
		
		return s;
		
	}
	
}
