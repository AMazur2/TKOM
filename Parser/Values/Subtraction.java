package Parser.Values;

import Parser.Expression;
import Visitor.Visitor;

public class Subtraction extends AdditiveExpression {

    public Subtraction(Expression lex, Expression rex) {
        super(lex, rex);
    }

    @Override
    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }
}
