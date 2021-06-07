package Parser.Values;

import Parser.Expression;
import Visitor.Visitor;

public class GreaterThan extends ComparisonOperator {
    public GreaterThan(Expression lex, Expression rex) {
        super(lex, rex);
    }

    public GreaterThan() {super();}

    @Override
    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }
}
