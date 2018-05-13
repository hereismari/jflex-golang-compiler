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
    	System.out.println("Token " + sym + ", Line: " + yyline + ", size:" + yylength());
    
    	Location left = new Location(yyline+1, yycolumn+1);
    	Location right = new Location(yyline+1, yycolumn+yylength());
    	return symbolFactory.newSymbol("sym", sym, left, right);
	}
	
	private Symbol symbol(int sym, Object val) {
		System.out.println("Token " + val + ", Line: " + yyline + ", size:" + yylength());
	
    	Location left = new Location(yyline+1, yycolumn+1);
    	Location right = new Location(yyline+1, yycolumn+yylength());
    	return symbolFactory.newSymbol("sym", sym, left, right, val);
	}
	
	private void ignore(String pattern, String content) {
		System.out.println("Ignoring " + pattern + ", content: " + content);
	}
	
%}

%eofval{
     return symbolFactory.newSymbol("EOF", EOF, new Location(yyline+1, yycolumn+1), new Location(yyline+1, yycolumn+1));
%eofval}

Newline        = \r|\n|\r\n
UnicodeChar    = [^\r\n]
UnicodeLetter  = [:letter:]
UnicodeDigit   = [:digit:]

Letter         = {UnicodeLetter}|"_"
DecimalDigit   = [0-9]
OctalDigit     = [0-7]
HexDigit       = [0-9]|[A-F]|[a-f]
WhiteSpace     = {Newline} | [ \t\f]
LineComment    = "//"{UnicodeChar}*{Newline}?
GeneralComment = "/*" ([^*] | "*" + [^*/])* "*" + "/"

%%

// Ignore comments
{LineComment}    { ignore("//", yytext()); }
{GeneralComment} { ignore("/*", yytext()); }

// keywords
"if"             { return symbol(IF, yytext()); }

// punctuation and operators
";"				 { return symbol(SEMICOLON, ";"); }


// Ignore whitespace
{WhiteSpace} { ignore("WhiteSpace", yytext()); }

[^]  { System.err.println("Error: Illegal character: " + yytext() + " Line: " + (yyline+1) + ", Column:" + (yycolumn+1)); }