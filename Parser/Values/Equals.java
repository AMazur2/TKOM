package Parser.Values;

import Parser.Expression;
import Visitor.Visitor;

public class Equals extends ComparisonOperator {
    public Equals(Expression lex, Expression rex) {
        super(lex, rex);
    }

    public Equals() {super(); }

    @Override
    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }
}
