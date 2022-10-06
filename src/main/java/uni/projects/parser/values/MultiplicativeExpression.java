package uni.projects.parser.values;

import uni.projects.parser.Expression;

public abstract class MultiplicativeExpression extends OperatorExpression {
    public MultiplicativeExpression(Expression lex, Expression rex) {
        super(lex, rex);
    }
}
