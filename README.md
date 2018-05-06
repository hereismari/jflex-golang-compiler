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

```bash
cd src/core/lexical/
java -jar <path to jflex-1.6.1.jar> language.lex
```

### Syntax Analysis

```bash
cd src/core/syntax/
java -jar <path to java-cup-11a.jar> -parser Parser -symbols Sym Parser.cup
```

### Run both Analysis

Fix packages and imports for the files generated and run compiler/Main.py using your IDE.

### Run Compiler
