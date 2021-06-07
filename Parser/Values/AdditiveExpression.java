package Parser.Values;

import Parser.Expression;

public abstract class AdditiveExpression extends OperatorExpression {
    public AdditiveExpression(Expression lex, Expression rex) {
        super(lex, rex);
    }
}
