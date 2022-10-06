package uni.projects.parser.values;

import uni.projects.parser.Expression;

public abstract class ModuloExpression extends OperatorExpression{
    public ModuloExpression(Expression lex, Expression rex) {
        super(lex, rex);
    }
}
