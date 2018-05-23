# GoCompiler

A Golang compiler implementation.

![](https://www.devteam.space/blog/wp-content/uploads/2017/03/gopher_head-min.png)


## Hot Fixes :fire: !!!

Precisamos utilizar 3 símbolos diferentes não existentes na linguagem GO para remover ambiguidades em casos recursivos relacionados a Expression, os símbolos são: **INTERROGATION (?), DOUBLE_INTERROGATION (??) e HASH (#)**.

Utilizados nos seguintes casos:

* Quando X -> B | Expression. E FIRST(B) incluso em FIRST(Expression).Esse caso é resolvido utilizando HASH antes de B ou de maneira similar. Quando X -> Expression (Y | ) Expression, transformamos em X -> Expression (Y | HASH) Expression. 7 regras foram modificados para esse caso.

* Quando é possível ir para uma regra X onde FIRST(X) contém "( alfa" e também Expression. Esse caso é corrigido trocando a regra "( alfa " por INTERROGATION alfa. Utilizada apenas para 1 regra: Conversion.

* Em conflitos com Expression e Block utilizamos ?? antes da definição do Block. 3 regras alteradas: For, If e Switch.


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

run core/src/lexical/Generator.java

### Syntax Analysis

run core/src/syntax/Generator.java
