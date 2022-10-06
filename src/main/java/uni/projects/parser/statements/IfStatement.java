package uni.projects.parser.statements;

import uni.projects.parser.Expression;
import uni.projects.parser.Statement;
import uni.projects.visitor.Visitor;

import java.util.Vector;

public class IfStatement implements Statement {

    private Vector<Expression> conditions;
    private Vector<Vector<Statement>> ifInstructions;
    private Vector<Statement> elseInstructions;

    public IfStatement(Vector<Expression> con, Vector<Vector<Statement>> ifInst, Vector<Statement> elseInst )
    {
        this.conditions = con;
        this.ifInstructions = ifInst;
        this.elseInstructions = elseInst;
    }

    public Vector<Expression> getConditions() { return this.conditions; }

    public Vector<Vector<Statement>> getIfInstructions() { return this.ifInstructions; }

    public Vector<Statement> getElseInstructions() { return this.elseInstructions; }

    @Override
    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }
}
