lexer grammar DecaLexer;

options {
   language=Java;
   // Tell ANTLR to make the generated lexer class extend the
   // the named class, which is where any supporting code and
   // variables will be placed.
   superClass = AbstractDecaLexer;
}

@members {
}

// Deca lexer rules.

//Fragment rules
fragment DIGIT : '0' .. '9';
fragment POSITIVE_DIGIT : '1' .. '9';
fragment LETTER : ('a' .. 'z' | 'A' .. 'Z');
fragment STRING_CAR : ~('"' | '\n' | '\\') ;
fragment SIGN : '+' | '-' | ;
fragment NUM : DIGIT+;
fragment DEC:NUM DOT NUM;
fragment FLOATDEC: (DEC | DEC EXP)('F' | 'f' | );
fragment DIGITHEX : DIGIT | ('A' .. 'F') | ('a' .. 'f' );
fragment NUMHEX : DIGITHEX+;
fragment FLOATHEX : ('0x' | '0X' ) NUMHEX DOT NUMHEX ('P' | 'p') SIGN NUM ('F' | 'f' | );
fragment FILENAME : (LETTER | DIGIT | DOT | MINUS | '_' )+;

//non fragment rules
OBRACE : '{' ;
CBRACE : '}' ;
OPARENT : '(' ;
CPARENT : ')' ;
SEMI : ';' ;
COMMA : ',';
EQUALS : '=' ;
PRINT : 'print' ;
PRINTLN : 'println' ;
PRINTX : 'printx' ;
PRINTLNX : 'printlnx' ;
WHILE : 'while' ;
RETURN : 'return' ;
ELSE : 'else' ;
IF : 'if' ;
OR :'||' ;
AND : '&&' ;
EQEQ : '==' ;
NEQ : '!=' ;
LEQ : '<=' ;
GEQ : '>=' ;
GT: '>' ;
LT: '<' ;
INSTANCEOF : 'instanceof' ;
PLUS : '+' ;
MINUS : '-' ;
TIMES : '*' ;
SLASH : '/' ;
PERCENT : '%' ;
EXCLAM : '!' ;
DOT : '.' ;
READINT: 'readInt' ;
READFLOAT: 'readFloat' ;
NEW : 'new' ;
TRUE: 'true' ;
FALSE: 'false' ;
THIS: 'this' ;
NULL: 'null' ;
CLASS: 'class' ;
EXTENDS : 'extends' ;
PROTECTED : 'protected';
ASM: 'asm' ;

EXP : ('E' | 'e' ) SIGN NUM;
INT : '0' | POSITIVE_DIGIT DIGIT*;
FLOAT :FLOATDEC | FLOATHEX;
STRING : '"' (STRING_CAR | '\"' | '\\')* '"' ;
MULTI_LINE_STRING:'"' (STRING_CAR | '\n' | '\"' | '\\')*? '"' ;
IDENT:(LETTER | '$' | '_')(LETTER | DIGIT | '$' | '_')* ;

INCLUDE : '#include' (' ')* '"' FILENAME '"';
CLE: ASM | PROTECTED | EXTENDS | CLASS | NULL | THIS| FALSE | TRUE | NEW | READFLOAT | READINT ;

COMMENT_CLASSIC: '/*' .*? '*/'
                    {
                        skip();
                    };

COMMENT_LINE :'//' (~'\n')* '\n'
                    {
                        skip();
                    };

// Ignore spaces, tabs, newlines and whitespaces
WS  :   ( ' '
        | '\t'
        | '\r'
        | '\n'
        ) {
              skip(); // avoid producing a token
          }
    ;