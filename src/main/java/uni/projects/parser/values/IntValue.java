package uni.projects.parser.values;

import uni.projects.parser.Expression;
import uni.projects.visitor.Visitable;
import uni.projects.visitor.Visitor;

public class IntValue implements Expression, Visitable {

    private int value;

    public IntValue(int v) {
        this.value = v;
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
