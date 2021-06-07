package Parser.Values;

import Parser.Expression;
import Visitor.Visitable;
import Visitor.Visitor;

public class MessValue implements Expression, Visitable {

    private String value;

    public MessValue(String v) { this.value = v; }

    public String getValue() { return this.value;}

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
