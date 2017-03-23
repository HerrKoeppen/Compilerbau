package BeispielCompiler1;

import BeispielCompiler1.Token;

public class Knoten {

	/**
	 * Im Token steckt der Inhalt des Knotens drin, also ein Operator, eine Zahl oder ein 
	 * Variablenbezeichner. Der Einfachheit halber verwenden wir hier die Klasse Token. 
	 */
	private Token token; 
	
	/**
	 * Kindknoten linkerhand
	 */
	private Knoten links;
	
	/**
	 * Kindknoten rechterhand
	 */
	private Knoten rechts;

	public Knoten(Token token) {
		super();
		this.token = token;
	}

	public Knoten(Token token, Knoten linkerOperand, Knoten rechterOperand) {
		this.token = token;
		this.links = linkerOperand;
		this.rechts = rechterOperand;
	}

	public Knoten getLinks() {
		return links;
	}

	public void setLinks(Knoten links) {
		this.links = links;
	}

	public Knoten getRechts() {
		return rechts;
	}

	public void setRechts(Knoten rechts) {
		this.rechts = rechts;
	}

	public Token getToken() {
		return token;
	}

}
