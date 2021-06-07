package Parser.Values;

import Parser.Expression;

public abstract class PowerExpression extends OperatorExpression {
    public PowerExpression(Expression lex, Expression rex) {
        super(lex, rex);
    }
}
