package Lexer;

import Reader.Position;
import Reader.Reader;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

public class Lexer {

    private HashMap<Character, TokenTypes> singleCharacter;
    private HashMap<String, TokenTypes> specialCharacter;
    private Vector<Token> tokens;
    private Reader reader;
    private Position pos;
    private int maxLength;
    private int tokensBuff;
    private int maxTokens = 2;

    public Lexer(@NotNull Reader r, int ml, int mt)
    {
        createHashMap();
        this.reader = r;
        this.maxLength = ml;
        this.tokens = new Vector<>();
        this.tokensBuff = 0;
        this.maxTokens = mt;
    }

    public Token getCurrentToken() throws Exception
    {
        if(tokens.size() == 0)
            advance();
        else if(tokens.size() == 1)
            return tokens.elementAt(0);
        return tokens.elementAt(tokensBuff);
    }

    public Token getCurrentAndAdvance() throws Exception
    {
        Token t = getCurrentToken();
        advance();
        return t;
    }

    public void advance() throws Exception
    {
        if(tokens.size() != 0 && tokensBuff + 1 < tokens.size())
        {
            tokensBuff++;
            return;
        }
        addToken(getNextToken());
    }

    public void regress()
    {
        if(tokensBuff > 0)
            tokensBuff--;
    }

    public void addToken(Token t)
    {
        if(tokens.size() >= maxTokens)
            tokens.remove(0);
        if(tokensBuff < tokens.size())
            tokensBuff++;
        tokens.add(t);
    }

    public Token getNextToken() throws Exception
    {
        Token t = null;
        TokenTypes name;

        skipWhiteChar();
        StringBuilder context = new StringBuilder(reader.getContextBuilder());
        this.pos = new Position(reader.getPosition());

        if( reader.getCurrent() == 0xFFFF )
            t = new Token(TokenTypes.EOF, this.pos, null);

        if ( (name = singleCharacter.get(reader.getCurrent())) != null){
            t = new Token(name, this.pos, context.append(reader.getCurrent()).toString());
            reader.consume();
        }

        if( t == null )
            t = createOperator(context);

        if( t == null )
            t = createStringToken(context);

        if( t == null )
            t = createIdentifierToken(context);

        if( t == null )
            t = createIntToken(context);

        if( t == null )
            throw new Exception("Failed to create token: " + reader.getCurrent());

        return t;

    }

    private void skipWhiteChar() throws IOException
    {
        while(Character.isWhitespace(reader.getCurrent()))
            reader.consume();
    }

    private Token createStringToken(StringBuilder context) throws IOException
    {
        if( reader.getCurrent() == '"') {
            StringBuilder sb = new StringBuilder();
            reader.consume();           // pozbywamy się pierwszego symbolu "
            boolean isEnd = (reader.getCurrent() == '"' || reader.getCurrent() == 0xFFFF);

            while (!isEnd && sb.length() < this.maxLength) {
                sb.append(reader.getCurrent());
                reader.consume();
                if (reader.getCurrent() == '"' || reader.getCurrent() == 0xFFFF)
                    isEnd = true;
            }

            reader.consume();           // pozbywamy się drugiego symbolu " oznaczającego koniec stringa
            return new MessToken(this.pos, context.append(sb).toString(), sb.toString());
        }
        else
            return null;
    }

    private Token createIdentifierToken(StringBuilder context) throws IOException
    {
        if(isValidCharacter(reader.getCurrent())) {
            StringBuilder sb = new StringBuilder();
            sb.append(reader.getCurrent());
            reader.consume();

            while (isValidCharacter(reader.getCurrent()) && sb.length() < this.maxLength)
            {
                sb.append(reader.getCurrent());
                reader.consume();
            }

            String string = sb.toString();

            TokenTypes name;
            if ((name = specialCharacter.get(string)) != null)
                if( name == TokenTypes.BOOLEAN )
                {
                    if(string.equals("true"))
                        return new BoolToken(this.pos, context.append(string).toString(), true);
                    else
                        return new BoolToken(this.pos, context.append(string).toString(), false);
                }
                else
                    return new Token(name, this.pos, context.append(string).toString());
            else
                return new IdentifierToken(this.pos, context.append(string).toString(), string);
        }
        else
            return null;
    }

    private Token createIntToken(StringBuilder context) throws Exception
    {
        if(isDigit(reader.getCurrent()))
        {
            if (reader.getCurrent() == '0')
            {
                IntToken it = new IntToken(this.pos, 0);
                reader.consume();
                return it;
            }
            else
            {
                StringBuilder sb = new StringBuilder();
                sb.append(reader.getCurrent());
                reader.consume();

                while (isDigit(reader.getCurrent()))
                {
                    sb.append(reader.getCurrent());
                    reader.consume();
                }

                return new IntToken(this.pos, context.append(sb).toString(), Integer.parseInt(sb.toString()));
            }
        }
        else
            return null;
    }

    private Token createOperator(StringBuilder context) throws Exception
    {
        if(isOperator(reader.getCurrent())) {
            StringBuilder sb = new StringBuilder();
            sb.append(reader.getCurrent());
            reader.consume();

            if(reader.getCurrent() == '=')
            {
                sb.append(reader.getCurrent());
                reader.consume();

                String op = sb.toString();
                String c = context.append(op).toString();
                TokenTypes type = specialCharacter.get(op);

                return new Token(type, this.pos, c);
            }
            else
            {
                String op = sb.toString();
                TokenTypes name;
                if ((name = specialCharacter.get(op)) != null)
                    return new Token(name, this.pos, context.append(op).toString());
                else
                    throw new Exception("Error during operator creation: " + op);
            }
        }
        else
            return null;
    }

    private boolean isOperator(char c) { return c == '!' || ('<' <= c && c <= '>'); }

    private boolean isValidCharacter(char c) { return ( '?' <= c && c <= ']' ) || ('a' <= c && c <= 'z' ); }

    private boolean isDigit(char c) { return '0' <= c && c <= '9'; }

    private void createHashMap()
    {
        this.singleCharacter = new HashMap<>();
        this.specialCharacter = new HashMap<>();

        singleCharacter.put('+', TokenTypes.PLUS);
        singleCharacter.put('-', TokenTypes.MINUS);
        singleCharacter.put('*', TokenTypes.MULT);
        singleCharacter.put('/', TokenTypes.DIVIDE);
        singleCharacter.put('%', TokenTypes.MOD_OP);
        singleCharacter.put('^', TokenTypes.POW_OP);
        singleCharacter.put('v', TokenTypes.OR_OP);
        singleCharacter.put('&', TokenTypes.AND_OP);
        singleCharacter.put(';', TokenTypes.SEMICOLON);
        singleCharacter.put(',', TokenTypes.COMMA);
        singleCharacter.put('|', TokenTypes.BAR);
        singleCharacter.put('$', TokenTypes.DOLLAR);
        singleCharacter.put('_', TokenTypes.FLOOR);
        singleCharacter.put('(', TokenTypes.ROUND_OPEN);
        singleCharacter.put(')', TokenTypes.ROUND_CLOSE);
        singleCharacter.put('{', TokenTypes.CURL_OPEN);
        singleCharacter.put('}', TokenTypes.CURL_CLOSE);

        specialCharacter.put("num", TokenTypes.NUM);
        specialCharacter.put("mess", TokenTypes.MESS);
        specialCharacter.put("bool", TokenTypes.BOOL);
        specialCharacter.put(">=", TokenTypes.GREATER_EQUAL);
        specialCharacter.put(">", TokenTypes.GREATER);
        specialCharacter.put("<=", TokenTypes.LESS_EQUAL);
        specialCharacter.put("<", TokenTypes.LESS);
        specialCharacter.put("==", TokenTypes.EQUAL);
        specialCharacter.put("!=", TokenTypes.NOT_EQUAL);
        specialCharacter.put("class", TokenTypes.CLASS);
        specialCharacter.put("return", TokenTypes.RETURN);
        specialCharacter.put("if", TokenTypes.IF);
        specialCharacter.put("elseif", TokenTypes.ELSEIF);
        specialCharacter.put("else", TokenTypes.ELSE);
        specialCharacter.put("while", TokenTypes.WHILE);
        specialCharacter.put("for", TokenTypes.FOR);
        specialCharacter.put("ref", TokenTypes.REF);
        specialCharacter.put("=", TokenTypes.ASSIGNMENT_OP);
        specialCharacter.put("!", TokenTypes.NOT_OP);
        specialCharacter.put("true", TokenTypes.BOOLEAN);
        specialCharacter.put("false", TokenTypes.BOOLEAN);
    }

    public Vector<Token> getTokens() { return this.tokens; }

    public int getTokensBuff() { return this.tokensBuff; }
}
