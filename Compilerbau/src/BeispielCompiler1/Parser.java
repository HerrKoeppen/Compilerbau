package parser;

import java.util.ArrayList;

import lexer.Token;
import lexer.TokenType;

public class Parser {

	/**
	 * Liste, die vom Lexer kommt
	 */
	private ArrayList<Token> tokenListe;

	/**
	 * Zur Speicherung des Parse-Baums (Abstract Syntax Tree) reicht es, die
	 * Wurzel zu speichern. Über sie bekommt man jeden beliebigen Knoten
	 * darunter.
	 */
	private Knoten wurzel;

	/**
	 * Nummer des Tokens, das gerade analysiert wird (von 0 beginnend)
	 */
	private int position;

	/**
	 * 
	 * @param tokenListe
	 *            Liste von Tokens, die geparst werden soll
	 */
	public Parser(ArrayList<Token> tokenListe) {

		super();

		this.tokenListe = tokenListe;

		position = 0;

	}

	/**
	 * Parst die dem Konstruktor übergebene Liste von Tokens und gibt einen
	 * Parse-Baum (Abstract Syntax Tree) zurück.
	 * 
	 * @return Parse-Baum (Abstract Syntax Tree)
	 * @throws Exception
	 *             Falls die Liste der Tokens nicht der Syntax entspricht
	 */
	public Knoten parse() throws Exception {

		wurzel = summeDifferenz(); // Versuche, eine Summe oder Differenz zu finden

		return wurzel;

	}

	/**
	 * Versucht, eine Summe oder Differenz zu parsen. Parst zunächst den ersten
	 * Operanden (unter der Annahme, dass es eine Multiplikation oder Division
	 * ist) und sieht dann nach, ob ein Pluszeichen oder ein Minuszeichen kommt.
	 * Falls "ja", parst es den zweiten Operanden und fügt alle drei zu einem
	 * Baum zusammen.
	 * 
	 * Folgt ein weiteres Plus oder Minus, wird der Baum als erster Operand
	 * einer weiteren Summe/Differenz gedeuet und nach dem zweiten Operanden
	 * dazu gesucht usw.
	 * 
	 * Folgt kein weiteres Plus oder Minus, so endet die Methode und der
	 * aktuelle Baum wird zurückgegeben.
	 * 
	 * @return Geparster Teilbaum
	 * @throws Exception
	 */
	private Knoten summeDifferenz() throws Exception {

		Knoten linkerOperand = produktQuotient(); // Versuche, eine Multiplikation
												// oder Division zu finden

		while (peek() == TokenType.plus || peek() == TokenType.minus) {

			Token operator = nextToken();

			Knoten rechterOperand = produktQuotient();

			Knoten neuerKnoten = new Knoten(operator, linkerOperand,
					rechterOperand);

			linkerOperand = neuerKnoten;

		}

		return linkerOperand;

	}

	/**
	 * Wie PlusMinus, jedoch für Mal und geteilt. Als erster bzw. zweiter
	 * Operand wird nach einem einfachen Term (Klammerterm, Zahl, Variable oder
	 * Negation) gesucht.
	 * 
	 * @return
	 * @throws Exception
	 */
	private Knoten produktQuotient() throws Exception {

		Knoten linkerOperand = einfacherTerm();

		while (peek() == TokenType.mal || peek() == TokenType.geteilt) {

			Token operator = nextToken();

			Knoten rechterOperand = einfacherTerm();

			Knoten neuerKnoten = new Knoten(operator, linkerOperand,
					rechterOperand);

			linkerOperand = neuerKnoten;

		}

		return linkerOperand;

	}

	private Knoten einfacherTerm() throws Exception {

		if (peek() == null) {
			throw new Exception("Parserfehler: unerwartetes Ende");
		}

		switch (peek()) {
		case klammerAuf: // Klammerterm
			nextToken();
			Knoten knoten = summeDifferenz(); // In der Klammer kann sich wieder eine
											// Summe/Differenz befinden...
			erwarte(TokenType.klammerZu); // Überliest die schließende Klammer
			return knoten;
		case text:
			return new Knoten(nextToken());
		case zahl:
			return new Knoten(nextToken());
		case minus:
			Knoten knoten1 = new Knoten(new Token(TokenType.negation));
			nextToken(); // überliest das Minuszeichen
			knoten1.setLinks(einfacherTerm()); // die Negation wirkt auf den einfachen
										// Term danach
			return knoten1;
		default:
			throw new Exception("Das Token " + peek() + " ist fehl am Platz.");
		}

	}

	/**
	 * Gibt das nächste Token aus der Tokenliste zurück, erhöht aber nicht die
	 * Leseposition
	 * 
	 * @return
	 */
	public TokenType peek() {

		if (position < tokenListe.size()) {

			return tokenListe.get(position).getTokenType();

		}

		return null;
	}

	/**
	 * Gibt das nächste Token aus der Tokenliste zurück und erhöht(!) die
	 * Leseposition
	 * 
	 * @return
	 */
	public Token nextToken() {

		if (position < tokenListe.size()) {

			Token token = tokenListe.get(position);

			position++;

			return token;

		}

		return null;

	}

	/**
	 * Wirft eine Exception, wenn das nächste Token in der Tokenliste nicht dem
	 * übergebenen Typ entspricht.
	 * 
	 * @param tokenType
	 * @throws Exception
	 */
	public void erwarte(TokenType tokenType) throws Exception {

		if (peek() == null) {
			throw new Exception(
					"Parserfehler: unerwartetes Ende. Erwartet wird: "
							+ tokenType);
		}

		if (peek() != tokenType) {
			throw new Exception("Parserfehler: Erwartet wird: " + tokenType);
		}

		nextToken();

	}

	/**
	 * Nur zu Debuggingzwecken
	 */
	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();

		toString(wurzel, "", sb);

		return sb.toString();

	}

	/**
	 * Traversiert vom übergebenen Knoten ausgehend den Teilbaum in pre-order
	 * Reihenfolge, d.h. zuerst wird der Konten selbst besucht, dann der
	 * komplette linke Teilbaum, der dranhängt, dann der komplette rechte
	 * Teilbaum, der dranhängt.
	 * 
	 * @param knoten
	 * @param einruecken
	 * @param sb
	 */
	private void toString(Knoten knoten, String einruecken, StringBuilder sb) {

		sb.append(einruecken);
		sb.append(knoten.getToken().toString() + "\n");

		if (knoten.getLinks() != null) {
			toString(knoten.getLinks(), einruecken + "   ", sb);
		}

		if (knoten.getRechts() != null) {
			toString(knoten.getRechts(), einruecken + "   ", sb);
		}

	}

	public Knoten getWurzel() {
		return wurzel;
	}

}
