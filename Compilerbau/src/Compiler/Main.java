/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Compiler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import lexer.Lexer;

/**
 *
 * @author jonathan.brandt
 */
public class Main {
    
    public static void main(String args[]) throws IOException, Exception{
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String input = in.readLine();
        Lexer lexer = new Lexer(input);
        Parser parser = new Parser(lexer.getTokenList());
        parser.parse();
        Interpreter inter = new Interpreter();
        System.out.println(inter.interpretiere(parser.getWurzel()));
    }
    
}
