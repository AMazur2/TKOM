package uni.projects.parser.values;

import uni.projects.parser.Expression;
import uni.projects.visitor.Visitor;

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
