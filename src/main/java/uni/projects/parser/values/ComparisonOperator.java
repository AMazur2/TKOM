package uni.projects.parser.values;

import uni.projects.parser.Expression;

public abstract class ComparisonOperator extends OperatorExpression implements Expression {
    public ComparisonOperator(Expression lex, Expression rex) {
        super(lex, rex);
    }
    public ComparisonOperator() {super();}
}
