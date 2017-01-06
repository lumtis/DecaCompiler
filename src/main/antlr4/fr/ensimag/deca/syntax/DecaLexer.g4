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

//DUMMY_TOKEN : .;
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
fragment DIGIT : '0' .. '9';
fragment POSITIVE_DIGIT : '1' .. '9';
fragment LETTER : ('a' .. 'z' | 'A' .. 'Z');
fragment STRING_CAR : ~('"' | '\n' | '\\') ;
SIGN : '+' | '-' | ;
NUM : DIGIT+;
EXP : ('E' | 'e' ) SIGN NUM;
INT : '0' | POSITIVE_DIGIT*;
DEC:NUM DOT NUM;
FLOATDEC: (DEC | DEC EXP)('F' | 'f' | ' ');
DIGITHEX : DIGIT | ('A' .. 'F') | ('a' .. 'f' );
NUMHEX : DIGITHEX+;
FLOATHEX : ('0x' | '0X' ) NUMHEX DOT NUMHEX ('P' | 'p') SIGN NUM ('F' | 'f' | ' ');
FLOAT :FLOATDEC + FLOATHEX;
STRING : '"' (STRING_CAR | '\"' | '\\')* '"' ;
MULTI_LINE_STRING:'"' (STRING_CAR | '\n' | '\"' | '\\')* '"' ;
IDENT:(LETTER | '$' | '_')(LETTER | DIGIT | '$' | '_')* ;//A rajouter : exception mot clé java ne peuvent pas être des noms de variables/

FILENAME : (LETTER | DIGIT | DOT | MINUS | '_' )+;
INCLUDE : '#include' (' ')* '"' FILENAME '"';
CLE: ASM | PROTECTED | EXTENDS | CLASS | NULL | THIS| FALSE | TRUE | NEW | READFLOAT | READINT ;

COMMENT_CLASSIC: '/*' .* '*/'
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