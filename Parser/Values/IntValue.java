package Parser.Values;

import Parser.Expression;
import Visitor.Visitable;
import Visitor.Visitor;

public class IntValue implements Expression, Visitable {

    private int value;

    public IntValue(int v) { this.value = v; }

    public int getValue() { return this.value; }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
