package Tests;

import Lexer.*;
import Reader.Position;
import Reader.Reader;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Vector;

public class LexerTest {

    @Test
    public void lexerTest() throws Exception
    {
        String data1 = "class\n( ){}\n +\n- * /\n% ^\n_ $\n\n; , |\n!v&\n== <= >= != > < =\n";
        String data2 = "num mess bool\n245 \"test\"\nif else elseif for while\nifek identyfikator a\n";
        String data3 = "return\nref\ntrue\nfalse";
        String data = data1.concat(data2).concat(data3);

        BufferedReader br = new BufferedReader(new StringReader(data));
        Reader r = new Reader(br,10);
        Lexer l = new Lexer(r, 14, 2);

        Vector<Token> expected = createExpectedTokens();
        Vector<Token> given = new Vector<>();

        Token t = l.getNextToken();
        while(t.getName() != TokenTypes.EOF)
        {
            given.add(t);
            t = l.getNextToken();
        }

        Assert.assertEquals(given.size(), expected.size());

        Assert.assertEquals("( ){", given.elementAt(3).getContext());
        Assert.assertEquals("( ){}", given.elementAt(4).getContext());
        Assert.assertEquals("- *", given.elementAt(7).getContext());
        Assert.assertEquals("if else elseif", given.elementAt(33).getContext());
        Assert.assertEquals("se elseif for", given.elementAt(34).getContext());

        for(int i = 0; i < expected.size(); ++i)
            Assert.assertTrue(given.elementAt(i).isEqual(expected.elementAt(i)));
    }

    @Test
    public void multiLineStringTest() throws Exception
    {
        String data = "mess\n\"bardzo\ndlugi\nstring\"\n=\n-546";
        BufferedReader br = new BufferedReader(new StringReader(data));
        Reader r = new Reader(br,10);
        Lexer l = new Lexer(r, 19, 2);

        Vector<Token> expected = createExpectedTokens2();

        Vector<Token> given = new Vector<>();

        Token t = l.getNextToken();
        while(t.getName() != TokenTypes.EOF)
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
        String data1 = "class myClass{\nnum a;\nmess b;\nbool c;\n\nsum(num e, num f);\n}\n\n";
        String data2 = "print()\n{\nmyClass_a;\nc = a + 1;\n\nwhile( d <= 3 )\n{\nd = d + 1;\n}\n\nreturn c;\n}";
        String data = data1.concat(data2);

        BufferedReader br = new BufferedReader(new StringReader(data));
        Reader r = new Reader(br,10);
        Lexer l = new Lexer(r, 10,2);

        Vector<Token> expected = createExpectedTokens3();

        Vector<Token> given = new Vector<>();

        Token t = l.getNextToken();
        while(t.getName() != TokenTypes.EOF)
        {
            given.add(t);
            t = l.getNextToken();
        }

        Assert.assertEquals(given.size(), expected.size());

        for(int i = 0; i < given.size(); ++i)
            Assert.assertTrue(given.elementAt(i).isEqual(expected.elementAt(i)));
    }

    @Test
    public void shortIdentifiersTest() throws Exception
    {
        String data = "abcdefghijkl";
        BufferedReader br = new BufferedReader(new StringReader(data));
        Reader r = new Reader(br,10);
        Lexer l = new Lexer(r, 4,2);

        Vector<Token> expected = createExpectedTokens4();

        Vector<Token> given = new Vector<>();

        Token t = l.getNextToken();
        while(t.getName() != TokenTypes.EOF)
        {
            given.add(t);
            t = l.getNextToken();
        }

        Assert.assertEquals(given.size(), expected.size());

        for(int i = 0; i < given.size(); ++i)
            Assert.assertTrue(given.elementAt(i).isEqual(expected.elementAt(i)));
    }

    @Test
    public void advanceRegressBufferTest() throws Exception
    {
        String data = "class myClass{\nnum a;\nmess b;\nbool c;";
        BufferedReader br = new BufferedReader(new StringReader(data));
        Reader r = new Reader(br, 10);
        Lexer l = new Lexer(r, 10,2);

        Vector<Token> expected = createExpectedTokens5();

        Token t = l.getCurrentToken();
        Assert.assertTrue(t.isEqual(expected.elementAt(0)));

        t = l.getCurrentAndAdvance();
        Assert.assertTrue(t.isEqual(expected.elementAt(0)));

        t = l.getCurrentToken();
        Assert.assertTrue(t.isEqual(expected.elementAt(1)));

        l.getCurrentAndAdvance();
        t = l.getCurrentToken();
        Assert.assertTrue(t.isEqual(expected.elementAt(2)));

        l.regress();
        Assert.assertTrue(l.getCurrentToken().isEqual(expected.elementAt(1)));

        l.regress();
        Assert.assertTrue(l.getCurrentToken().isEqual(expected.elementAt(1)));

        l.getCurrentAndAdvance();
        Assert.assertTrue(l.getCurrentToken().isEqual(expected.elementAt(2)));

        l.getCurrentAndAdvance();
        Assert.assertTrue(l.getCurrentToken().isEqual(expected.elementAt(3)));

    }

    private Vector<Token> createExpectedTokens()
    {
        Vector<Token> expected = new Vector<>();
        expected.add(new Token(TokenTypes.CLASS, new Position(1,1)));
        expected.add(new Token(TokenTypes.ROUND_OPEN, new Position(1,2)));
        expected.add(new Token(TokenTypes.ROUND_CLOSE, new Position(3,2)));
        expected.add(new Token(TokenTypes.CURL_OPEN, new Position(4,2)));
        expected.add(new Token(TokenTypes.CURL_CLOSE, new Position(5,2)));
        expected.add(new Token(TokenTypes.PLUS, new Position(2,3)));
        expected.add(new Token(TokenTypes.MINUS, new Position(1,4)));
        expected.add(new Token(TokenTypes.MULT, new Position(3,4)));
        expected.add(new Token(TokenTypes.DIVIDE, new Position(5,4)));
        expected.add(new Token(TokenTypes.MOD_OP, new Position(1,5)));
        expected.add(new Token(TokenTypes.POW_OP, new Position(3,5)));
        expected.add(new Token(TokenTypes.FLOOR, new Position(1,6)));
        expected.add(new Token(TokenTypes.DOLLAR, new Position(3,6)));
        expected.add(new Token(TokenTypes.SEMICOLON, new Position(1,8)));
        expected.add(new Token(TokenTypes.COMMA, new Position(3,8)));
        expected.add(new Token(TokenTypes.BAR, new Position(5,8)));
        expected.add(new Token(TokenTypes.NOT_OP, new Position(1,9)));
        expected.add(new Token(TokenTypes.OR_OP, new Position(2,9)));
        expected.add(new Token(TokenTypes.AND_OP, new Position(3,9)));
        expected.add(new Token(TokenTypes.EQUAL , new Position(1 , 10)));
        expected.add(new Token(TokenTypes.LESS_EQUAL , new Position(4, 10)));
        expected.add(new Token(TokenTypes.GREATER_EQUAL , new Position(7,10)));
        expected.add(new Token(TokenTypes.NOT_EQUAL , new Position(10 , 10)));
        expected.add(new Token(TokenTypes.GREATER , new Position(13 , 10)));
        expected.add(new Token(TokenTypes.LESS , new Position(15,10)));
        expected.add(new Token(TokenTypes.ASSIGNMENT_OP, new Position(17,10)));
        expected.add(new Token(TokenTypes.NUM, new Position(1,11)));
        expected.add(new Token(TokenTypes.MESS, new Position(5,11)));
        expected.add(new Token(TokenTypes.BOOL, new Position(10,11)));
        expected.add(new IntToken(new Position(1,12), 245));
        expected.add(new MessToken(new Position(5,12), "test"));
        expected.add(new Token(TokenTypes.IF, new Position(1,13)));
        expected.add(new Token(TokenTypes.ELSE, new Position(4,13)));
        expected.add(new Token(TokenTypes.ELSEIF, new Position(9,13)));
        expected.add(new Token(TokenTypes.FOR, new Position(16,13)));
        expected.add(new Token(TokenTypes.WHILE, new Position(20,13)));
        expected.add(new IdentifierToken(new Position(1,14), "ifek"));
        expected.add(new IdentifierToken(new Position(6,14), "identyfikator"));
        expected.add(new IdentifierToken(new Position(20,14), "a"));
        expected.add(new Token(TokenTypes.RETURN, new Position(1,15)));
        expected.add(new Token(TokenTypes.REF, new Position(1,16)));
        expected.add(new BoolToken(new Position(1,17), true));
        expected.add(new BoolToken(new Position(1,18), false));

        return expected;
    }

    private Vector<Token> createExpectedTokens2()
    {
        Vector<Token> expected = new Vector<>();

        String es = "bardzo\ndlugi\nstring";

        expected.add(new Token(TokenTypes.MESS, new Position(1,1)));
        expected.add(new MessToken(new Position(1,2), es));
        expected.add(new Token(TokenTypes.ASSIGNMENT_OP, new Position(1,5)));
        expected.add(new Token(TokenTypes.MINUS, new Position(1,6)));
        expected.add(new IntToken(new Position(2,6), 546));

        return expected;
    }

    private Vector<Token> createExpectedTokens3()
    {
        Vector<Token> expected = new Vector<>();

        expected.add(new Token(TokenTypes.CLASS, new Position(1,1)));
        expected.add(new IdentifierToken(new Position(7,1), "myClass"));
        expected.add(new Token(TokenTypes.CURL_OPEN, new Position(14,1)));
        expected.add(new Token(TokenTypes.NUM, new Position(1,2)));
        expected.add(new IdentifierToken(new Position(5,2), "a"));
        expected.add(new Token(TokenTypes.SEMICOLON, new Position(6,2)));
        expected.add(new Token(TokenTypes.MESS, new Position(1,3)));
        expected.add(new IdentifierToken(new Position(6,3), "b"));
        expected.add(new Token(TokenTypes.SEMICOLON, new Position(7,3)));
        expected.add(new Token(TokenTypes.BOOL, new Position(1,4)));
        expected.add(new IdentifierToken(new Position(6,4), "c"));
        expected.add(new Token(TokenTypes.SEMICOLON, new Position(7,4)));
        expected.add(new IdentifierToken(new Position(1,6), "sum"));
        expected.add(new Token(TokenTypes.ROUND_OPEN, new Position(4,6)));
        expected.add(new Token(TokenTypes.NUM, new Position(5,6)));
        expected.add(new IdentifierToken(new Position(9,6), "e"));
        expected.add(new Token(TokenTypes.COMMA, new Position(10,6)));
        expected.add(new Token(TokenTypes.NUM, new Position(12,6)));
        expected.add(new IdentifierToken(new Position(16,6), "f"));
        expected.add(new Token(TokenTypes.ROUND_CLOSE, new Position(17,6)));
        expected.add(new Token(TokenTypes.SEMICOLON, new Position(18,6)));
        expected.add(new Token(TokenTypes.CURL_CLOSE, new Position(1,7)));
        expected.add(new IdentifierToken(new Position(1,9), "print"));
        expected.add(new Token(TokenTypes.ROUND_OPEN, new Position(6,9)));
        expected.add(new Token(TokenTypes.ROUND_CLOSE, new Position(7,9)));
        expected.add(new Token(TokenTypes.CURL_OPEN, new Position(1,10)));
        expected.add(new IdentifierToken(new Position(1,11), "myClass"));
        expected.add(new Token(TokenTypes.FLOOR, new Position(8,11)));
        expected.add(new IdentifierToken(new Position(9,11), "a"));
        expected.add(new Token(TokenTypes.SEMICOLON, new Position(10,11)));
        expected.add(new IdentifierToken(new Position(1,12), "c"));
        expected.add(new Token(TokenTypes.ASSIGNMENT_OP, new Position(3,12)));
        expected.add(new IdentifierToken(new Position(5,12), "a"));
        expected.add(new Token(TokenTypes.PLUS, new Position(7,12)));
        expected.add(new IntToken(new Position(9,12), 1));
        expected.add(new Token(TokenTypes.SEMICOLON, new Position(10,12)));
        expected.add(new Token(TokenTypes.WHILE, new Position(1,14)));
        expected.add(new Token(TokenTypes.ROUND_OPEN, new Position(6,14)));
        expected.add(new IdentifierToken(new Position(8,14), "d"));
        expected.add(new Token(TokenTypes.LESS_EQUAL , new Position(10, 14)));
        expected.add(new IntToken(new Position(13,14), 3));
        expected.add(new Token(TokenTypes.ROUND_CLOSE, new Position(15,14)));
        expected.add(new Token(TokenTypes.CURL_OPEN, new Position(1,15)));
        expected.add(new IdentifierToken(new Position(1,16), "d"));
        expected.add(new Token(TokenTypes.ASSIGNMENT_OP, new Position(3,16)));
        expected.add(new IdentifierToken(new Position(5,16), "d"));
        expected.add(new Token(TokenTypes.PLUS, new Position(7,16)));
        expected.add(new IntToken(new Position(9,16), 1));
        expected.add(new Token(TokenTypes.SEMICOLON, new Position(10,16)));
        expected.add(new Token(TokenTypes.CURL_CLOSE, new Position(1,17)));
        expected.add(new Token(TokenTypes.RETURN, new Position(1,19)));
        expected.add(new IdentifierToken(new Position(8,19), "c"));
        expected.add(new Token(TokenTypes.SEMICOLON, new Position(9,19)));
        expected.add(new Token(TokenTypes.CURL_CLOSE, new Position(1,20)));

        return expected;
    }

    private Vector<Token> createExpectedTokens4()
    {
        Vector<Token> expected = new Vector<>();

        expected.add(new IdentifierToken(new Position(1,1), "abcd"));
        expected.add(new IdentifierToken(new Position(5,1), "efgh"));
        expected.add(new IdentifierToken(new Position(9,1), "ijkl"));

        return expected;
    }

    private Vector<Token> createExpectedTokens5()
    {
        Vector<Token> expected = new Vector<>();

        expected.add(new Token(TokenTypes.CLASS, new Position(1,1)));
        expected.add(new IdentifierToken(new Position(7,1), "myClass"));
        expected.add(new Token(TokenTypes.CURL_OPEN, new Position(14,1)));
        expected.add(new Token(TokenTypes.NUM, new Position(1,2)));
        expected.add(new IdentifierToken(new Position(5,2), "a"));
        expected.add(new Token(TokenTypes.SEMICOLON, new Position(6,2)));
        expected.add(new Token(TokenTypes.MESS, new Position(1,3)));
        expected.add(new IdentifierToken(new Position(6,3), "b"));
        expected.add(new Token(TokenTypes.SEMICOLON, new Position(7,3)));
        expected.add(new Token(TokenTypes.BOOL, new Position(1,4)));
        expected.add(new IdentifierToken(new Position(6,4), "c"));

        return expected;
    }
}