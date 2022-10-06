package uni.projects.parser.statements;

import uni.projects.parser.Expression;
import uni.projects.parser.Statement;
import uni.projects.visitor.Visitor;

import java.util.Vector;

public class ForStatement implements Statement {

    private Vector<Statement> initialize;
    private Expression condition;
    private Expression postInstr;
    private Vector<Statement> instructions;

    public ForStatement(Vector<Statement> init, Expression con, Expression post, Vector<Statement> inst )
    {
        this.initialize = init;
        this.condition = con;
        this.postInstr = post;
        this.instructions = inst;
    }

    public Vector<Statement> getInitialize() { return this.initialize; }

    public Expression getCondition() { return this.condition; }

    public Expression getPostInstr() { return this.postInstr; }

    public Vector<Statement> getInstructions() { return this.instructions; }

    @Override
    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }
}
