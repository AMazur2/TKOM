package Lexer;

import Reader.Position;
import Reader.Reader;
import org.junit.Assert;
import org.junit.Test;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Vector;

public class LexerTest {

    @Test
    public void lexerTest() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("tests/lexerTest.txt"));
        Reader r = new Reader(br);
        Lexer l = new Lexer(r);

        Vector<Token> expected = createExpectedTokens();
        Vector<Token> given = new Vector<>();

        Token t = l.getNextToken();
        while(t.getName() != Tokens.EOF)
        {
            given.add(t);
            t = l.getNextToken();
        }

        Assert.assertEquals(given.size(), expected.size());

        for(int i = 0; i < given.size(); ++i)
            Assert.assertTrue(given.elementAt(i).isEqual(expected.elementAt(i)));
    }

    @Test
    public void multiLineStringTest() throws Exception
    {
        BufferedReader br = new BufferedReader(new FileReader("tests/multiLineString.txt"));
        Reader r = new Reader(br);
        Lexer l = new Lexer(r);

        Vector<Token> expected = createExpectedTokens2();

        Vector<Token> given = new Vector<>();

        Token t = l.getNextToken();
        while(t.getName() != Tokens.EOF)
        {
            given.add(t);
            t = l.getNextToken();
        }

        Assert.assertEquals(given.size(), expected.size());

        for(int i = 0; i < given.size(); ++i)
            Assert.assertTrue(given.elementAt(i).isEqual(expected.elementAt(i)));
    }

    @Test
    public void exampleCodeTest() throws Exception
    {
        BufferedReader br = new BufferedReader(new FileReader("tests/exampleCodeTest.txt"));
        Reader r = new Reader(br);
        Lexer l = new Lexer(r);

        Vector<Token> expected = createExpectedTokens3();

        Vector<Token> given = new Vector<>();

        Token t = l.getNextToken();
        while(t.getName() != Tokens.EOF)
        {
            given.add(t);
            t = l.getNextToken();
        }

        Assert.assertEquals(given.size(), expected.size());

        for(int i = 0; i < given.size(); ++i)
            Assert.assertTrue(given.elementAt(i).isEqual(expected.elementAt(i)));
    }

    private Vector<Token> createExpectedTokens()
    {
        Vector<Token> expected = new Vector<>();
        expected.add(new Token(Tokens.CLASS, new Position(1,1), "class"));
        expected.add(new Token(Tokens.ROUND_OPEN, new Position(1,2), "("));
        expected.add(new Token(Tokens.ROUND_CLOSE, new Position(3,2), ")"));
        expected.add(new Token(Tokens.CURL_OPEN, new Position(4,2), "{"));
        expected.add(new Token(Tokens.CURL_CLOSE, new Position(5,2), "}"));
        expected.add(new Token(Tokens.ADD_OP, new Position(2,3), "+"));
        expected.add(new Token(Tokens.ADD_OP, new Position(1,4), "-"));
        expected.add(new Token(Tokens.MULT_OP, new Position(3,4), "*"));
        expected.add(new Token(Tokens.MULT_OP, new Position(5,4), "/"));
        expected.add(new Token(Tokens.MOD_OP, new Position(1,5), "%"));
        expected.add(new Token(Tokens.POW_OP, new Position(3,5), "^"));
        expected.add(new Token(Tokens.FLOOR, new Position(1,6), "_"));
        expected.add(new Token(Tokens.DOLLAR, new Position(3,6), "$"));
        expected.add(new Token(Tokens.SEMICOLON, new Position(1,8), ";"));
        expected.add(new Token(Tokens.COLON, new Position(3,8), ","));
        expected.add(new Token(Tokens.BAR, new Position(5,8), "|"));
        expected.add(new Token(Tokens.NOT_OP, new Position(1,9), "!"));
        expected.add(new Token(Tokens.OR_OP, new Position(2,9), "v"));
        expected.add(new Token(Tokens.AND_OP, new Position(3,9), "&"));
        expected.add(new Token(Tokens.COMPARISON_OP, new Position(1,10), "=="));
        expected.add(new Token(Tokens.COMPARISON_OP, new Position(4,10), "<="));
        expected.add(new Token(Tokens.COMPARISON_OP, new Position(7,10), ">="));
        expected.add(new Token(Tokens.COMPARISON_OP, new Position(10,10), "!="));
        expected.add(new Token(Tokens.COMPARISON_OP, new Position(13,10), ">"));
        expected.add(new Token(Tokens.COMPARISON_OP, new Position(15,10), "<"));
        expected.add(new Token(Tokens.ASSIGNMENT_OP, new Position(17,10), "="));
        expected.add(new Token(Tokens.NUM, new Position(1,11), "num"));
        expected.add(new Token(Tokens.MESS, new Position(5,11), "mess"));
        expected.add(new Token(Tokens.BOOL, new Position(10,11), "bool"));
        expected.add(new Token(Tokens.INT, new Position(1,12), "245"));
        expected.add(new Token(Tokens.STRING, new Position(5,12), "test"));
        expected.add(new Token(Tokens.IF, new Position(1,13), "if"));
        expected.add(new Token(Tokens.ELSE, new Position(4,13), "else"));
        expected.add(new Token(Tokens.ELSEIF, new Position(9,13), "elseif"));
        expected.add(new Token(Tokens.FOR, new Position(16,13), "for"));
        expected.add(new Token(Tokens.WHILE, new Position(20,13), "while"));
        expected.add(new Token(Tokens.IDENTIFIER, new Position(1,14), "ifek"));
        expected.add(new Token(Tokens.IDENTIFIER, new Position(6,14), "identyfikator"));
        expected.add(new Token(Tokens.IDENTIFIER, new Position(20,14), "a"));
        expected.add(new Token(Tokens.RETURN, new Position(1,15), "return"));
        expected.add(new Token(Tokens.REF, new Position(1,16), "ref"));
        expected.add(new Token(Tokens.BOOLEAN, new Position(1,17), "true"));
        expected.add(new Token(Tokens.BOOLEAN, new Position(1,18), "false"));

        return expected;
    }

    private Vector<Token> createExpectedTokens2()
    {
        Vector<Token> expected = new Vector<>();
        char c = 13;
        String es = "bardzo" + c + "\ndlugi" + c + "\nstring";

        expected.add(new Token(Tokens.MESS, new Position(1,1), "mess"));
        expected.add(new Token(Tokens.STRING, new Position(1,2), es));
        expected.add(new Token(Tokens.ASSIGNMENT_OP, new Position(1,5), "="));
        expected.add(new Token(Tokens.ADD_OP, new Position(1,6), "-"));
        expected.add(new Token(Tokens.INT, new Position(2,6), "546"));

        return expected;
    }

    private Vector<Token> createExpectedTokens3()
    {
        Vector<Token> expected = new Vector<>();

        expected.add(new Token(Tokens.CLASS, new Position(1,1), "class"));
        expected.add(new Token(Tokens.IDENTIFIER, new Position(7,1), "myClass"));
        expected.add(new Token(Tokens.CURL_OPEN, new Position(14,1), "{"));
        expected.add(new Token(Tokens.NUM, new Position(1,2), "num"));
        expected.add(new Token(Tokens.IDENTIFIER, new Position(5,2), "a"));
        expected.add(new Token(Tokens.SEMICOLON, new Position(6,2), ";"));
        expected.add(new Token(Tokens.MESS, new Position(1,3), "mess"));
        expected.add(new Token(Tokens.IDENTIFIER, new Position(6,3), "b"));
        expected.add(new Token(Tokens.SEMICOLON, new Position(7,3), ";"));
        expected.add(new Token(Tokens.BOOL, new Position(1,4), "bool"));
        expected.add(new Token(Tokens.IDENTIFIER, new Position(6,4), "c"));
        expected.add(new Token(Tokens.SEMICOLON, new Position(7,4), ";"));
        expected.add(new Token(Tokens.IDENTIFIER, new Position(1,6), "sum"));
        expected.add(new Token(Tokens.ROUND_OPEN, new Position(4,6), "("));
        expected.add(new Token(Tokens.NUM, new Position(5,6), "num"));
        expected.add(new Token(Tokens.IDENTIFIER, new Position(9,6), "e"));
        expected.add(new Token(Tokens.COLON, new Position(10,6), ","));
        expected.add(new Token(Tokens.NUM, new Position(12,6), "num"));
        expected.add(new Token(Tokens.IDENTIFIER, new Position(16,6), "f"));
        expected.add(new Token(Tokens.ROUND_CLOSE, new Position(17,6), ")"));
        expected.add(new Token(Tokens.SEMICOLON, new Position(18,6), ";"));
        expected.add(new Token(Tokens.CURL_CLOSE, new Position(1,7), "}"));
        expected.add(new Token(Tokens.IDENTIFIER, new Position(1,9), "print"));
        expected.add(new Token(Tokens.ROUND_OPEN, new Position(6,9), "("));
        expected.add(new Token(Tokens.ROUND_CLOSE, new Position(7,9), ")"));
        expected.add(new Token(Tokens.CURL_OPEN, new Position(1,10), "{"));
        expected.add(new Token(Tokens.IDENTIFIER, new Position(1,11), "myClass"));
        expected.add(new Token(Tokens.FLOOR, new Position(8,11), "_"));
        expected.add(new Token(Tokens.IDENTIFIER, new Position(9,11), "a"));
        expected.add(new Token(Tokens.SEMICOLON, new Position(10,11), ";"));
        expected.add(new Token(Tokens.IDENTIFIER, new Position(1,12), "c"));
        expected.add(new Token(Tokens.ASSIGNMENT_OP, new Position(3,12), "="));
        expected.add(new Token(Tokens.IDENTIFIER, new Position(5,12), "a"));
        expected.add(new Token(Tokens.ADD_OP, new Position(7,12), "+"));
        expected.add(new Token(Tokens.INT, new Position(9,12), "1"));
        expected.add(new Token(Tokens.SEMICOLON, new Position(10,12), ";"));
        expected.add(new Token(Tokens.WHILE, new Position(1,14), "while"));
        expected.add(new Token(Tokens.ROUND_OPEN, new Position(6,14), "("));
        expected.add(new Token(Tokens.IDENTIFIER, new Position(8,14), "d"));
        expected.add(new Token(Tokens.COMPARISON_OP, new Position(10,14), "<="));
        expected.add(new Token(Tokens.INT, new Position(13,14), "3"));
        expected.add(new Token(Tokens.ROUND_CLOSE, new Position(15,14), ")"));
        expected.add(new Token(Tokens.CURL_OPEN, new Position(1,15), "{"));
        expected.add(new Token(Tokens.IDENTIFIER, new Position(1,16), "d"));
        expected.add(new Token(Tokens.ASSIGNMENT_OP, new Position(3,16), "="));
        expected.add(new Token(Tokens.IDENTIFIER, new Position(5,16), "d"));
        expected.add(new Token(Tokens.ADD_OP, new Position(7,16), "+"));
        expected.add(new Token(Tokens.INT, new Position(9,16), "1"));
        expected.add(new Token(Tokens.SEMICOLON, new Position(10,16), ";"));
        expected.add(new Token(Tokens.CURL_CLOSE, new Position(1,17), "}"));
        expected.add(new Token(Tokens.RETURN, new Position(1,19), "return"));
        expected.add(new Token(Tokens.IDENTIFIER, new Position(8,19), "c"));
        expected.add(new Token(Tokens.SEMICOLON, new Position(9,19), ";"));
        expected.add(new Token(Tokens.CURL_CLOSE, new Position(1,20), "}"));

        return expected;
    }

}
