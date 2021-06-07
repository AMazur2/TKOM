package Tests;

import Lexer.IdentifierToken;
import Lexer.Token;
import Lexer.TokenTypes;
import Reader.Position;
import org.junit.Assert;
import org.junit.Test;

public class IdentifierTokenTest {

    @Test
    public void constructorTest()
    {
        Position p = new Position();
        IdentifierToken t = new IdentifierToken(p, "abc");

        Assert.assertEquals(TokenTypes.IDENTIFIER, t.getName());
        Assert.assertTrue(t.getPos().isEqual(p));
        Assert.assertEquals("abc", t.getId());
    }

    @Test
    public void isEqualTest()
    {
        Position p1 = new Position(3, 4);
        Position p2 = new Position(p1);
        Position p3 = new Position();

        Token t1 = new IdentifierToken(p1, "abc");
        Token t2 = new IdentifierToken(p2, "abc");
        Token t3 = new IdentifierToken(p2, "cba");
        Token t4 = new IdentifierToken(p3, "dac");
        Token t5 = new IdentifierToken(p2, "kot");

        Assert.assertTrue(t1.isEqual(t2));
        Assert.assertFalse(t1.isEqual(t3));
        Assert.assertFalse(t1.isEqual(t4));
        Assert.assertFalse(t5.isEqual(t3));
        Assert.assertFalse(t3.isEqual(t5));
    }

}