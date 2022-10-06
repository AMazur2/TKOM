package uni.projects.parser.statements;

import uni.projects.parser.Expression;
import uni.projects.visitor.Visitor;

import java.util.HashMap;
import java.util.Vector;

public class ClassStatement implements Expression {

    private String name;
    private Vector<VariableDeclaration> arguments;
    private HashMap<String, FunctionStatement> functions;

    public ClassStatement(Vector<VariableDeclaration> args, HashMap<String, FunctionStatement> fun, String name)
    {
        this.name = name;
        this.arguments = args;
        this.functions = fun;
    }

    public Vector<VariableDeclaration> getArguments() { return this.arguments; }

    public HashMap<String, FunctionStatement> getFunctions() { return this.functions; }

    public String getName() { return this.name; }

    @Override
    public Object accept(Visitor visitor) {
        return null;
    }
}
