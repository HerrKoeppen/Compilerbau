package BeispielCompiler1;

import BeispielCompiler1.Lexer;
import BeispielCompiler1.Parser;

public class InterpreterTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			String text = "1 + 2*(3 + a) - (7-b)";

			System.out.println("Eingabetext:\n" + text);

			Lexer l = new Lexer(text);
			l.lex();
			System.out.println("\nTokens:\n" + l.toString());

			Parser p = new Parser(l.getTokenListe());

			p.parse();

			System.out.println("\nParsebaum:\n" + p.toString());
			
			Interpreter i = new Interpreter();
			i.belegeVariable("a", 5);
			i.belegeVariable("b", 1);
			
			System.out.println("\nVariablenbelegung:\n" + i.getBelegungAlsString());
			System.out.println("\nTermwert:\n" + i.interpretiere(p.getWurzel()));
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
