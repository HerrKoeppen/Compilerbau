package BeispielCompiler2;

import java.util.HashMap;

import lexer.TokenType;

import parser.Knoten;

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
	public void belegeVariable(String bezeichner, Object wert) {
		variablenbelegung.put(bezeichner, wert);
	}

	/**
	 * Berechnet - ausgehend vom �bergebenen Knoten - den Wert des Terms, der
	 * durch den Knoten und den darunterh�ngenden Teilbaum gegeben ist.
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

			case kleiner:
				return (Double) interpretiere(knoten.getLinks()) < (Double) interpretiere(knoten
						.getRechts());

			case groesser:
				return (Double) interpretiere(knoten.getLinks()) > (Double) interpretiere(knoten
						.getRechts());

			case kleinergleich:
				return (Double) interpretiere(knoten.getLinks()) <= (Double) interpretiere(knoten
						.getRechts());

			case groessergleich:
				return (Double) interpretiere(knoten.getLinks()) >= (Double) interpretiere(knoten
						.getRechts());

			case identisch:

				Object linkerWert = interpretiere(knoten.getLinks());

				if (linkerWert.getClass() == Double.class) {
					return (Double) interpretiere(knoten.getLinks()) == (Double) interpretiere(knoten
							.getRechts());
				} else {
					return (Boolean) interpretiere(knoten.getLinks()) == (Boolean) interpretiere(knoten
							.getRechts());
				}

			case ungleich:

				Object linkerWert1 = interpretiere(knoten.getLinks());

				if (linkerWert1.getClass() == Double.class) {
					return (Double) interpretiere(knoten.getLinks()) != (Double) interpretiere(knoten
							.getRechts());
				} else {
					return (Boolean) interpretiere(knoten.getLinks()) != (Boolean) interpretiere(knoten
							.getRechts());
				}

			case trueKeyword:

				return true;

			case falseKeyword:

				return false;

			case text:

				String variablenbezeichner = knoten.getToken().getText();

				Object wert = variablenbelegung.get(variablenbezeichner);

				if (wert == null) {
					throw new Exception("Die Belegung der Variable "
							+ variablenbezeichner + " ist nicht bekannt.");
				}

				return wert;

			case zahl:
				return knoten.getToken().getZahl();

			case whileKeyword:
				/**
				 * Im linken Knoten hat der Parser die Bedingung (also den Term
				 * innerhalb von "(...)") abgelegt:
				 */
				while ((Boolean) interpretiere(knoten.getLinks())) {

					/**
					 * Der rechte Knoten zeigt auf die erste Anweisung innerhalb
					 * der Wiederholung. Die Methode interpretiere f�hrt auch
					 * gleich die nachfolgenden Anweisungen aus.
					 */
					interpretiere(knoten.getRechts());

				}

				/**
				 * f�hre die Anweisungen aus, die auf die Wiederholung folgen,
				 * also nach der "}"
				 */
				interpretiere(knoten.getNaechsteAnweisung());

				return null;

			case zuweisung:
				String variablenbezeichner1 = knoten.getLinks().getToken()
						.getText();

				Object wert1 = interpretiere(knoten.getRechts());

				/**
				 * Neuen Wert der Variable speichern:
				 */
				variablenbelegung.put(variablenbezeichner1, wert1);

				/**
				 * F�hre die Anweisungen aus, die nach der Zuweisung kommen:
				 */
				interpretiere(knoten.getNaechsteAnweisung());

				return wert1;

			case printKeyword:
				
				/**
				 * Im linken Knoten steckt der Term, dessen Wert ausgegeben werden soll
				 */
				System.out.println(interpretiere(knoten.getLinks()));

				/**
				 * F�hre die Anweisungen aus, die nach der Print-Anweisung kommen:
				 */
				interpretiere(knoten.getNaechsteAnweisung());

				return null;

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
	 *         Form variablenbezeichner = wert enth�lt.
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
