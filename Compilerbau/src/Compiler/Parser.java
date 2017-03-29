package Compiler;

import java.util.ArrayList;

import lexer.Token;
import lexer.TokenType;

public class Parser {

	/**
	 * Liste, die vom Lexer kommt
	 */
	private ArrayList<Token> tokenListe;

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
	 * 
	 * @return Parse-Baum (Abstract Syntax Tree)
	 * @throws Exception
	 *             Falls die Liste der Tokens nicht der Syntax entspricht
	 */
	public Knoten parse() throws Exception {

		wurzel = sequenz(); 

		return wurzel;

	}

	/**
	 * Parst eine Reihe von Anweisungen.
	 * 
	 * @return
	 * @throws Exception
	 */
	private Knoten sequenz() throws Exception {
		
		Knoten knoten = null;
		Knoten ersteAnweisung = null;
		
		do {
			
			Knoten letzterKnoten = knoten;
			
			knoten = anweisung();
			
			if(letzterKnoten != null){
				
				letzterKnoten.setNaechsteAnweisung(knoten);
				
				if(ersteAnweisung == null){
					
					ersteAnweisung = letzterKnoten;
					
				}
			}
			
		} while (knoten != null);
		
		return ersteAnweisung;
		
	}
	
	/**
	 * Parst eine Anweisung, d.h. eine Wiederholung, eine Print-Anweisung oder
	 * eine Zuweisung.
	 * 
	 * @return
	 * @throws Exception
	 */
	private Knoten anweisung() throws Exception {

		if(peek() == null){
			return null;
		}
		/**
		 * Eine Anweisung beginnt mit whileKeyword, printKeyword oder text:
		 */
		switch (peek()) {
		case whileSchleife:
			return wiederholung();
		case printWord:
			return print();
                case text:
                        return text();
		case zuweisen:
			return zuweisen();
		default:
			return null;
		}

	}

	/**
	 * Die Methode geht davon aus, dass das n�chste Token vom Typ printKeyword
	 * ist.
	 * 
	 * @return
	 * @throws Exception
	 */
	private Knoten print() throws Exception {

		Knoten knoten = new Knoten(erwarte(TokenType.printWord));

		erwarte(TokenType.klammerAuf);

		Knoten aussage = aussage();

		erwarte(TokenType.klammerZu);

		erwarte(TokenType.strichpunkt);

		knoten.setLinks(aussage);

		return knoten;

	}
        
//        private Knoten text() throws Exception {
//            erwarte(TokenType.text);
////            Knoten knoten = new Knoten(erwarte(TokenType.text));
//            erwarte(TokenType.klammerAuf);
//
//            Token text = nextToken();
//            Knoten text = peek();
//
//            erwarte(TokenType.klammerZu);
//
//            erwarte(TokenType.strichpunkt);
//
//            knoten.setLinks(text);
//
//            return knoten;
//                
//        }

	/**
	 * Die Methode geht davon aus, dass das n�chste Token vom Typ whileKeyword
	 * ist.
	 * 
	 * @return
	 * @throws Exception
	 */
	private Knoten wiederholung() throws Exception {

		Knoten knoten = new Knoten(erwarte(TokenType.whileSchleife));

		erwarte(TokenType.klammerAuf);

		Knoten aussage = aussage();

		erwarte(TokenType.klammerZu);

		erwarte(TokenType.geschweifteKlammerAuf);

		Knoten anweisungen = sequenz();

		erwarte(TokenType.geschweifteKlammerZu);

		knoten.setLinks(aussage);
		knoten.setRechts(anweisungen);

		return knoten;

	}

	/**
	 * Die Methode geht davon aus, dass das n�chste Token vom Typ text ist.
	 * 
	 * @return
	 * @throws Exception
	 */
	private Knoten zuweisen() throws Exception {

		Knoten linkeSeite = new Knoten(nextToken());

		Knoten knoten = new Knoten(erwarte(TokenType.zuweisen));

		Knoten rechteSeite = aussage();

		erwarte(TokenType.strichpunkt);

		knoten.setLinks(linkeSeite);
		knoten.setRechts(rechteSeite);

		return knoten;

	}

	/**
	 * Versucht, eine Aussage im Programmtext zu parsen. Weiteres: siehe Methode
	 * SummeDifferenz.
	 * 
	 * @return Geparster Teilbaum
	 * @throws Exception
	 */
	private Knoten aussage() throws Exception {

		Knoten linkerOperand = summeDifferenz(); // Versuche, eine
													// Summe/Differenz zu finden

		while (peek() == TokenType.identisch || peek() == TokenType.ungleich
				|| peek() == TokenType.kleiner || peek() == TokenType.groesser
				|| peek() == TokenType.kleinergleich
				|| peek() == TokenType.groessergleich) {

			Token operator = nextToken();

			Knoten rechterOperand = summeDifferenz();

			Knoten neuerKnoten = new Knoten(operator, linkerOperand,
					rechterOperand);

			linkerOperand = neuerKnoten;

		}

		return linkerOperand;

	}

	/**
	 * 
	 * @return Geparster Teilbaum
	 * @throws Exception
	 */
	private Knoten summeDifferenz() throws Exception {

		Knoten linkerOperand = produktQuotient(); // Versuche, eine
													// Multiplikation
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
			Knoten knoten = summeDifferenz(); // In der Klammer kann sich wieder
												// eine
												// Summe/Differenz befinden...
			erwarte(TokenType.klammerZu); // �berliest die schlie�ende Klammer
			return knoten;
		case text:
			return new Knoten(nextToken());
		case zahl:
			return new Knoten(nextToken());
		case minus:
			Knoten knoten1 = new Knoten(new Token(TokenType.negation));
			nextToken(); // �berliest das Minuszeichen
			knoten1.setLinks(einfacherTerm()); // die Negation wirkt auf den
												// einfachen
			// Term danach
			return knoten1;
		default:
			throw new Exception("Das Token " + peek() + " ist fehl am Platz.");
		}

	}

	/**
	 * Gibt das n�chste Token aus der Tokenliste zur�ck, erh�ht aber nicht die
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
	 * Gibt das n�chste Token aus der Tokenliste zur�ck und erh�ht(!) die
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
	 * Wirft eine Exception, wenn das n�chste Token in der Tokenliste nicht dem
	 * �bergebenen Typ entspricht.
	 * 
	 * @param tokenType
	 * @throws Exception
	 */
	public Token erwarte(TokenType tokenType) throws Exception {

		if (peek() == null) {
			throw new Exception(
					"Parserfehler: unerwartetes Ende. Erwartet wird: "
							+ tokenType);
		}

		if (peek() != tokenType) {
			throw new Exception("Parserfehler: Erwartet wird: " + tokenType);
		}

		return nextToken();

	}

	/**
	 * Nur zu Debuggingzwecken
	 */
	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();

		toString(wurzel, "", "", sb);

		return sb.toString();

	}

	/**
	 * Traversiert vom �bergebenen Knoten ausgehend den Teilbaum in pre-order
	 * Reihenfolge, d.h. zuerst wird der Konten selbst besucht, dann der
	 * komplette linke Teilbaum, der dranh�ngt, dann der komplette rechte
	 * Teilbaum, der dranh�ngt.
	 * 
	 * @param knoten
	 * @param einruecken
	 * @param sb
	 */
	private void toString(Knoten knoten, String einruecken, String kante, StringBuilder sb) {

		sb.append(einruecken + kante);
		sb.append(knoten.getToken().toString() + "\n");

		if (knoten.getLinks() != null) {
			
			toString(knoten.getLinks(), einruecken + "   ", "l:", sb);
		}

		if (knoten.getRechts() != null) {
			toString(knoten.getRechts(), einruecken + "   ", "r:", sb);
		}
		
		if(knoten.getNaechsteAnweisung() != null){
			toString(knoten.getNaechsteAnweisung(), einruecken + "   ", "n:", sb);
		}

	}

	public Knoten getWurzel() {
		return wurzel;
	}

}
