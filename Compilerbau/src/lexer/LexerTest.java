package lexer;

public class LexerTest {


	public static void main(String[] args) {
		
		try {
		
			Lexer l = new Lexer("a = 1.22;\nb = 2;\nwhile(a < 10){\n  a = a + 1;\n  b = b * 2;\n  print(b);\n}");

                        
			l.lex();

			System.out.println(l);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}

}
