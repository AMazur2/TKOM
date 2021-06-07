package Parser.Values;

import Parser.Expression;
import Visitor.Visitor;

public class LowerEqual extends ComparisonOperator {
    public LowerEqual(Expression lex, Expression rex) {
        super(lex, rex);
    }

    public LowerEqual() {super();}

    @Override
    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }
}
