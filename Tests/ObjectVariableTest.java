package Tests;

import Parser.ObjectVariable;
import Parser.Statements.ClassStatement;
import Parser.Variable;
import Parser.VariableType;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

public class ObjectVariableTest {

    @Test
    public void objectVariableTest()
    {
        ClassStatement cs = new ClassStatement(null, null, "MyClass");
        ObjectVariable ov = new ObjectVariable(VariableType.CLASS, "MyClass", new HashMap<String, Variable>());

        Assert.assertEquals(ov.getVt(), VariableType.CLASS);
        Assert.assertTrue(ov.getValue() instanceof HashMap);
        Assert.assertEquals(ov.getClassName(), "MyClass");
    }
}