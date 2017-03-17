package BeispielCompiler2;

import lexer.Lexer;

public class ParserTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {

			String text = "a = 1;\nb = 2;\nwhile(a < 10){\n  a = a + 1;\n  b = b * 2;\n  print(b);\n}";
			
			System.out.println("Eingabetext:\n" + text);
			
			Lexer l = new Lexer(text);
			
			l.lex();

			System.out.println("\nTokens:\n" + l.toString());
			
			Parser p = new Parser(l.getTokenListe());
			
			p.parse();
			
			System.out.println("\nSyntaxbaum (AST):\n" + p.toString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
