/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Compiler;

import java.util.HashMap;



public class Interpreter {

	/**
	 * Speichert Zuordnungen von Variablenbezeichnern zu Werten:
	 */
	private HashMap<String, Object> variablenbelegung = new HashMap<>();

	/**
	 * Belegt die Variable mit Bezeichner bezeichner mit dem Wert wert.
	 * 
	 * @param bezeichner
	 *            Bezeichner der Variablen
	 * @param wert
	 *            Wert der Variablen
	 */
        
//	public void belegeVariable(String bezeichner, Object wert) {
//		variablenbelegung.put(bezeichner, wert);
//	}

	/**
	 * Berechnet - ausgehend vom übergebenen Knoten - den Wert des Terms, der
	 * durch den Knoten und den darunterhängenden Teilbaum gegeben ist.
	 * 
	 * @param knoten
	 *            Wurzel des Teilbaums, dessen Termwert berechnet werden soll
	 * @return Wert des Terms
	 * @throws Exception
	 */
	public Object interpretiere(Knoten knoten) throws Exception {

		if (knoten != null) {

			switch (knoten.getToken().getTokenType()) {

			case plus:
				return (Double) interpretiere(knoten.getLinks())
						+ (Double) interpretiere(knoten.getRechts());

			case minus:
				return (Double) interpretiere(knoten.getLinks())
						- (Double) interpretiere(knoten.getRechts());

			case mal:
				return (Double) interpretiere(knoten.getLinks())
						* (Double) interpretiere(knoten.getRechts());

			case geteilt:
				return (Double) interpretiere(knoten.getLinks())
						/ (Double) interpretiere(knoten.getRechts());

			case negation:
				return -(Double) interpretiere(knoten.getLinks());

			case kleinerAls:
				return (Double) interpretiere(knoten.getLinks()) < (Double) interpretiere(knoten
						.getRechts());

			case groesserAls:
				return (Double) interpretiere(knoten.getLinks()) > (Double) interpretiere(knoten
						.getRechts());

			case kleinerGleich:
				return (Double) interpretiere(knoten.getLinks()) <= (Double) interpretiere(knoten
						.getRechts());

			case groesserGleich:
				return (Double) interpretiere(knoten.getLinks()) >= (Double) interpretiere(knoten
						.getRechts());

			case gleich:

				Object linkerWert = interpretiere(knoten.getLinks());

				if (linkerWert.getClass() == Double.class) {
					return (Double) interpretiere(knoten.getLinks()) == (Double) interpretiere(knoten
							.getRechts());
				} else {
					return (Boolean) interpretiere(knoten.getLinks()) == (Boolean) interpretiere(knoten
							.getRechts());
				}

			case nichtGleich:

				Object linkerWert1 = interpretiere(knoten.getLinks());

				if (linkerWert1.getClass() == Double.class) {
					return (Double) interpretiere(knoten.getLinks()) != (Double) interpretiere(knoten
							.getRechts());
				} else {
					return (Boolean) interpretiere(knoten.getLinks()) != (Boolean) interpretiere(knoten
							.getRechts());
				}

//			case trueKeyword:
//
//				return true;
//
//			case falseKeyword:
//
//				return false;

//			case text:
//
//				String variablenbezeichner = knoten.getToken().getText();
//
//				Object wert = variablenbelegung.get(variablenbezeichner);
//
//				if (wert == null) {
//					throw new Exception("Die Belegung der Variable "
//							+ variablenbezeichner + " ist nicht bekannt.");
//				}
//
//				return wert;
                                
                        case text:
                                return knoten.getToken().getText();
                        
                        case zeichen:
                                return knoten.getToken().getChar();
                                
                        case bool:
                                return knoten.getToken().getBool();
                        
                        case klaus:
                                return null;
                                
			case gZahl:
				return knoten.getToken().getGZahl();
                        
                        case kZahl:
                                return knoten.getToken().getKZahl();
                                
//                        case liste:
//                                knoten.getRechts().add(knoten.getLinks());

			case whileSchleife:
				/**
				 * Im linken Knoten hat der Parser die Bedingung (also den Term
				 * innerhalb von "(...)") abgelegt:
				 */
				while ((Boolean) interpretiere(knoten.getLinks())) {

					/**
					 * Der rechte Knoten zeigt auf die erste Anweisung innerhalb
					 * der Wiederholung. Die Methode interpretiere führt auch
					 * gleich die nachfolgenden Anweisungen aus.
					 */
					interpretiere(knoten.getRechts());

				}

				/**
				 * führe die Anweisungen aus, die auf die Wiederholung folgen,
				 * also nach der "}"
				 */
				interpretiere(knoten.getNaechsteAnweisung());

				return null;

			case zuweisen:
				String variablenbezeichner1 = knoten.getLinks().getToken()
						.getText();

				Object wert1 = interpretiere(knoten.getRechts());

				/**
				 * Neuen Wert der Variable speichern:
				 */
				variablenbelegung.put(variablenbezeichner1, wert1);

				/**
				 * Führe die Anweisungen aus, die nach der Zuweisung kommen:
				 */
				interpretiere(knoten.getNaechsteAnweisung());

				return wert1;
                        
                        case hole:
                                return interpretiere(knoten.getLinks());
                        
                        case falls:
                            boolean bed;
                            bed = (Boolean) interpretiere(knoten.getLinks());
                            if (bed){
                                interpretiere(knoten.getRechts());
                            }
                            interpretiere(knoten.getNaechsteAnweisung());
                        
//                        case negfalls:
//                            if (!interpretiere(knoten.getLinks)){
//                                interpretiere(knoten.getRechts);
//                            }                              
                                        

//			case printKeyword:
//				
//				/**
//				 * Im linken Knoten steckt der Term, dessen Wert ausgegeben werden soll
//				 */
//				System.out.println(interpretiere(knoten.getLinks()));
//
//				/**
//				 * Führe die Anweisungen aus, die nach der Print-Anweisung kommen:
//				 */
//				interpretiere(knoten.getNaechsteAnweisung());
//
//				return null;

			default:
				return null; // sollte nie vorkommen
			}

		} else {

			return null;
		}

	}

	/**
	 * Nur zu Debuggingzwecken
	 * 
	 * @return String, der alle Variablen zusammen mit ihrer Belegung in der
	 *         Form variablenbezeichner = wert enthält.
	 */
	public String getBelegungAlsString() {

		String s = "";

		for (String bezeichner : variablenbelegung.keySet().toArray(
				new String[0])) {
			s += bezeichner + " = " + variablenbelegung.get(bezeichner) + "\n";
		}

		return s;
	}

}
