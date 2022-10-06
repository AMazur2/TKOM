package uni.projects.parser.values;

import uni.projects.parser.Expression;

public abstract class PowerExpression extends OperatorExpression {
    public PowerExpression(Expression lex, Expression rex) {
        super(lex, rex);
    }
}
