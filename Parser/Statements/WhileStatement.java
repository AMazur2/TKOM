package Parser.Statements;

import Parser.Expression;
import Parser.Statement;
import Visitor.Visitor;

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
