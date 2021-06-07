package Parser.Values;

import Parser.Expression;
import Visitor.Visitor;

public class NotEquals extends ComparisonOperator {
    public NotEquals(Expression lex, Expression rex) {
        super(lex, rex);
    }

    public NotEquals() {super();}

    @Override
    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }
}
