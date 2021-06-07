package Parser.Values;

import Parser.Expression;
import Visitor.Visitor;

public class Modulo extends ModuloExpression {

    public Modulo(Expression lex, Expression rex) {
        super(lex, rex);
    }

    @Override
    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }
}
