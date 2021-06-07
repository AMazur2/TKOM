package Parser.Values;

import Parser.Expression;
import Visitor.Visitable;
import Visitor.Visitor;

public class BoolValue implements Expression, Visitable {

    private boolean value;

    public BoolValue(boolean v) { this.value = v; }

    public boolean getValue() { return this.value; }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
