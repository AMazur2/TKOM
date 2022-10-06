package uni.projects.parser.values;

import uni.projects.parser.Expression;

public abstract class AdditiveExpression extends OperatorExpression {
    public AdditiveExpression(Expression lex, Expression rex) {
        super(lex, rex);
    }
}
