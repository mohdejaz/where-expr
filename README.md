# where-expr

Embedded java parser/evaluator for where like expression.

Here's how to use it ...

```java
// Input is from String
CharStream input=CharStreams.fromString("(upd >= '2021-05-30') and (upd < '2021-06-07')");
// Create Lexer
    LabeledWhereExprLexer lexer=new LabeledWhereExprLexer(input);
// Tokens
    CommonTokenStream tokens=new CommonTokenStream(lexer);
// Parser
    LabeledWhereExprParser parser=new LabeledWhereExprParser(tokens);
// Get Parse Tree
    ParseTree tree=parser.start();
// Dump it if you like
    System.out.println("tree: "+tree.toStringTree(parser));
// Create a map to hold variables in expression
    Map<String, Object> vars=new HashMap<>();
// Populate
    vars.put("upd",LocalDate.parse("2021-06-06"));
// Evaluate expression
    EvalWhereVisitor visitor=new EvalWhereVisitor(vars);
    System.out.println(visitor.visit(tree));
```

## Grammar

Here's the grammar for where expression parse/evaluator.

```antlrv4
grammar LabeledWhereExpr;
@header {
package parser;
}

start
    :	expr+
    ;

expr
    : ID '(' (expr (',' expr)*)? ')'    #func
    | '-' expr                          #unminus
    | '!' expr                          #not
    | expr op=('/'|'*') expr            #mult
    | expr op=('+'|'-') expr            #add
    | expr '~' expr                     #like
    | expr op=('='|'!=') expr           #eq
    | expr op=('>'|'>='|'<'|'<=') expr	#rel
    | expr 'and' expr                   #and
    | expr 'or' expr                    #or
    | ID 'in' '[' expr (',' expr)* ']'	#idin
    | INT                               #int
    | DATE                              #date
    | STR                               #str
    | ID                                #id
    | '(' expr ')'                      #parens
    ;

AND     : 'and';
OR      : 'or';
EQ      : '=' ;
NEQ     : '!=';
GT      : '>';
GE      : '>=';
LT      : '<';
LE      : '<=';
ADD     : '+' ;
SUB     : '-' ;
MUL     : '*' ;
DIV     : '/' ;
LIKE    : '~' ;
DATE    : '\'' DIGIT DIGIT DIGIT DIGIT '-' DIGIT DIGIT '-' DIGIT DIGIT '\'';
ID      : ID_LETTER (ID_LETTER | DIGIT)* ;
INT     : DIGIT+ ('.' DIGIT*)?
        | '.' DIGIT+
        ;
STR     : '"' ( ESC | . )*? '"' ;
WS      : [ \r\n\t]+ -> skip ;
fragment
ESC     : '\\' [btnr"\\] ; // \b, \t, \n etc...
fragment
ID_LETTER : 'a'..'z'|'A'..'Z'|'_' ;
fragment
DIGIT   : '0'..'9' ;
```