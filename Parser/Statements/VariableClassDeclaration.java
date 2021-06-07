package Parser.Statements;

import Lexer.TokenTypes;
import Parser.Statement;
import Visitor.Visitor;

public class VariableClassDeclaration extends VariableDeclaration implements Statement {

    private final String className;

    public VariableClassDeclaration(TokenTypes tt, String cn, String s) {
        super(tt, s);
        this.className = cn;
    }

    public String getClassName() { return this.className; }

    @Override
    public Object accept(Visitor visitor) throws Exception { return visitor.visit(this); }
}
