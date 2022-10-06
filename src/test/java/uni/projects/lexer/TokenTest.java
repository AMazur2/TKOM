package uni.projects.lexer;

import uni.projects.reader.Position;
import org.junit.Assert;
import org.junit.Test;

public class TokenTest {

    @Test
    public void constructorTest() {
        Position p = new Position();
        Token t = new Token(TokenTypes.PLUS, p, null);

        Assert.assertEquals(TokenTypes.PLUS, t.getName());
        Assert.assertTrue(t.getPos().isEqual(p));
    }

    @Test
    public void isEqualTest() {
        Position p1 = new Position(3, 4);
        Position p2 = new Position(p1);
        Position p3 = new Position();

        Token t1 = new Token(TokenTypes.BAR, p1, null);
        Token t2 = new Token(TokenTypes.BAR, p2, null);
        Token t3 = new Token(TokenTypes.CLASS, p2, null);
        Token t4 = new Token(TokenTypes.BAR, p3, null);
        Token t5 = new Token(TokenTypes.BAR, p2, null);

        Assert.assertTrue(t1.isEqual(t2));
        Assert.assertFalse(t1.isEqual(t3));
        Assert.assertFalse(t1.isEqual(t4));
        Assert.assertFalse(t5.isEqual(t3));
        Assert.assertFalse(t3.isEqual(t5));

    }

}