package uni.projects.parser.values;

import uni.projects.parser.Expression;
import uni.projects.visitor.Visitor;

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
