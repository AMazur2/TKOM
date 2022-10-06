package uni.projects.parser.values;

import uni.projects.parser.Expression;
import uni.projects.visitor.Visitable;
import uni.projects.visitor.Visitor;

public class BoolValue implements Expression, Visitable {

    private boolean value;

    public BoolValue(boolean v) { this.value = v; }

    public boolean getValue() { return this.value; }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
