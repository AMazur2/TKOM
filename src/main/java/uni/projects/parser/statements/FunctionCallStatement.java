package uni.projects.parser.statements;

import uni.projects.parser.Expression;
import uni.projects.parser.Statement;
import uni.projects.visitor.Visitor;

import java.util.Vector;

public class FunctionCallStatement implements Statement, Expression {

    private String name;
    private Vector<Expression> arguments;

    public FunctionCallStatement(String n)
    {
        this.name = n;
        this.arguments = new Vector<>();
    }

    public FunctionCallStatement() {};

    public void setName(String n) { this.name = n; }

    public void addArgument(Expression instr) { arguments.add(instr); }

    public String getName() { return this.name; }

    public Vector<Expression> getArguments() { return this.arguments; }

    @Override
    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }
}
