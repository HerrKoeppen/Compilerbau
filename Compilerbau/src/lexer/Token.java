package lexer;

public class Token {
	
	private TokenType tokenType; 		// Art des Tokens (z.B. zahl, klammerAuf usw.)
	private int gZahl;			// Wird nur belegt, wenn tokenType == ganze Zahl
	private float kZahl;                    // Wird nur belegt, wenn tokenType == Kommazahl
        private String text;                    // Wird nur belegt, falls tokenType == bezeichner
	
        public Token(float kZahl)
        {
            this.kZahl = kZahl;
            tokenType = TokenType.kZahl;
        }
        
	public Token(int gZahl)
        {
            this.gZahl = gZahl;
            tokenType = TokenType.gZahl;
	}
	
	public Token(String text)
        {
            this.text = text;
            tokenType = TokenType.text;
	}
        
	
	public Token(TokenType tokenType)
        {
            this.tokenType = tokenType;
	}

	public TokenType getTokenType() 
        {
            return tokenType;
	}

	public int getGZahl() 
        {
            return gZahl;
	}

	public String getText() 
        {
            return text;
	}
        
        public float getKZahl()
        {
            return kZahl;
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
		case text:
			s += "[" + text + "]";
			break;
		default:
			break;
		}
		
		return s;
		
	}
	
}
