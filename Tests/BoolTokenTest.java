package Tests;

import Lexer.BoolToken;
import Lexer.Token;
import Lexer.TokenTypes;
import Reader.Position;
import org.junit.Assert;
import org.junit.Test;

public class BoolTokenTest {

    @Test
    public void constructorTest()
    {
        Position p = new Position();
        BoolToken t = new BoolToken(p, false);

        Assert.assertEquals(p, t.getPos());
        Assert.assertEquals(TokenTypes.BOOLEAN, t.getName());
        Assert.assertFalse(t.getValue());
    }

    @Test
    public void isEqualTest()
    {
        Position p1 = new Position(3, 4);
        Position p2 = new Position(p1);
        Position p3 = new Position();

        Token t1 = new BoolToken(p1, true);
        Token t2 = new BoolToken(p2, true);
        Token t3 = new BoolToken(p2, false);
        Token t4 = new BoolToken(p3, true);
        Token t5 = new BoolToken(p2, false);

        Assert.assertTrue(t1.isEqual(t2));
        Assert.assertFalse(t1.isEqual(t3));
        Assert.assertFalse(t1.isEqual(t4));
        Assert.assertTrue(t5.isEqual(t3));
        Assert.assertTrue(t3.isEqual(t5));
    }
}