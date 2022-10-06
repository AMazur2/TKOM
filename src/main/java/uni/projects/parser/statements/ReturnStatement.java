package uni.projects.parser.statements;

import uni.projects.parser.Expression;
import uni.projects.parser.Statement;
import uni.projects.visitor.Visitor;

public class ReturnStatement implements Statement {

    private Expression expression;

    public void setExpression(Expression e) { this.expression = e; }

    public Expression getExpression() { return this.expression; }

    @Override
    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }
}
