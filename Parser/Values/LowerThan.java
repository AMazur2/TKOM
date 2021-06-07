package Parser.Values;

import Parser.Expression;
import Visitor.Visitor;

public class LowerThan extends ComparisonOperator {

    public LowerThan(Expression lex, Expression rex) {
        super(lex, rex);
    }

    public LowerThan() {super();}

    @Override
    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }
}
