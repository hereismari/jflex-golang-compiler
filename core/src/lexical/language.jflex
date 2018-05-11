package lexical;

import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.Location;
import java_cup.runtime.Symbol;
import syntax.Sym;

%%

%class Lexer
%unicode
%public
%cup
%line
%column
%implements Sym

%{
	private ComplexSymbolFactory symbolFactory;
	
	public Lexer(java.io.Reader in, ComplexSymbolFactory sf) {
		this(in);
		symbolFactory = sf;
    }
    
    private Symbol symbol(int sym) {
    	Location left = new Location(yyline+1, yycolumn+1);
    	Location right = new Location(yyline+1, yycolumn+yylength());
    	return symbolFactory.newSymbol("sym", sym, left, right);
	}
	
	private Symbol symbol(int sym, Object val) {
    	Location left = new Location(yyline+1, yycolumn+1);
    	Location right = new Location(yyline+1, yycolumn+yylength());
    	return symbolFactory.newSymbol("sym", sym, left, right, val);
	}
%}

%eofval{
     return symbolFactory.newSymbol("EOF", EOF, new Location(yyline+1, yycolumn+1), new Location(yyline+1, yycolumn+1));
%eofval}

Newline        = \R
UnicodeChar    = [^\R]
UnicodeLetter  = [:letter:]
UnicodeDigit   = [:digit:]

Letter         = {UnicodeLetter} | "_"
DecimalDigit   = [0-9]
OctalDigit     = [0-7]
HexDigit       = [0-9] | [A-F] | [a-f]
WhiteSpace     = {Newline} | [ \t\f]
LineComment    = "//" {UnicodeChar}* {Newline}?
GeneralComment = "/*" ([^*] | "*" + [^*/])* "*" + "/"

%%

{LineComment}    { }
{GeneralComment} { }

"if"             { return symbol(IF, yytext()); }

[^]  { System.err.println("Error: Illegal character: " + yytext() + " " + (yyline+1) + "/" + (yycolumn+1)); }