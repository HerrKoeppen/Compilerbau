package BeispielCompiler1;

import java.util.HashMap;

import BeispielCompiler1.Knoten;

public class Interpreter {

	private HashMap<String, Double> variablenbelegung = new HashMap<>();
	
	public void belegeVariable(String bezeichner, double wert){
		variablenbelegung.put(bezeichner, wert);
	}
	
	public double interpretiere(Knoten knoten) throws Exception{
		
		switch (knoten.getToken().getTokenType()) {
		case plus:
			return interpretiere(knoten.getLinks()) + interpretiere(knoten.getRechts());
		case minus:
			return interpretiere(knoten.getLinks()) - interpretiere(knoten.getRechts());
		case mal:
			return interpretiere(knoten.getLinks()) * interpretiere(knoten.getRechts());
		case geteilt:
			return interpretiere(knoten.getLinks()) / interpretiere(knoten.getRechts());
		case negation:
			return - interpretiere(knoten.getLinks());
		case text:
			String variablenbezeichner = knoten.getToken().getText();
			Double wert = variablenbelegung.get(variablenbezeichner);
			if(wert == null){
				throw new Exception("Die Belegung der Variable " + variablenbezeichner + " ist nicht bekannt.");
			}
			return wert;
		case zahl:
			return knoten.getToken().getZahl();

		default:
			return 0; // sollte nie vorkommen
		}
		
	}

	public String getBelegungAlsString() {
		
		String s = "";
		
		for(String bezeichner: variablenbelegung.keySet().toArray(new String[0])){
			s += bezeichner + " = " + variablenbelegung.get(bezeichner) + "\n";
		}
		
		return s;
	}
	
}
