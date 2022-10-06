package uni.projects.parser.values;

import uni.projects.parser.Expression;
import uni.projects.visitor.Visitor;

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
