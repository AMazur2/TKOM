package Parser.Values;

import Parser.Expression;
import Visitor.Visitor;

public class MinusOperator implements Expression {

    private Expression ex;

    public MinusOperator(Expression ex) { this.ex = ex; }

    public Expression getEx() { return this.ex; }

    @Override
    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }
}
