import java_cup.runtime.Symbol;

%%

%cup
%public
%class Lexer
%line
%column


DIGIT = [0-9]
LETTER = [a-zA-Z_]
COMMENT = \|\|.*\n
STRING = \~{LETTER}+\~
INTEGER = {DIGIT}+
VARIABLE = @{LETTER}+
ASSIGNMENT = \${LETTER}+
FUNCTION = &{LETTER}+
FUNCTION_PARAMS = \^{LETTER}+ 
CALL_FUNCTION = %{LETTER}+
IGNORE = [\n|\s|\t\r]

%%

<YYINITIAL> {

    "program"       {return new Symbol(Sym.PROGRAM); }
    "if"            {return new Symbol(Sym.IF); }

    "<{"            {return new Symbol(Sym.BEGIN); }
    "}>"            {return new Symbol(Sym.END); }

    "INT"           {return new Symbol(Sym.INTEGER_TYPE); }
    "STR"           {return new Symbol(Sym.INTEGER_TYPE); }

    ":"             {return new Symbol(Sym.COLON); }
    "("             {return new Symbol(Sym.LEFT_PARAMETER); }
    ")"             {return new Symbol(Sym.RIGHT_PARAMETER); }    
    "["             {return new Symbol(Sym.LEFT_BRACKETS); }
    "]"             {return new Symbol(Sym.RIGHT_BRACKETS); }    

    ";"             {return new Symbol(Sym.SEMICOLON); }

    "TT"            {return new Symbol(Sym.TT); }
    "FF"            {return new Symbol(Sym.FF); }

    "->"            {return new Symbol(Sym.SYMBOL_ASSIGNMENT); }

    {CALL_FUNCTION} {return new Symbol(Sym.CALL_FUNCTION); }

    {ASSIGNMENT}    {return new Symbol(Sym.ASSIGNMENT); }

    {STRING}        {return new Symbol(Sym.STRING); }
    {INTEGER}       {return new Symbol(Sym.INTEGER); }  


    {FUNCTION}      {return new Symbol(Sym.FUNCTION); }

    {FUNCTION_PARAMS}   {return new Symbol(Sym.FUNCTION_PARAMS); }

    {VARIABLE}      {return new Symbol(Sym.VARIABLE); }

    {IGNORE}        {}
    {COMMENT}       {}

}

<<EOF>> { return new Symbol( Sym.EOF ); }


[^] { throw new Error("Illegal character: "+yytext()+" at line "+(yyline+1)+", column "+(yycolumn+1) ); }