package Parser.Values;

import Parser.Expression;
import Visitor.Visitor;

public class Power extends PowerExpression {

    public Power(Expression lex, Expression rex) {
        super(lex, rex);
    }

    @Override
    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }
}
