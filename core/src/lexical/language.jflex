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
UnicodeChar    = [^\r|\n|\r\n]
UnicodeCharDash = [^\r|\n|\r\n|"\""]
UnicodeCharDash2 = [^\r|\n|\r\n|"`"]

UnicodeLetter  = [:letter:]
UnicodeDigit   = [:digit:]

Letter         = {UnicodeLetter}|"_"
DecimalDigit   = [0-9]
OctalDigit     = [0-7]
HexDigit       = [0-9]|[A-F]|[a-f]

// Number literal
DecimalLit = [1-9]{DecimalDigit}*
OctalLit   = "0"{OctalDigit}*
HexLit     = "0"("x"|"X"){HexDigit}{HexDigit}*
IntLit     = {DecimalLit}|{OctalLit}|{HexLit}

Decimals   = {DecimalDigit}{DecimalDigit}*
Exponent   = ("e"|"E")("+"|"-")?{Decimals}
FloatLit   = {Decimals}"."{Decimals}?{Exponent}?|{Decimals}{Exponent}|"."{Decimals}{Exponent}?

ImaginaryLit = ({Decimals}|{FloatLit})"i"

// Rune literal
OctalByteValue   = "\\"{OctalDigit}{OctalDigit}{OctalDigit}
ByteValue        = {OctalByteValue} | {HexByteValue}
HexByteValue     = "\\""x"{HexDigit}{HexDigit}
LittleUValue     = "\\""u"{HexDigit}{HexDigit}{HexDigit}{HexDigit}
BigUValue        = "\\""U"{HexDigit}{HexDigit}{HexDigit}{HexDigit}{HexDigit}{HexDigit}{HexDigit}{HexDigit}
EscapedChar      = "\\"( "a" | "b" | "f" | "n" | "r" | "t" | "v" | "\\" | "'" | "\"")

UnicodeValue     = {UnicodeChar} | {LittleUValue} | {BigUValue} | {EscapedChar}
RuneLit          = "'" ( {UnicodeValue} | {ByteValue}) "'" 

// String literal

UnicodeValueDash     = {UnicodeCharDash} | {LittleUValue} | {BigUValue} | {EscapedChar}
RawStringLit         = "`"({UnicodeCharDash2} | {Newline})* "`"
IntStringLit         = "\""({UnicodeValueDash} | {ByteValue})* "\""
StringLit            = {RawStringLit} | {IntStringLit}

// White space and comments
WhiteSpace     = {Newline} | [ \t\f]
LineComment    = "//"{UnicodeChar}*{Newline}?
GeneralComment = "/*" ([^*] | "*" + [^*/])* "*" + "/"

Identifier     = {Letter}({Letter} | {UnicodeDigit})*
 

%%

// Ignore comments

{LineComment}                 {}
{GeneralComment}              {}

// Keywords

"break"                       { return symbol(BREAK, "break"); }
"default"                     { return symbol(DEFAULT, "default"); }
"func"                        { return symbol(FUNC, "func"); }
"interface"                   { return symbol(INTERFACE, "interface"); }
"select"                      { return symbol(SELECT, "select"); }

"case"                        { return symbol(CASE, "case"); }
"defer"                       { return symbol(DEFER, "defer"); }
"go"                          { return symbol(GO, "go"); }
"map"                         { return symbol(MAP, "map"); }
"struct"                      { return symbol(STRUCT, "struct"); }

"chan"                        { return symbol(CHAN, "chan"); }
"else"                        { return symbol(ELSE, "else"); }
"goto"                        { return symbol(GOTO, "goto"); }
"package"                     { return symbol(PACKAGE, "package"); }
"switch"                      { return symbol(SWITCH, "switch"); }

"const"                       { return symbol(CONST, "const"); }
"fallthrough"                 { return symbol(FALLTHROUGH, "fallthrough"); }
"if"                          { return symbol(IF, "if"); }
"range"                       { return symbol(RANGE, "range"); }
"type"                        { return symbol(TYPE, "type"); }

"continue"                    { return symbol(CONTINUE, "continue"); }
"for"                         { return symbol(FOR, "for"); }
"import"                      { return symbol(IMPORT, "import"); }
"return"                      { return symbol(RETURN, "return"); }
"var"                         { return symbol(VAR, "var"); }

// Operators

"+"                           { return symbol(PLUS, "+"); }
"&"                           { return symbol(ADDRESS, "&"); }
"&&"                          { return symbol(AND, "&&"); }
"-"                           { return symbol(MINUS, "-"); }
"|"                           { return symbol(OR, "|"); }
"<"                           { return symbol(LT, "<"); }
"<="                          { return symbol(LTE, "<="); }
"*"                           { return symbol(MUL, "*"); }
"^"                           { return symbol(XOR, "^"); }
">"                           { return symbol(GT, ">"); }
">="                          { return symbol(GTE, ">="); }
"/"                           { return symbol(DIV, "/"); }
"%"                           { return symbol(MOD, "%"); }
"!"                           { return symbol(NOT, "!"); }

"+="                          { return symbol(ADD_ASSIGN, "+="); }
"&="                          { return symbol(ADDRESS_ASSIGN, "&="); }
"-="                          { return symbol(MINUS_ASSIGN, "-="); }
"|="                          { return symbol(OR_ASSIGN, "|="); }
"*="                          { return symbol(MUL_ASSIGN, "*="); }
"^="                          { return symbol(XOR_ASSIGN, "^="); }
"/="                          { return symbol(DIV_ASSIGN, "/="); }
"<<="                         { return symbol(LEFT_ASSIGN, "<<="); }
"="                           { return symbol(ASSIGN, "="); }
":="                          { return symbol(CHANNEL_ASSIGN, ":="); }
">>="                         { return symbol(RIGHT_ASSIGN, ">>="); }
"&^="                         { return symbol(AND_NOT_ASSIGN, "&^="); }

"=="                          { return symbol(EQ_OP, "=="); }
"!="                          { return symbol(NE_OP, "!="); }
"||"                          { return symbol(OR_OP, "||"); }
"<<"                          { return symbol(LEFT_OP, "<<"); }
"++"                          { return symbol(INC_OP, "++"); }
">>"                          { return symbol(RIGHT_OP, ">>"); }
"--"                          { return symbol(SUB_OP, "--"); }
"&^"                          { return symbol(AND_NOT_OP, "&^"); }
"<-"                          { return symbol(CHANNEL_OP, "<-"); }

// Punctuation

"("                           { return symbol(PAR_L, "("); }
")"                           { return symbol(PAR_R, ")"); }
"["                           { return symbol(SQRD_L, "["); }
"]"                           { return symbol(SQRD_R, "]"); }
"{"                           { return symbol(CURLY_L, "{"); }
"}"                           { return symbol(CURLY_R, "}"); }

","                           { return symbol(COMMA, ","); }
";"                           { return symbol(SEMICOLON, ";"); }
"..."			              { return symbol(ELLIPSIS,"..."); }
"."                           { return symbol(DOT, "."); }
":"                           { return symbol(COLON, ":"); }

// Other


{Identifier}                  { return symbol(IDENTIFIER, yytext()); }
"_"                           { return symbol(BLANK_IDENTIFIER, yytext()); }

{IntLit}                      { return symbol(INT_LITERAL, yytext()); }
{FloatLit}                    { return symbol(FLOAT_LITERAL, yytext()); }
{ImaginaryLit}                { return symbol(IMG_LITERAL, yytext()); }
{RuneLit}                     { return symbol(RUNE_LITERAL, yytext()); }
{StringLit}                   { return symbol(STRING_LITERAL, yytext()); }

// Ignore whitespace 
{WhiteSpace} {}

[^]  { System.err.println("Error: Illegal character: " + yytext() + " Line: " + (yyline+1) + ", Column:" + (yycolumn+1)); }