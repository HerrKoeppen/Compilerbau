package Compiler;

import java.util.ArrayList;

public class Lexer {

	/**
	 * Die Zeichenkette wird von links nach rechts abgearbeitet. Hier ist
	 * gespeichert, das wievielte Zeichen gerade angesehen wird:
	 */
	private int position;

	/**
	 * Liste zur Speicherung der ermittelten Tokens
	 */
	private ArrayList<Token> tokenListe;

	/**
	 * Wir speichern den Programmtext in einem Attribut der Klasse, damit er
	 * nachher nicht von Methode zu Methode weitergereicht werden muss.
	 */
	private String text;

	/**
	 * 
	 * @param text
	 *            Programmtext, der in Tokens zerlegt werden soll
	 */
	public Lexer(String text) {

		this.text = text;

		position = 0; // Wir beginnen mit dem Zeichen ganz links

		tokenListe = new ArrayList<Token>(); // Liste zur Aufnahme der gelesenen
												// Tokens instanzieren

	}

	/**
	 * Zerlegt den Programmtext, der dem Konstruktor �bergeben wurde
	 * 
	 * @return Liste mit Tokens
	 * @throws Exception
	 *             Eine Exception wird ausgel�st, wenn der Programmtext Passagen
	 *             enth�lt, aus denen keine Token gebildet werden k�nnen.
	 */
	public ArrayList<Token> lex() throws Exception {

		/**
		 * Wiederholung, die den Programmtext von links nach rechts abarbeitet:
		 */
		while (position < text.length()) {

			/**
			 * peek() liest das naechste Zeichen im Programmtext, erhoeht die
			 * Variable position aber nicht. Ruft man peek() mehrmals
			 * hintereinander auf, liefert es also immer dasselbe Zeichen.
			 */
			char c = peek();

			if (istZiffer(c)) {
				lexZahl();
			} else if (istBuchstabe(c)) {
				lexText();
			} else {
				switch (c) {
				case '+':
					addToken(TokenType.plus);
					break;
				case '-':
					addToken(TokenType.minus);
					break;
				case '*':
					addToken(TokenType.mal);
					break;
				case '/':
					addToken(TokenType.geteilt);
					break;
				case '(':
					addToken(TokenType.klammerAuf);
					break;
				case ')':
					addToken(TokenType.klammerZu);
					break;
				case '{':
					addToken(TokenType.geschweifteKlammerAuf);
					break;
				case '}':
					addToken(TokenType.geschweifteKlammerZu);
					break;
				case ';':
					addToken(TokenType.strichpunkt);
					break;
				case '<':
					if(peek(1) == '='){
						addToken(TokenType.kleinerGleich);
						position++;
					} else {
						addToken(TokenType.kleinerAls);
					}
					break;
				case '>':
					if(peek(1) == '='){
						addToken(TokenType.groesserGleich);
						position++;
					} else {
						addToken(TokenType.groesserAls);
					}
					break;
				case '!':
					if(peek(1) == '='){
						addToken(TokenType.nichtGleich);
						position++;
					} else {
						throw new Exception("Das Zeichen = wird erwartet.");
					}
					break;
				case '=':
					if(peek(1) == '=')
                                        {
						addToken(TokenType.gleich);
						position++;
					} 
                                        else 
                                        {
						addToken(TokenType.zuweisen);
					}
					break;

				case ' ':
				case '\n':
				case '\r':
					// Leerzeichen werden einfach �berlesen
					break;
				default:
					throw new Exception("Der Lexer kann mit dem Zeichen '" + c
							+ "' an Position " + position + " nichts anfangen.");
				}

				position++; // Das von peek() oben gelesene Zeichen ist
							// verarbeitet, also jetzt das n�chste holen.

			}

		}

		return tokenListe;

	}

	/**
	 * Die Methode lexVariable geht davon aus, dass das naechste zu verarbeitende
	 * Zeichen ein Buchstabe ist. Solange weitere Buchstaben oder Ziffern
	 * kommen, liest sie sie und setzt sie zu einem Text zusammen.
	 */
	private void lexText() {

		String text = "";

		do {
			char c = peek();
			text += c;
			position++;
		} while (istBuchstabe(peek()) || istZiffer(peek()) || peek() == '_');

		switch (text) {

		case "while":
			tokenListe.add(new Token(TokenType.whileSchleife));
			break;
		case "print":
			tokenListe.add(new Token(TokenType.printWord));
			break;
                case "else":
                        if(text == " if")
                        {
                            
                        }
                        
		default:
			tokenListe.add(new Token(text));
			break;
		}

	}

	/**
	 * Die Methode lexZahl liest eine Zahl
	 */
	private void lexZahl() 
        {
                boolean fl=false;
		String zahlAlsString = "";

		do 
                {
			char c = peek();
			zahlAlsString += c;
                        if (c=='.')
                        {
                            fl=true;    
                        }
			position++;
		} while (istZiffer(peek()) || peek() == '.');
                if (fl=true)
                {
                    float kZahl = Float.parseFloat(zahlAlsString);
                    tokenListe.add(new Token(kZahl));
                }
                else
                {
                    int gZahl = Integer.parseInt(zahlAlsString);
                    tokenListe.add(new Token(gZahl));
                }              
	}

	/**
	 * F�gt der tokenListe das �bergebene Token hinzu
	 * 
	 * @param tokenType
	 */
	private void addToken(TokenType tokenType) {
		tokenListe.add(new Token(tokenType));

	}

	/**
	 * peek() liest das naechste Zeichen im Programmtext, erhoeht die Variable
	 * position aber nicht. Ruft man peek() mehrmals hintereinander auf, liefert
	 * es also immer dasselbe Zeichen.
	 * 
	 * @return n�chstes Zeichen im Programmtext
	 */
	private char peek() {
		if (position < text.length()) {
			return text.charAt(position);
		} else {
			return (char) 0;
		}
	}

	/**
	 * peek(n) liest das Zeichen im Programmtext an (aktuelle Position + n). Die
	 * aktuelle Position (Attribut position) wird nicht ver�ndert.
	 * 
	 * @return Das Zeichen, das n Zeichen weiter steht als die aktuelle Position
	 */
	private char peek(int n) {
		if (position + n < text.length()) {
			return text.charAt(position + n);
		} else {
			return (char) 0;
		}
	}

	/**
	 * 
	 * @param c
	 *            beliebiges zeichen
	 * @return true, falls c eine Ziffer ist
	 */
	private boolean istZiffer(char c) {
		return c >= '0' && c <= '9';
	}

	/**
	 * 
	 * @param c
	 *            beliebiges Zeichen
	 * @return true, falls c ein Buchstabe ist
	 */
	private boolean istBuchstabe(char c) {
		return c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z';
	}

	/**
	 * Vorsicht: Die �bergebene Liste ist nur dann gef�llt, wenn zuvor die
	 * Methode lex aufgerufen wurde.
	 * 
	 * @return Liste mit den Tokens, in die der Lexer den Programmtext zerlegt
	 *         hat.
	 */
	public ArrayList<Token> getTokenListe() {
		return tokenListe;
	}

	/**
	 * Nur zu Debuggingzwecken
	 */
	@Override
	public String toString() {

		String s = "";

		for (Token token : tokenListe) {
			s += token.toString() + " ";
		}

		if (!s.isEmpty()) {
			s = s.substring(0, s.length() - 1);
		}

		return s;

	}

}
