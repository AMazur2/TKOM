import org.junit.Assert;
import org.junit.Test;

public class PositionTest {

    @Test
    public void constructorTest()
    {

        Position p1 = new Position();

        Assert.assertEquals(p1.getColumn(), 1);
        Assert.assertEquals(p1.getLine(), 1);

        Position p2 = new Position(2, 5);

        Assert.assertEquals(p2.getLine(), 5);
        Assert.assertEquals(p2.getColumn(), 2);

        Position p3 = new Position(p1);

        Assert.assertEquals(p3.getColumn(), 1);
        Assert.assertEquals(p3.getLine(), 1);

    }

    @Test
    public void settersTest()
    {

        Position pos = new Position();

        pos.setColumn(5);
        pos.setLine(4);

        Assert.assertEquals(pos.getLine(), 4);
        Assert.assertEquals(pos.getColumn(), 5);

    }

    @Test
    public void incrementTest()
    {

        Position p1 = new Position();

        p1.incrementColumn();

        Assert.assertEquals(p1.getColumn(), 2);
        Assert.assertEquals(p1.getLine(), 1);

        p1.incrementLine();

        Assert.assertEquals(p1.getLine(), 2);
        Assert.assertEquals(p1.getColumn(), 2);

    }

    @Test
    public void isEqualsTest()
    {

        Position p1 = new Position();
        Position p2 = new Position(p1);
        Position p3 = new Position(3,4);

        Assert.assertTrue(p1.isEqual(p2));
        Assert.assertFalse(p3.isEqual(p1));

    }

}