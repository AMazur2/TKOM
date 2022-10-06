package uni.projects.lexer;

public enum TokenTypes {
    NUM , MESS , BOOL , REF ,                                       // typy zmiennych
    PLUS , MINUS , DIVIDE , MULT , MOD_OP , POW_OP,                             // działania matematyczne
    ASSIGNMENT_OP , OR_OP , AND_OP ,  NOT_OP ,      // operatory logiczne
    GREATER, GREATER_EQUAL, LESS, LESS_EQUAL, EQUAL, NOT_EQUAL,
    STRING , INT , BOOLEAN , CLASS , RETURN , IDENTIFIER ,
    COMMA , SEMICOLON , BAR , DOLLAR, FLOOR ,
    ROUND_OPEN , ROUND_CLOSE , CURL_OPEN , CURL_CLOSE ,             // nawiasy
    IF , ELSEIF , ELSE,  WHILE , FOR ,
    EOF
}
