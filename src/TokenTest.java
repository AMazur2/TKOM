import org.junit.Assert;
import org.junit.Test;

public class TokenTest {

    @Test
    public void constructorTest()
    {
        Position p = new Position();
        Token t = new Token(Tokens.ADD_OP, p, null);

        Assert.assertEquals(Tokens.ADD_OP, t.getName());
        Assert.assertTrue(t.getPos().isEqual(p));
        Assert.assertNull(t.getInfo());
    }

    @Test
    public void isEqualTest()
    {
        Position p1 = new Position(3, 4);
        Position p2 = new Position(p1);
        Position p3 = new Position();

        Token t1 = new Token(Tokens.BAR, p1, null);
        Token t2 = new Token(Tokens.BAR, p2, null);
        Token t3 = new Token(Tokens.CLASS, p2, null);
        Token t4 = new Token(Tokens.BAR, p3, null);
        Token t5 = new Token(Tokens.BAR, p2, "message");

        Assert.assertTrue(t1.isEqual(t2));
        Assert.assertFalse(t1.isEqual(t3));
        Assert.assertFalse(t1.isEqual(t4));
        Assert.assertFalse(t5.isEqual(t3));
        Assert.assertFalse(t3.isEqual(t5));

    }

}