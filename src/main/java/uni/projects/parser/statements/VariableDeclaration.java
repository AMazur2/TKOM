package uni.projects.parser.statements;

import uni.projects.lexer.TokenTypes;
import uni.projects.parser.Statement;
import uni.projects.visitor.Visitor;

public class VariableDeclaration implements Statement {

    private TokenTypes type;
    private String name;

    public VariableDeclaration(TokenTypes tt, String s)
    {
        this.type = tt;
        this.name = s;
    }

    public String getName() { return this.name; }

    public TokenTypes getType() { return this.type; }

    @Override
    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }
}
