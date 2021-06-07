package Tests;

import Parser.Variable;
import Parser.VariableType;
import org.junit.Assert;
import org.junit.Test;

public class VariableTest {

    @Test
    public void constructorTest()
    {
        Variable v1 = new Variable(VariableType.INT, 256);
        Assert.assertEquals(v1.getVt(), VariableType.INT);
        Assert.assertEquals(v1.getValue(), 256);

        Variable v2 = new Variable(VariableType.BOOL, false);
        Assert.assertEquals(v2.getVt(), VariableType.BOOL);
        Assert.assertEquals(v2.getValue(), false);

        Variable v3 = new Variable(VariableType.STRING, "abc");
        Assert.assertEquals(v3.getVt(), VariableType.STRING);
        Assert.assertEquals(v3.getValue(), "abc");
    }

    @Test
    public void setTest()
    {
        Variable v1 = new Variable(VariableType.STRING, "abc");
        Assert.assertEquals(v1.getVt(), VariableType.STRING);
        Assert.assertEquals(v1.getValue(), "abc");

        v1.setVt(VariableType.INT);
        v1.setValue(136);
        Assert.assertEquals(v1.getValue(), 136);
        Assert.assertEquals(v1.getVt(), VariableType.INT);
    }
}