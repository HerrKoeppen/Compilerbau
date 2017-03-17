package BeispielCompiler2;

import lexer.Lexer;
import parser.Parser;

public class InterpreterTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			/**
			 * Der Text wird hier Stringkonstante definiert. Er k�nnte ebenso
			 * gut gerade vom Benutzer eingegeben worden sein. Wichtig ist: Der
			 * Java-Compiler compiliert hier nichts. Es wird alles durch Lexer,
			 * Parser und Interpreter erledigt.
			 */
			String text = "a = 1.2;\nb = 2;\nwhile(a < 10){\n  a = a + 1;\n  b = b * 2;\n  print(b);\n}";

			System.out.println("Eingabetext:\n" + text);

			/**
			 * Zerlegung des Programmtextes in Tokens:
			 */
			Lexer l = new Lexer(text);
			l.lex();
			System.out.println("\nTokens:\n" + l.toString());

			/**
			 * Bauen des Abstract Syntax Tree:
			 */
			Parser p = new Parser(l.getTokenListe());

			p.parse();

			System.out.println("\nSyntaxbaum (abstract syntax tree):\n" + p.toString());
			
			/**
			 * Ausf�hrung des Programms
			 */

			Interpreter i = new Interpreter();

			System.out.println("\nAusgabe des Programms:\n");			
			
			Object wert = i.interpretiere(p.getWurzel());
			
			System.out
					.println("\nTermwert:\n" + wert);

		} catch (Exception e) {
			// Falls ein Fehler bei der �bersetzung aufgetreten ist:
			e.printStackTrace();
		}

	}

}
