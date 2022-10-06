package uni.projects.parser.values;

import uni.projects.parser.Expression;
import uni.projects.visitor.Visitable;
import uni.projects.visitor.Visitor;

public class MessValue implements Expression, Visitable {

    private String value;

    public MessValue(String v) { this.value = v; }

    public String getValue() { return this.value;}

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
