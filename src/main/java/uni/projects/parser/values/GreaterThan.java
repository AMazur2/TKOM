package uni.projects.parser.values;

import uni.projects.parser.Expression;
import uni.projects.visitor.Visitor;

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
