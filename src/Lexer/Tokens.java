package Lexer;

public enum Tokens {
    NUM , MESS , BOOL , REF ,                                       // typy zmiennych
    ADD_OP , MULT_OP , MOD_OP , POW_OP,                             // działania matematyczne
    COMPARISON_OP , ASSIGNMENT_OP , OR_OP , AND_OP ,  NOT_OP ,      // operatory logiczne
    STRING , INT , CLASS , BOOLEAN , RETURN , IDENTIFIER ,
    COLON , SEMICOLON , BAR , DOLLAR, FLOOR ,
    ROUND_OPEN , ROUND_CLOSE , CURL_OPEN , CURL_CLOSE ,             // nawiasy
    IF , ELSEIF , ELSE,  WHILE , FOR ,
    EOF
}
