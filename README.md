# GoCompiler

A Golang compiler implemented using JFlex + Cup.

![](https://cdn-images-1.medium.com/max/480/1*vHUiXvBE0p0fLRwFHZuAYw.gif)

## Code generation (portuguese only)

* escopo comum
   * Realizar checagem de tipos e contextos:
     - [X] Abstrações (nome, quantidade e tipos de parâmetros de entrada e tipo de retorno);
     - [X] Declaração;
     - [X] Uso de Variáveis;
     - [X] Comandos de atribuição;
     - [X] Expressões aritméticas;
     - [X] literais (inteiros, string, booleanos).

* escopo A
     - [X] Funções
     - [X] Expressões relacionais
     - [X] Comandos condicionais: if-else

## Semantic Analysis (portuguese only)

* escopo comum
   * Realizar checagem de tipos e contextos:
     - [x] Tipos existentes (considerando String, float, int, bool);
     - [X] Abstrações (nome, quantidade e tipos de parâmetros de entrada e tipo de retorno);
     - [X] Declaração;
     - [X] Uso de Variáveis;
     - [X] Comandos de atribuição;
     - [X] Expressões aritméticas;
     - [X] literais (inteiros, string, booleanos).

* escopo A
     - [X] Funções
     - [X] Expressões relacionais
     - [X] Comandos condicionais: if-else

## Syntax Analysis (portuguese only)

### Hot Fixes :fire: !!!

Precisamos utilizar 3 símbolos diferentes não existentes na linguagem GO para remover ambiguidades em casos recursivos relacionados a Expression, os símbolos são: **INTERROGATION (?), DOUBLE_INTERROGATION (??) e HASH (#)**.

Utilizados nos seguintes casos:

* Quando X -> B | Expression. E FIRST(B) incluso em FIRST(Expression).Esse caso é resolvido utilizando HASH antes de B ou de maneira similar. Quando X -> Expression (Y | ) Expression, transformamos em X -> Expression (Y | HASH) Expression. 7 regras foram modificados para esse caso.

* Quando é possível ir para uma regra X onde FIRST(X) contém "( alfa" e também Expression. Esse caso é corrigido trocando a regra "( alfa " por INTERROGATION alfa. Utilizada apenas para 1 regra: Conversion.

* **Para resolver conflitos com CURLY_L, CompositeLit foi retirado da gramática.** (Em conflitos com Expression e Block utilizamos ?? antes da definição do Block. 3 regras alteradas: For, If e Switch.)

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

### Semantic Analysis

run core/src/semantic/TestSemantic.java

### Code Generation

run core/src/semantic/TestCodeGenerator.java
