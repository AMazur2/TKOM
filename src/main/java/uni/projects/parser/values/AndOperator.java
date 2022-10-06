package uni.projects.parser.values;

import uni.projects.parser.Expression;
import uni.projects.visitor.Visitor;

public class AndOperator extends OperatorExpression implements Expression {
    public AndOperator(Expression lex, Expression rex) {
        super(lex, rex);
    }

    @Override
    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }
}
