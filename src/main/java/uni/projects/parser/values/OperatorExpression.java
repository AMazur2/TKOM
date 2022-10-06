package uni.projects.parser.values;

import uni.projects.parser.Expression;
import uni.projects.visitor.Visitable;

public abstract class OperatorExpression implements Expression, Visitable {

    protected Expression lex;
    protected Expression rex;

    public OperatorExpression(Expression lex, Expression rex)
    {
        this.lex = lex;
        this.rex = rex;
    }

    public OperatorExpression()
    {
        this.lex = null;
        this.lex = null;
    }

    public void setLex( Expression lex ) { this.lex = lex; }

    public void setRex( Expression rex ) { this.rex = rex; }

    public Expression getLex() { return this.lex; }

    public Expression getRex() { return this.rex; }

}
