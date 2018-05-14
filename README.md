# GoCompiler

A Golang compiler implementation.

![](https://www.devteam.space/blog/wp-content/uploads/2017/03/gopher_head-min.png)

## Dependencies

* Java 1.8.X
* jflex 1.6.1

### Optional

* Eclipse 4.X

## Installing JFlex + Cup

1. [Download flex + cup](http://jflex.de/download.html)
2. Follow the [installation steps](http://jflex.de/installing.html)
3. Create a new Java project on Eclipse
4. Add jflex to the project:  
`Build Path -> Configure Build Path -> Add external JARs -> choose: jflex/lib/java-cup-11a.jar and jflex/lib/jflex-1.6.1.jar`

## Running it

### Lexical Analysis

The lexical definition can be seen at [core/src/lexical/language.jflex](core/src/lexical/language.jflex).

1. Run [core/src/lexical/Generator.java](core/src/lexical/Generator.java)
2. If you want to test the lexical analysis run [core/src/lexical/TestLexical.java](core/src/lexical/TestLexical.java)

### Syntax Analysis

1. Generate the Parser.java and Sym.java
```bash
cd core/src/syntax/
java -jar <path to java-cup-11a.jar> -package syntax -parser Parser -symbols Sym -interface Parser.cup
```
2. If you want to test the syntax analysis run [core/src/sytax/TestSyntax.java](core/src/sytax/TestSyntax.java)
