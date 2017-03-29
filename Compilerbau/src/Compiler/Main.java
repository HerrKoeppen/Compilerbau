/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Compiler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author jonathan.brandt
 */
public class Main {
    
    public static void main(String args[]) throws IOException, Exception{
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String input = in.readLine();
        System.out.println("input:"+input);
        Lexer lexer = new Lexer(input);
        lexer.lex();
        System.out.println("lexer:"+lexer.toString());
        System.out.println("lexer:"+lexer.getTokenListe());
        Parser parser = new Parser(lexer.getTokenListe());
        parser.parse();
//        System.out.println("parser:"+parser.toString());
        Interpreter inter = new Interpreter();
        System.out.println(inter.interpretiere(parser.getWurzel()));
    }
    
}
