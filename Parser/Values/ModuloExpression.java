package Parser.Values;

import Parser.Expression;

public abstract class ModuloExpression extends OperatorExpression{
    public ModuloExpression(Expression lex, Expression rex) {
        super(lex, rex);
    }
}
