package BeispielCompiler1;

import BeispielCompiler1.Lexer;

public class ParserTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {

			String text = "1 + 2 * (4 * -a1 - 3)";
			
			System.out.println("Eingabetext:\n" + text);
			
			Lexer l = new Lexer(text);
			
			l.lex();

			System.out.println("\nTokens:\n" + l.toString());
			
			Parser p = new Parser(l.getTokenListe());
			
			p.parse();
			
			System.out.println("\nParsebaum:\n" + p.toString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
