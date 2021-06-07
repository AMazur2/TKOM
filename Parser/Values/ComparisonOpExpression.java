package Parser.Values;

import Parser.Expression;

public abstract class ComparisonOpExpression extends OperatorExpression implements Expression {

    public ComparisonOpExpression(Expression lex, Expression rex) {
        super(lex, rex);
    }

    public ComparisonOpExpression() {super();}
}
