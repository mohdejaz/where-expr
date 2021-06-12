grammar LabeledWhereExpr;
@header {
package parser;
}

start
	:	expr+
	;

expr
	: ID '(' (expr (',' expr)*)? ')'    #func
	| '-' expr							#unminus
	| '!' expr							#not
	| expr op=('/'|'*') expr			#mult
	| expr op=('+'|'-') expr			#add
	| expr '~' expr						#like
	| expr op=('='|'!=') expr           #eq
	| expr op=('>'|'>='|'<'|'<=') expr	#rel
	| expr 'and' expr					#and
	| expr 'or' expr					#or
	| ID 'in' '[' expr (',' expr)* ']'	#idin
	| INT								#int
	| DATE								#date
	| STR								#str
	| ID								#id
	| '(' expr ')'						#parens
	;

AND		: 'and';
OR		: 'or';
EQ		: '=' ;
NEQ		: '!=';
GT		: '>';
GE		: '>=';
LT		: '<';
LE		: '<=';
ADD		: '+' ;
SUB		: '-' ;
MUL		: '*' ;
DIV		: '/' ;
LIKE	: '~' ;
DATE    : '\'' DIGIT DIGIT DIGIT DIGIT '-' DIGIT DIGIT '-' DIGIT DIGIT '\'';
ID		: ID_LETTER (ID_LETTER | DIGIT)* ;
INT		: DIGIT+ ('.' DIGIT*)?
			| '.' DIGIT+
			;
STR		: '"' ( ESC | . )*? '"' ;
WS		: [ \r\n\t]+ -> skip ;
fragment
ESC : '\\' [btnr"\\] ; // \b, \t, \n etc...
fragment
ID_LETTER : 'a'..'z'|'A'..'Z'|'_' ;
fragment
DIGIT : '0'..'9' ;
