package Lexer;

import org.jetbrains.annotations.NotNull;
import Reader.Position;
import Reader.Reader;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {

    private HashMap<Character, Tokens> singleCharacter;
    private HashMap<String, Tokens> specialCharacter;
    private Reader reader;
    private char current;
    private Position pos;

    public Lexer(@NotNull Reader r){
        createHashMap();
        this.reader = r;
    }

    public Token getNextToken() throws Exception {
        Token t;
        Tokens name;

        current = reader.getCurrent();
        skipWhiteChar();

        this.pos = new Position(reader.getPosition());

        if ( (name = singleCharacter.get(current)) != null){
            t = new Token(name, this.pos, Character.toString(current));
            reader.consume();
        }
        else if (isOperator(current))
            t = createOperator();
        else if(current == '"')
            t = createStringToken();
        else if (isValidCharacter(current))
            t = createIdentifierToken();
        else if (isDigit(current))
            t = createIntToken();
        else if ( current == 0xFFFF)
            t = new Token(Tokens.EOF, this.pos, null);
        else
            throw new Exception("Failed to create token: " + current);
        return t;
    }

    private void skipWhiteChar() throws IOException
    {
        while(Character.isWhitespace(current))
        {
            reader.consume();
            current = reader.getCurrent();
        }
    }

    private Token createStringToken() throws IOException {
        StringBuilder sb = new StringBuilder();
        reader.consume();           // pozbywamy się pierwszego symbolu "
        boolean isEnd = (current = reader.getCurrent()) == '"';

        while(!isEnd)
        {
            sb.append(current);
            reader.consume();
            if((current = reader.getCurrent()) == '"')
                isEnd = true;
        }

        reader.consume();           // pozbywamy się drugiego symbolu " oznaczającego koniec stringa
        return new Token(Tokens.STRING, this.pos, sb.toString());
    }

    private Token createIdentifierToken() throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(current);
        reader.consume();

        current = reader.getCurrent();
        while(isValidCharacter(current))
        {
            sb.append(current);
            reader.consume();
            current = reader.getCurrent();
        }

        String string = sb.toString();

        Tokens name;
        if( (name = specialCharacter.get(string)) != null)
            return new Token(name, this.pos, string);
        else
            return new Token(Tokens.IDENTIFIER, this.pos, string);
    }

    private Token createIntToken() throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(current);
        reader.consume();

        current = reader.getCurrent();
        while(47 < current && current < 58)
        {
            sb.append(current);
            reader.consume();
            current = reader.getCurrent();
        }

        String string = sb.toString();
        Pattern p = Pattern.compile("([1-9][0-9]*)|0");
        Matcher m = p.matcher(string);
        if(m.matches())
            return new Token(Tokens.INT, this.pos, string);
        else
            throw new Exception("Incorrect number syntax: " + string);
    }

    private boolean isOperator(char c) { return c == 33 || (59 < c && c < 63); }

    private boolean isValidCharacter(char c) { return (62 < c && c < 91) || (95 < c && c < 123); }

    private boolean isDigit(char c) { return 47 < c && c < 58; }

    private Token createOperator() throws Exception
    {
        StringBuilder sb = new StringBuilder();
        sb.append(current);
        reader.consume();

        current = reader.getCurrent();
        while( isOperator(current))
        {
            sb.append(current);
            reader.consume();
            current = reader.getCurrent();
        }

        String string = sb.toString();
        Tokens name;
        if((name = specialCharacter.get(string)) != null)
            return new Token(name, this.pos, string);
        else
            throw new Exception("Error during operator creation: " + string);
    }

    private void createHashMap() {
        this.singleCharacter = new HashMap<>();
        this.specialCharacter = new HashMap<>();

        singleCharacter.put('+', Tokens.ADD_OP);
        singleCharacter.put('-', Tokens.ADD_OP);
        singleCharacter.put('*', Tokens.MULT_OP);
        singleCharacter.put('/', Tokens.MULT_OP);
        singleCharacter.put('%', Tokens.MOD_OP);
        singleCharacter.put('^', Tokens.POW_OP);
        singleCharacter.put('v', Tokens.OR_OP);
        singleCharacter.put('&', Tokens.AND_OP);
        singleCharacter.put(';', Tokens.SEMICOLON);
        singleCharacter.put(',', Tokens.COLON);
        singleCharacter.put('|', Tokens.BAR);
        singleCharacter.put('$', Tokens.DOLLAR);
        singleCharacter.put('_', Tokens.FLOOR);
        singleCharacter.put('(', Tokens.ROUND_OPEN);
        singleCharacter.put(')', Tokens.ROUND_CLOSE);
        singleCharacter.put('{', Tokens.CURL_OPEN);
        singleCharacter.put('}', Tokens.CURL_CLOSE);

        specialCharacter.put("num", Tokens.NUM);
        specialCharacter.put("mess", Tokens.MESS);
        specialCharacter.put("bool", Tokens.BOOL);
        specialCharacter.put(">=", Tokens.COMPARISON_OP);
        specialCharacter.put(">", Tokens.COMPARISON_OP);
        specialCharacter.put("<=", Tokens.COMPARISON_OP);
        specialCharacter.put("<", Tokens.COMPARISON_OP);
        specialCharacter.put("==", Tokens.COMPARISON_OP);
        specialCharacter.put("!=", Tokens.COMPARISON_OP);
        specialCharacter.put("class", Tokens.CLASS);
        specialCharacter.put("return", Tokens.RETURN);
        specialCharacter.put("if", Tokens.IF);
        specialCharacter.put("elseif", Tokens.ELSEIF);
        specialCharacter.put("else", Tokens.ELSE);
        specialCharacter.put("while", Tokens.WHILE);
        specialCharacter.put("for", Tokens.FOR);
        specialCharacter.put("ref", Tokens.REF);
        specialCharacter.put("=", Tokens.ASSIGNMENT_OP);
        specialCharacter.put("!", Tokens.NOT_OP);
        specialCharacter.put("true", Tokens.BOOLEAN);
        specialCharacter.put("false", Tokens.BOOLEAN);
    }

}
