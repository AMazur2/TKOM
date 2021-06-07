package Parser.Values;

import Parser.Expression;

public abstract class MultiplicativeExpression extends OperatorExpression {
    public MultiplicativeExpression(Expression lex, Expression rex) {
        super(lex, rex);
    }
}
