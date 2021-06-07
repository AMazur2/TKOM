package Lexer;

import Reader.Position;
import org.jetbrains.annotations.NotNull;

public class IdentifierToken extends Token {

    private String id;

    public IdentifierToken(@NotNull Position p, String id)
    {
        super(TokenTypes.IDENTIFIER, p, null);
        this.id = id;
    }

    public IdentifierToken(@NotNull Position p, String c, String id)
    {
        super(TokenTypes.IDENTIFIER, p, c);
        this.id = id;
    }

    public String getId() { return this.id; }

    @Override
    public boolean isEqual(Object o)
    {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        IdentifierToken t = (IdentifierToken) o;

        return this.name == t.getName() && this.pos.isEqual(t.getPos()) && this.id.equals(t.getId());
    }

    @Override
    public void showDetails()
    {
        System.out.println("Name: " + this.name +  " | Position: (" + this.pos.getLine() + " ; " + this.pos.getColumn()
                + ") | Id: " + this.id );
    }

}
