package uni.projects.parser.values;

import uni.projects.parser.Expression;
import uni.projects.parser.Statement;
import uni.projects.visitor.Visitor;

public class Equal implements Statement {

    private Expression lex;
    private Expression rex;

    public Equal(Expression lex, Expression rex)
    {
        super();
        this.lex = lex;
        this.rex = rex;
    }

    public Expression getLex() { return this.lex; }

    public Expression getRex() { return this.rex; }

    @Override
    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }
}
