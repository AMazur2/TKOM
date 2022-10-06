package uni.projects.lexer;

import uni.projects.reader.Position;
import org.junit.Assert;
import org.junit.Test;

public class MessTokenTest {

    @Test
    public void constructorTest() {
        Position p = new Position();
        MessToken t = new MessToken(p, "test");

        Assert.assertEquals(TokenTypes.STRING, t.getName());
        Assert.assertTrue(t.getPos().isEqual(p));
        Assert.assertEquals("test", t.getMess());
    }

    @Test
    public void isEqualTest() {
        Position p1 = new Position(3, 4);
        Position p2 = new Position(p1);
        Position p3 = new Position();

        Token t1 = new MessToken(p1, "abc");
        Token t2 = new MessToken(p2, "abc");
        Token t3 = new MessToken(p2, "cba");
        Token t4 = new MessToken(p3, "def");
        Token t5 = new MessToken(p2, "ghi");

        Assert.assertTrue(t1.isEqual(t2));
        Assert.assertFalse(t1.isEqual(t3));
        Assert.assertFalse(t1.isEqual(t4));
        Assert.assertFalse(t5.isEqual(t3));
        Assert.assertFalse(t3.isEqual(t5));
    }
}