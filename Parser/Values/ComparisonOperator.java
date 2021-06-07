package Parser.Values;

import Parser.Expression;

public abstract class ComparisonOperator extends OperatorExpression implements Expression {
    public ComparisonOperator(Expression lex, Expression rex) {
        super(lex, rex);
    }
    public ComparisonOperator() {super();}
}
