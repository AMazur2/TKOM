package uni.projects.parser;

import org.junit.Assert;
import org.junit.Test;

public class ScopeTest {

    @Test
    public void fullTest() throws Exception {
        Scope s = new Scope();

        Variable v = new Variable(VariableType.INT, 125);
        s.putVariable("num", v);
        Assert.assertTrue(s.inScope("num"));
        Assert.assertEquals(s.getValue("num"), 125);

        Variable v1 = new Variable(VariableType.BOOL, false);
        s.putVariable("b", v1);
        Assert.assertTrue(s.inScope("b"));
        Assert.assertTrue(s.getValue("b") instanceof Boolean);
        Assert.assertEquals(s.getValue("b"), false);

        s.setValue("b", true);
        Assert.assertEquals(s.getValue("b"), true);

        Scope childScope = new Scope(s);

        Assert.assertTrue(childScope.inScope("b"));
        Assert.assertTrue(childScope.inScope("num"));

        Variable v2 = new Variable(VariableType.STRING, "abc");
        childScope.putVariable("string", v2);
        Assert.assertTrue(childScope.inScope("string"));
        Assert.assertEquals(childScope.getValue("string"), "abc");
        Assert.assertFalse(s.inScope("string"));
    }
}