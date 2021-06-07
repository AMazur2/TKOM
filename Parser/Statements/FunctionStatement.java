package Parser.Statements;

import Parser.Statement;
import Visitor.Visitor;

import java.util.Vector;

public class FunctionStatement implements Statement {

    private String name;
    private Vector<VariableDeclaration> arguments;
    private Vector<Statement> instructions;

    public FunctionStatement( String n, Vector<VariableDeclaration> arg, Vector<Statement> instr )
    {
        this.name = n;
        this.arguments = arg;
        this.instructions = instr;
    }

    public Vector<Statement> getInstructions() { return this.instructions; }

    public Vector<VariableDeclaration> getArguments() { return this.arguments; }

    public String getName() { return this.name; }

    @Override
    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }

}
