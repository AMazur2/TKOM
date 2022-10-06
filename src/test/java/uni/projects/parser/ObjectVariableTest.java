package uni.projects.parser;

import org.junit.Assert;
import org.junit.Test;
import uni.projects.parser.statements.ClassStatement;

import java.util.HashMap;

public class ObjectVariableTest {

    @Test
    public void objectVariableTest() {
        ClassStatement cs = new ClassStatement(null, null, "MyClass");
        ObjectVariable ov = new ObjectVariable(VariableType.CLASS, "MyClass", new HashMap<String, Variable>());

        Assert.assertEquals(ov.getVt(), VariableType.CLASS);
        Assert.assertTrue(ov.getValue() instanceof HashMap);
        Assert.assertEquals(ov.getClassName(), "MyClass");
    }
}