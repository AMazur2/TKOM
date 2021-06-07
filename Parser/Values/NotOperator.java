package Parser.Values;

import Parser.Expression;
import Visitor.Visitor;

public class NotOperator implements Expression {

    private final Expression ex;

    public NotOperator(Expression ex) { this.ex = ex; }

    public Expression getEx() { return this.ex; }


    @Override
    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }
}
