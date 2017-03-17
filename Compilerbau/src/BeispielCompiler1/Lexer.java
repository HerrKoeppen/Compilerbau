package lexer;

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
	 * Zerlegt den Programmtext, der dem Konstruktor übergeben wurde
	 * 
	 * @return Liste mit Tokens
	 * @throws Exception
	 *             Eine Exception wird ausgelöst, wenn der Programmtext Passagen
	 *             enthält, aus denen keine Token gebildet werden können.
	 */
	public ArrayList<Token> lex() throws Exception {

		/**
		 * Wiederholung, die den Programmtext von links nach rechts abarbeitet:
		 */
		while (position < text.length()) {

			/**
			 * peek() liest das nächste Zeichen im Programmtext, erhöht die
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
				case ' ':
				case '\n':
				case '\r':
					// Leerzeichen werden einfach überlesen
					break;
				default:
					throw new Exception("Der Lexer kann mit dem Zeichen '" + c
							+ "' an Position " + position + " nichts anfangen.");
				}

				position++; // Das von peek() oben gelesene Zeichen ist
							// verarbeitet, also jetzt das nächste holen.

			}

		}

		return tokenListe;

	}

	/**
	 * Die Methode lexVariable geht davon aus, dass das nächste zu verarbeitende
	 * Zeichen ein Buchstabe ist. Solange weitere Buchstaben oder Ziffern
	 * kommen, liest sie sie und setzt sie zu einem Variablennamen zusammen.
	 */
	private void lexText() {

		String variablenBezeichner = "";

		do {
			char c = peek();
			variablenBezeichner += c;
			position++;
		} while (istBuchstabe(peek()) || istZiffer(peek()) || peek() == '_');

		tokenListe.add(new Token(variablenBezeichner));

	}

	/**
	 * Die Methode lexZahl liest eine Zahl
	 */
	private void lexZahl() {

		String zahlAlsString = "";

		do {
			char c = peek();
			zahlAlsString += c;
			position++;
		} while (istZiffer(peek()) || peek() == '.');

		/**
		 * Hier machen wir es uns leicht und lassen Java den String in eine Zahl
		 * konvertieren. Die Methode parseDouble ist für sich genommen natürlich
		 * auch ein Lexer.
		 */
		double zahl = Double.parseDouble(zahlAlsString);

		tokenListe.add(new Token(zahl));

	}

	/**
	 * Fügt der tokenListe das übergebene Token hinzu
	 * 
	 * @param tokenType
	 */
	private void addToken(TokenType tokenType) {
		tokenListe.add(new Token(tokenType));

	}

	/**
	 * peek() liest das nächste Zeichen im Programmtext, erhöht die Variable
	 * position aber nicht. Ruft man peek() mehrmals hintereinander auf, liefert
	 * es also immer dasselbe Zeichen.
	 * 
	 * @return nächstes Zeichen im Programmtext
	 */
	private char peek() {
		if (position < text.length()) {
			return text.charAt(position);
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
	 * Vorsicht: Die übergebene Liste ist nur dann gefüllt, wenn zuvor die
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
