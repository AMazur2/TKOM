package Tests;

import Lexer.IntToken;
import Lexer.Token;
import Lexer.TokenTypes;
import Reader.Position;
import org.junit.Assert;
import org.junit.Test;

public class IntTokenTest {

    @Test
    public void constructorTest()
    {
        Position p = new Position();
        IntToken t = new IntToken(p, 10);

        Token s;

        Assert.assertEquals(TokenTypes.INT, t.getName());
        Assert.assertTrue(t.getPos().isEqual(p));
        Assert.assertEquals(10, t.getValue());

        s = t;

        Assert.assertEquals(s.getClass().toString(), "class Lexer.IntToken");
    }

    @Test
    public void isEqualTest()
    {
        Position p1 = new Position(3, 4);
        Position p2 = new Position(p1);
        Position p3 = new Position();

        Token t1 = new IntToken(p1, 1);
        Token t2 = new IntToken(p2, 1);
        Token t3 = new IntToken(p2, 0);
        Token t4 = new IntToken(p3, 1);
        Token t5 = new IntToken(p2, 3);

        Assert.assertTrue(t1.isEqual(t2));
        Assert.assertFalse(t1.isEqual(t3));
        Assert.assertFalse(t1.isEqual(t4));
        Assert.assertFalse(t5.isEqual(t3));
        Assert.assertFalse(t3.isEqual(t5));
    }
}