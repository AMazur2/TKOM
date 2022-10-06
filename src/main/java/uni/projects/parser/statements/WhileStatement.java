package uni.projects.parser.statements;

import uni.projects.parser.Expression;
import uni.projects.parser.Statement;
import uni.projects.visitor.Visitor;

import java.util.Vector;

public class WhileStatement implements Statement {

    private Expression condition;
    private Vector<Statement> instructions;

    public WhileStatement(Expression con , Vector<Statement> inst)
    {
        this.condition = con;
        this.instructions = inst;
    }

    public Expression getCondition() { return this.condition; }

    public Vector<Statement> getInstructions() { return this.instructions; }

    @Override
    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }
}
