package Parser.Values;

import Parser.Expression;
import Visitor.Visitor;

public class GreaterEqual extends ComparisonOperator {
    public GreaterEqual(Expression lex, Expression rex) {
        super(lex, rex);
    }

    public GreaterEqual() {super();}

    @Override
    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }
}
