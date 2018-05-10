import java.io.*;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.Location;
import java_cup.runtime.Symbol;

%%

%class Lexer
%unicode
%public
%cup
%line
%column
%char
%implements sym

%{
	ComplexSymbolFactory symbolFactory;
	
	public Lexer(java.io.Reader in, ComplexSymbolFactory sf) {
		this(in);
		symbolFactory = sf;
    }
    
    private Symbol symbol(int sym) {
    	Location left = new Location(yyline+1, yycolumn+1, yychar);
    	Location right = new Location(yyline+1, yycolumn+yylength(), yychar+yylength());
    	return symbolFactory.newSymbol("sym", sym, left, right);
	}
	
	private Symbol symbol(int sym, Object val) {
    	Location left = new Location(yyline+1, yycolumn+1, yychar);
    	Location right = new Location(yyline+1, yycolumn+yylength(), yychar+yylength());
    	return symbolFactory.newSymbol("sym", sym, left, right, val);
	}
%}

%eofval{
     return symbolFactory.newSymbol("EOF", EOF, new Location(yyline+1, yycolumn+1, yychar), new Location(yyline+1, yycolumn+1, yychar+1));
%eofval}

Digit      = [0-9]
Letter     = [a-zA-Z]
NewLine    = \r|\n|\r\n
WhiteSpace = {NewLine} | [ \t\f]

%%

"if" { return symbol(IF, yytext()); }

[^]  { System.err.println("Error: Illegal character: " + yytext() + " " + (yyline+1) + "/" + (yycolumn+1)); }