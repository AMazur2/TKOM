package Parser.Values;

import Parser.Expression;
import Visitor.Visitor;

public class FloorOperator extends OperatorExpression {

    public FloorOperator(Expression lex, Expression rex) { super(lex, rex); }

    @Override
    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }
}
