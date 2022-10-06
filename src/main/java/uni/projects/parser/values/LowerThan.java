package uni.projects.parser.values;

import uni.projects.parser.Expression;
import uni.projects.visitor.Visitor;

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
