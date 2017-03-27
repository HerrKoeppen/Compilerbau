/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Compiler;

import lexer.Token;

/**
 *
 * @author kiara.jung
 */
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
	
	/**
	 * Im Falle einer Anweisung: nächstfolgende Anweisung
	 */
	private Knoten naechsteAnweisung;

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

	public Knoten getNaechsteAnweisung() {
		return naechsteAnweisung;
	}

	public void setNaechsteAnweisung(Knoten naechsteAnweisung) {
		this.naechsteAnweisung = naechsteAnweisung;
	}
	
	

}

