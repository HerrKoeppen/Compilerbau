package BeispielCompiler1;

public class LexerTest {


	public static void main(String[] args) {
		
		try {
		
			Lexer l = new Lexer("1 + 2 * (4 * a1 - 3)");
			
			l.lex();

			System.out.println(l);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}

}
